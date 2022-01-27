/**
 * 
 */
package uk.ac.cf.milling.utils.runnables;

import java.awt.Toolkit;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.gui.simulation.ResultsPanel;
import uk.ac.cf.milling.objects.CuttingTool;
import uk.ac.cf.milling.objects.CuttingToolProfile;
import uk.ac.cf.milling.objects.KPIs;
import uk.ac.cf.milling.objects.Nc;
import uk.ac.cf.milling.objects.SettingsSingleton;
import uk.ac.cf.milling.objects.SimulatorConfig;
import uk.ac.cf.milling.utils.data.ConvertUtils;
import uk.ac.cf.milling.utils.data.DataManipulationUtils;
import uk.ac.cf.milling.utils.data.IoUtils;
import uk.ac.cf.milling.utils.db.CuttingToolProfileUtils;
import uk.ac.cf.milling.utils.db.CuttingToolUtils;
import uk.ac.cf.milling.utils.db.NcUtils;
import uk.ac.cf.milling.utils.plotting.Plotter2D;
import uk.ac.cf.milling.utils.plotting.Plotter3D;
import uk.ac.cf.milling.utils.simulation.SimulatorUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class SimulateProcessRunnable implements Runnable{
	SimulatorConfig config = null;

	public SimulateProcessRunnable(SimulatorConfig config) {
		this.config = config;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		KPIs kpis = new KPIs();

		// Store billet information into KPIs
//		kpis.setBillet(config.getBillet());
		
		//Start the progress bar
		JProgressBar progressBar = SettingsSingleton.getInstance().progressBar;
		progressBar.setVisible(true);
		
		// Create NC entry to know what was the initial file describing the process 
		Nc nc = NcUtils.getCreateNcFile(config.getInputFilePath());
		NcUtils.updateNcPath(nc.getNcId(), config.getInputFilePath(), config.getBillet().getBilletId());
		
		// Simulate and generate results based on the input file type
		String inputFileType = config.getInputFileType();
		switch (inputFileType){

		case "GCode file" :
			
			kpis = SimulatorUtils.simulateGCodeFileUGS(kpis, config);
			break;
			
		case "CSV file" :
//			kpis = SimulatorUtils.simulateCsvFile(kpis, config);
			kpis = SimulatorUtils.simulateAnalysisFile(kpis, config);
			break;
		default :
			System.out.println("Unknown input file type.");
		}
		
		
		/*
		 * Results section
		 */
		
		//Print toolpath limits
		DataManipulationUtils.printToolPathLimits(kpis);

		// Plot the machined part
		if (config.isShow3dPart()){

//			checkPart(kpis.getPart());

			JPanel chart3DPanelV4 = Plotter3D.getV4ChartPanel(kpis.getPart());
			ResultsPanel.addTab(
					"Part: " +	Paths.get(config.getInputFilePath()).getFileName()
					, chart3DPanelV4);
			System.out.println("done");
		}
		
		//Display charts
		if (config.isShow2dGraphs()){
			System.out.print("Plotting Process Parameters...");
			List<JPanel> panels = new ArrayList<JPanel>();
			
			JPanel mrrPanel = Plotter2D.get2dPlotPanel("MRR", ConvertUtils.castToFloatArray(kpis.getTimePoints()), ConvertUtils.castToFloatArray(kpis.getMrr()));
			panels.add(mrrPanel);

			//Generate the tool usage charts
			List<CuttingTool> tools = kpis.getTools();
			
			for (CuttingTool tool:tools){
				//TODO Generating torque chart
//				In order to calculate torque:
//				https://www.ctemag.com/news/articles/calculated-force
				
				// Generating axial usage chart
				List<CuttingToolProfile> axialProfiles = tool.getAxialProfile();
				double[] axialProfilesDataX = new double[axialProfiles.size()];
				double[] axialProfilesDataY = new double[axialProfiles.size()];
				int index = 0;
				for (CuttingToolProfile profile:axialProfiles){
					axialProfilesDataX[index] = profile.getDistanceFromNose();
					axialProfilesDataY[index] = profile.getMaterialRemoved();
					index++;
				}
				// Subtract the usage from previous runs
				List<CuttingToolProfile> axialProfilesOld = CuttingToolUtils.getCuttingTool(tool.getToolId()).getAxialProfile();
				index=0;
				for (CuttingToolProfile profile:axialProfilesOld){
					axialProfilesDataY[index] -= profile.getMaterialRemoved();
					index++;
				}
				
				JPanel axialToolPanel = Plotter2D.get2dPlotPanel(
						"Axial Profile (side)", 
						ConvertUtils.castToFloatArray(axialProfilesDataX), 
						ConvertUtils.castToFloatArray(axialProfilesDataY));
				panels.add(axialToolPanel);
				
				
				// Generating radial usage chart
				List<CuttingToolProfile> radialProfiles = tool.getRadialProfile();
				double[] radialProfilesDataX = new double[radialProfiles.size()];
				double[] radialProfilesDataY = new double[radialProfiles.size()];
				int radialIndex = 0;
				for (CuttingToolProfile profile : radialProfiles){
					radialProfilesDataX[radialIndex] = profile.getDistanceFromCentre();
					radialProfilesDataY[radialIndex] = profile.getMaterialRemoved();
					radialIndex++;
				}
				
//				Chart radialUsage = Plotter2D.get2dPlot(radialProfilesDataX, radialProfilesDataY);
//				radialUsage.getAxeLayout().setXAxeLabel("Distance from centre [mm]");
//				radialUsage.getAxeLayout().setYAxeLabel("Length in material machined [mm]");
//				
//				plots.add(radialUsage);

				JPanel radialToolPanel = Plotter2D.get2dPlotPanel(
						"Radial Profile (nose)", 
						ConvertUtils.castToFloatArray(radialProfilesDataX), 
						ConvertUtils.castToFloatArray(radialProfilesDataY)
						);
				
				panels.add(radialToolPanel);
				
			}
			
			JScrollPane tabPanel = Plotter2D.getScrollPanel(panels);
			ResultsPanel.addTab(
					Paths.get(config.getInputFilePath()).getFileName().toString() + //the filename
					" @ " + //separator
					 new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) //time
					 , tabPanel);

			System.out.println("done");
		}
		
		//Save used tool data to database (one may used database with current tool data to see additional use
		CuttingToolProfileUtils.updateCuttingToolProfilesFromTool(kpis.getTools());

		//Remove temporary files
		System.out.print("Clearing up temporary files...");
		String dataFilePath = config.getInputFilePath();System.out.println(dataFilePath);
//		(new File(analysisFilePath)).delete();
		System.out.println("all done");
		System.out.println("Total processing time: " + (System.currentTimeMillis() - startTime) + "msec");
		progressBar.setVisible(false);
		progressBar.setValue(0);
		Toolkit.getDefaultToolkit().beep();
		if (config.isShow2dGraphs()){
			GUIBuilder.showResultsPanel();
		}
		GUIBuilder.refreshCompare = true;
		GUIBuilder.refreshDataConnect = true;
		GUIBuilder.refreshTrain = true;
	}

	/**
	 * @param part
	 */
	private void checkPart(boolean[][][] part) {
		int xlength = part.length;
		int ylength = part[0].length;
		int zlength = part[0][0].length;
		int zPos = zlength / 2;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < xlength; i++) {
			for (int j = 0; j < ylength; j++) {
				sb.append((part[i][j][zPos]) ? "+" : " ");
			}
			sb.append("\n");
		}
		IoUtils.writeFile("part_output.csv", sb.toString());
		
	}
}
