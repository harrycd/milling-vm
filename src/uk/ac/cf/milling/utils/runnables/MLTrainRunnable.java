/**
 * 
 */
package uk.ac.cf.milling.utils.runnables;

import java.io.File;

import uk.ac.cf.milling.utils.learning.DataSynchronisation;
import uk.ac.cf.milling.utils.learning.Models;

/**
 * Start the ML model creation and training process.
 * @author Theocharis Alexopoulos
 * @date 24 Aug 2020
 *
 */
public class MLTrainRunnable implements Runnable{
	
	private int materialId;
	private File[] dataFiles;
	private String[] inputNames;
	private String[] targetNames;
	
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		if (materialId != 0 && dataFiles != null && inputNames != null && targetNames != null ) {
			for (File dataFile : dataFiles) {
				for (String targetName : targetNames) {
					Models.trainLearningFactors(materialId, dataFile.getAbsolutePath(), inputNames, targetName);
				}
			}
			System.out.println("Learning Set trained in : " + (System.currentTimeMillis() - startTime) + "msec");
		} else {
			System.out.println("Training process failed because one or more parameters are null");
		}
		
	}


	/**
	 * @param materialId the materialId to set
	 */
	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}


	/**
	 * @param dataFiles the dataFiles to set
	 */
	public void setDataFiles(File[] dataFiles) {
		this.dataFiles = dataFiles;
	}


	/**
	 * @param inputNames the inputNames to set
	 */
	public void setInputNames(String[] inputNames) {
		this.inputNames = inputNames;
	}


	/**
	 * @param targetNames the targetNames to set
	 */
	public void setTargetNames(String[] targetNames) {
		this.targetNames = targetNames;
	}
	
}
