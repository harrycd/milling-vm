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
import uk.ac.cf.milling.gui.view.ResultsPanel;
import uk.ac.cf.milling.utils.data.DataManipulationUtils;
import uk.ac.cf.milling.utils.data.IoUtils;

/**
 * @author Theocharis Alexopoulos
 * @date 2 Sep 2020
 *
 */
public class ViewProcessRunnable implements Runnable {
	private String filePath;
	private List<String> parameterList;
	private int ma;

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
		for (String paramName : parameterList) {
			int paramIndex = IoUtils.getCSVTitleIndex(filePath, paramName);
			double[] paramValues = allValues[paramIndex];
			if (ma > 1) {
				paramValues = DataManipulationUtils.getCenteredMovingAverage(paramValues, ma);
			}

			parameters.put(paramName, paramValues);
		}
		
		
		JPanel newResultsPanel = new ProcessGraphsPanel().getProcessGraphsPanel(coordinates, parameters);
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
	 * @param parameterList the parameterList to set
	 */
	public void setParameterList(List<String> parameterList) {
		this.parameterList = parameterList;
	}

	/**
	 * @param ma the ma to set
	 */
	public void setMa(int ma) {
		this.ma = ma;
	}
	
}
