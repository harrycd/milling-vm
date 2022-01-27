/**
 * 
 */
package uk.ac.cf.milling.utils.runnables;

import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import uk.ac.cf.milling.graphs.ProcessGraphsPanel;
import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.gui.simulation.ResultsPanel;
import uk.ac.cf.milling.utils.data.DataManipulationUtils;
import uk.ac.cf.milling.utils.data.IoUtils;

/**
 * @author Theocharis Alexopoulos
 * @date 2 Sep 2020
 *
 */
public class ViewProcessRunnable implements Runnable {
	private String filePath;
	private List<String> yParameters;
	private String xParameter;
	private boolean scatterGraph;
	private int xSma;
	private int ySma;

	@Override
	public void run() {
		
		GUIBuilder.showMachineLogPanel();
		
		double[][] allValues = IoUtils.getCSVValues(filePath);
		allValues = DataManipulationUtils.transpose2DArrayValues(allValues); //turned to double[col][rows]
		
		String[] coordinatesTitles = new String[] {"X", "Y", "Z"};
		int[] coordinatesIndexes = IoUtils.getCSVTitleIndexes(filePath, coordinatesTitles);
		double[][] coordinates = new double[3][];
		coordinates[0] = allValues[coordinatesIndexes[0]];
		coordinates[1] = allValues[coordinatesIndexes[1]];
		coordinates[2] = allValues[coordinatesIndexes[2]];
		
		Map<String, double[]> parameters = new HashMap<String, double[]>();
		for (String paramName : yParameters) {
			int paramIndex = IoUtils.getCSVTitleIndex(filePath, paramName);
			double[] paramValues = allValues[paramIndex];
			if (ySma > 1) {
				paramValues = DataManipulationUtils.getCenteredMovingAverage(paramValues, ySma);
			}

			parameters.put(paramName, paramValues);
		}
		
		int xParameterIndex = IoUtils.getCSVTitleIndex(filePath, xParameter);
		double[] xParameterValues;
		if (xParameterIndex >= 0) {
			xParameterValues = allValues[xParameterIndex];
		} else {
			xParameterValues = new double[allValues[0].length];
			for (int i = 0; i < allValues[0].length; i++) {
				xParameterValues[i] = i;
			}
		}
		
		if (xSma > 1) {
			xParameterValues = DataManipulationUtils.getCenteredMovingAverage(xParameterValues, xSma);
		}
		
		JPanel newResultsPanel = new ProcessGraphsPanel().getProcessGraphsPanel(coordinates, parameters, xParameterValues, scatterGraph);
		ResultsPanel.addTab("Process", newResultsPanel);
		
		
		//Inform with a sound that execution finished
		Toolkit.getDefaultToolkit().beep();

		//Switch to the results window
		GUIBuilder.showResultsPanel();
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @param yParameters the yParameters to set
	 */
	public void setYParameters(List<String> yParameters) {
		this.yParameters = yParameters;
	}

	/**
	 * @param xParameter the xParameter to set
	 */
	public void setXParameter(String xParameter) {
		this.xParameter = xParameter;
	}

	/**
	 * @param scatterGraph the scatterGraph to set
	 */
	public void setScatterGraph(boolean scatterGraph) {
		this.scatterGraph = scatterGraph;
	}

	/**
	 * @param xSma the xSma to set
	 */
	public void setXSma(int xSma) {
		this.xSma = xSma;
	}

	/**
	 * @param ySma the ySma to set
	 */
	public void setYSma(int ySma) {
		this.ySma = ySma;
	}
	
	
}
