/**
 * 
 */
package uk.ac.cf.milling.utils.runnables;

import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.gui.view.ResultsPanel;
import uk.ac.cf.milling.objects.KPIs;
import uk.ac.cf.milling.utils.DataFileUtils;
import uk.ac.cf.milling.utils.IoUtils;
import uk.ac.cf.milling.utils.MLUtils;
import uk.ac.cf.milling.utils.Plotter2D;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class MLCompareRunnable implements Runnable {
	private String dataFile1 = "";
	private String dataFile2 = "";
	
	private List<String> synchParams = new ArrayList<String>();
	private String compareParam = "";
	private boolean showDiff = false;
	private int ma = 1;
	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//Switch to log window
		GUIBuilder.showMachineLogPanel();
		System.out.println("Comparison process started...");
		
		//Measure execution time
		long startTime = System.currentTimeMillis();
		long stepTime = startTime;

		/*
		 * TODO
		 * The current implementation is fixed to the parameters of milling
		 * The new should treat the CSV file as a generic file that compares its values.
		 * The plots should use the csv title to indicate what has been compared
		 * Parameter mapping will be needed to connect the generic approach of ML module
		 * with the rest of the app which is "fixed" on the milling parameters. This has
		 * to be planned in a generic way so every parameter can be connected to other
		 * generically specified paramters with a specific calculation formula
		 */
		
		//...4b) plot the difference in number of samples (if sample rate is same, this corresponds to time)
		
		String[] titles1 = IoUtils.getCSVTitles(dataFile1);
		String[] titles2 = IoUtils.getCSVTitles(dataFile2);
		
		List<Integer> indexesSynch1 = DataFileUtils.findTitleIndex(synchParams, titles1);
		List<Integer> indexesSynch2 = DataFileUtils.findTitleIndex(synchParams, titles2);

		List<Integer> indexesCompare1 = DataFileUtils.findTitleIndex(Collections.singletonList(compareParam), titles1);
		List<Integer> indexesCompare2 = DataFileUtils.findTitleIndex(Collections.singletonList(compareParam), titles2);
		
		double[][] allValues1 = DataFileUtils.transpose2dArray(IoUtils.getCSVValues(dataFile1));
		double[][] allValues2 = DataFileUtils.transpose2dArray(IoUtils.getCSVValues(dataFile2));
		
			System.out.println("CSV files parsed in: " + (System.currentTimeMillis() - stepTime) + "msec");
			stepTime = System.currentTimeMillis();
		
		//Pass the values of syncronisation parameters in the correct order to new arrays
		//synchValues[param type index][param value]
		double[][] synchValues1 = new double[synchParams.size()][allValues1[0].length];
		double[][] synchValues2 = new double[synchParams.size()][allValues2[0].length];
		
		for (int i = 0; i < indexesSynch1.size(); i++)
			synchValues1[i] = allValues1[indexesSynch1.get(i)];
		
		for (int i = 0; i < indexesSynch2.size(); i++) {
			synchValues2[i] = allValues2[indexesSynch2.get(i)];
		}
		
		//DTW on the sets based on the synchronisation parameters
		int[][] relation = MLUtils.getCurveRelation(synchValues1, synchValues2);
		
		//Pass the values of comparison parameter to a new array
		double[] compareValues1 = allValues1[indexesCompare1.get(0)];
		double[] compareValues2 = allValues2[indexesCompare2.get(0)];
		
		System.out.println("Samples synchronised in: " + (System.currentTimeMillis() - stepTime) + "msec");
		stepTime = System.currentTimeMillis();
		
		JPanel plotPanel = new JPanel();
		if (showDiff){
			plotPanel = Plotter2D.generateDiffPlot(relation, compareValues1, compareValues2, (compareParam + " data comparison"), ma, "Samples (?)", compareParam);
		} else {
			plotPanel = Plotter2D.generatePlots(relation, compareValues1, compareValues2, (compareParam + " data comparison"), ma, "Samples (?)", compareParam);
		}
		System.out.println("done in: " + (System.currentTimeMillis() - stepTime) + "msec");

		printRelation(relation, synchValues1, synchValues2);
		printRelationDiff(relation, synchValues1, synchValues2);
		
		String plotTitle = compareParam;
		if (showDiff) plotTitle += " diff";
		if (ma > 1) plotTitle += " sma" + ma;
		ResultsPanel.addTab(plotTitle, plotPanel);
		
		
		//Inform with a sound that execution finished
		Toolkit.getDefaultToolkit().beep();
		
		//Switch to the results window
		GUIBuilder.showResultsPanel();
	}
	

	


	/**
	 * @param relation
	 * @param synchValues1
	 * @param synchValues2
	 */
	private void printRelation(int[][] relation, double[][] synchValues1, double[][] synchValues2) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < relation[0].length; i++) {
			sb.append(relation[0][i] + "\t" + relation[1][i] + "\t");
			for (int j = 0; j < synchValues1.length; j++) {
				sb.append(synchValues1[j][relation[0][i]] + "\t" +synchValues2[j][relation[1][i]] + "\t");
			}
			sb.append("\n");
		}
		
		IoUtils.writeFile("relation.txt", sb.toString());
	}

	/**
	 * @param relation
	 * @param synchValues1
	 * @param synchValues2
	 */
	private void printRelationDiff(int[][] relation, double[][] synchValues1, double[][] synchValues2) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < relation[0].length; i++) {
			sb.append(relation[0][i] + "\t" + relation[1][i] + "\t");
			for (int j = 0; j < synchValues1.length; j++) {
				sb.append(synchValues1[j][relation[0][i]] - synchValues2[j][relation[1][i]] + "\t");
			}
			sb.append("\n");
		}
		
		IoUtils.writeFile("relationDiff.txt", sb.toString());
	}





	/**
	 * @param dataFilePath1
	 * @param dataFilePath2
	 */
	public void setDataFiles(String dataFilePath1, String dataFilePath2){
		this.dataFile1 = dataFilePath1;
		this.dataFile2 = dataFilePath2;
	}
	
	
	/**
	 * @param synchParams - parameter synchronisation is based on
	 */
	public void setSynchParams(List<String> synchParams) {
		this.synchParams = synchParams;
	}

	/**
	 * @param compareParam - parameter to compare
	 */
	public void setCompareParam(String compareParam) {
		this.compareParam = compareParam;
	}


	/**
	 * @param showDiff the showDiff to set
	 */
	public void setShowDiff(boolean showDiff) {
		this.showDiff = showDiff;
	}


	/**
	 * @param ma the ma to set
	 */
	public void setMa(int ma) {
		this.ma = ma;
	}


	private static void printRelation(int[][] relation, KPIs kpis0, KPIs kpis1){

		int length = relation[0].length;
		double[] xCoord0 = kpis0.getToolX();
		double[] yCoord0 = kpis0.getToolY();
		double[] zCoord0 = kpis0.getToolZ();
		double[] xLoad0 = kpis0.getxLoad();
		double[] yLoad0 = kpis0.getyLoad();
		double[] zLoad0 = kpis0.getzLoad();
		double[] sLoad0 = kpis0.getSpindleLoad();
		

		double[] xCoord1 = kpis1.getToolX();
		double[] yCoord1 = kpis1.getToolY();
		double[] zCoord1 = kpis1.getToolZ();
		double[] xLoad1 = kpis1.getxLoad();
		double[] yLoad1 = kpis1.getyLoad();
		double[] zLoad1 = kpis1.getzLoad();
		double[] sLoad1 = kpis1.getSpindleLoad();


		String str;
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter("relationFullData.txt"));
			for (int i=0; i<length; i++){
				
				str = (relation[0][i] + "\t" + relation[1][i]+ 
						"\t" + "\t" + xCoord0[relation[0][i]] + "\t" + yCoord0[relation[0][i]] + "\t" + zCoord0[relation[0][i]]+
						"\t" + "\t" + xCoord1[relation[1][i]] + "\t" + yCoord1[relation[1][i]] + "\t" + zCoord1[relation[1][i]]+
						"\t" + "\t" + xLoad0[relation[0][i]] + "\t" + yLoad0[relation[0][i]] + "\t" + zLoad0[relation[0][i]] + "\t" + sLoad0[relation[0][i]]+
						"\t" + "\t" + xLoad1[relation[1][i]] + "\t" + yLoad1[relation[1][i]] + "\t" + zLoad1[relation[1][i]] + "\t" + sLoad1[relation[1][i]]+
						"\n");
				bw.write(str);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
