/**
 * 
 */
package uk.ac.cf.milling.utils.runnables;

import java.awt.Toolkit;

import javax.swing.JPanel;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.gui.view.ResultsPanel;
import uk.ac.cf.milling.utils.data.DataManipulationUtils;
import uk.ac.cf.milling.utils.data.IoUtils;
import uk.ac.cf.milling.utils.plotting.ColourScatter3D;

/**
 * @author Theocharis Alexopoulos
 * @date 2 Sep 2020
 *
 */
public class ViewProcessRunnable implements Runnable {
	private String filePath;
	private String paramName;

	@Override
	public void run() {
		
		double[][] allValues = IoUtils.getCSVValues(filePath);
		allValues = DataManipulationUtils.transpose2DArrayValues(allValues); //turned to double[col][rows]
		String[] titles = new String[] {"X", "Y", "Z", paramName};
		int[] indexes = IoUtils.getCSVTitleIndexes(filePath, titles);
		double[][] coordinates = new double[3][];
		coordinates[0] = allValues[indexes[0]];
		coordinates[1] = allValues[indexes[1]];
		coordinates[2] = allValues[indexes[2]];
		double[] paramValues = allValues[indexes[3]];
		
		JPanel paramPanel = ColourScatter3D.getParameter3DColourMap(coordinates, paramName, paramValues);
		ResultsPanel.addTab(paramName, paramPanel);
		
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
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
}
