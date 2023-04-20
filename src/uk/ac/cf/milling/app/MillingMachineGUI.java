/**
 * The current milling machine makes the following assumptions
 * Only linear G0 and G1 commands are given
 * The billet is a cube
 * The cutting tool does not collide with the billet (can start cutting under the surface
 * The cutting tool is homogenous, does not deflect and has infinite number of teeth.
 */
package uk.ac.cf.milling.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import uk.ac.cf.milling.gui.GUIBuilder;

/**
 * This is the starting point for running the simulator.<br>
 * Initialises the local graphical interface.
 * @author Theocharis Alexopoulos
 *
 */
public class MillingMachineGUI {
	
	public static void main(String[] args){
		Runtime runtime = Runtime.getRuntime();
		System.out.println("Free memory  : " + runtime.freeMemory());
		System.out.println("Total memory : " + runtime.totalMemory());
		System.out.println("Max memory   : " + runtime.maxMemory());
		System.out.println("Processors   : " + runtime.availableProcessors());
		
		// Ensure correct folder structure is in place
		try {
			Files.createDirectories(Paths.get("Database"));
			Files.createDirectories(Paths.get("MLModel"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Set App theme
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		GUIBuilder.createAndShowGUI();
	}
}
