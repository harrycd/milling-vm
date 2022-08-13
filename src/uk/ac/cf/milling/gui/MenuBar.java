/**
 * 
 */
package uk.ac.cf.milling.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import uk.ac.cf.milling.gui.help.HelpPanel;

/**
 * Builder for the menu bar at the top of the GUI.
 * @author Theocharis Alexopoulos
 *
 */
public class MenuBar {
	private JMenuBar menuBar;
	
	public JMenuBar getMenuBar(){
		createMenuBar();
		menuBar.setVisible(false);
		return menuBar;
	}

	private void createMenuBar() {
		menuBar = new JMenuBar();
		Font font = new Font("Verdana", Font.PLAIN, 20);

		// File menu
		JMenu fileMenu = new JMenu(" File ");
		fileMenu.setFont(font);
		menuBar.add(fileMenu);
		
		JMenuItem saveSetupFileMenu = new JMenuItem("Save setup");
		saveSetupFileMenu.setFont(font);
//		fileMenu.add(saveSetupFileMenu);
		
		JMenuItem loadSetupFileMenu = new JMenuItem("Load setup");
		loadSetupFileMenu.setFont(font);
//		fileMenu.add(loadSetupFileMenu);

		JMenuItem databaseFileMenu = new JMenuItem("Database");
		databaseFileMenu.setFont(font);
		fileMenu.add(databaseFileMenu);
		
		JMenuItem exitFileMenu = new JMenuItem("Exit");
		exitFileMenu.setFont(font);
		fileMenu.add(exitFileMenu);
		
		
		
		//Libraries menu
		JMenu libraryMenu = new JMenu(" Libraries ");
		libraryMenu.setFont(font);
		menuBar.add(libraryMenu);

		JMenuItem toolLibraryFileMenu = new JMenuItem("Cutting Tools");
		toolLibraryFileMenu.setFont(font);
		libraryMenu.add(toolLibraryFileMenu);
		
		JMenuItem carouselLibraryMenu = new JMenuItem("Carousel");
		carouselLibraryMenu.setFont(font);
		libraryMenu.add(carouselLibraryMenu);
		
		JMenuItem materialLibraryFileMenu = new JMenuItem("Materials");
		materialLibraryFileMenu.setFont(font);
		libraryMenu.add(materialLibraryFileMenu);

		JMenuItem billetLibraryFileMenu = new JMenuItem("Billets");
		billetLibraryFileMenu.setFont(font);
		libraryMenu.add(billetLibraryFileMenu);
		
		
		// Simulation menu
		JMenu viewMenu = new JMenu(" Simulation ");
		viewMenu.setFont(font);
		menuBar.add(viewMenu);
		
		JMenuItem dataSimulationMenu = new JMenuItem("Data");
		dataSimulationMenu.setFont(font);
		viewMenu.add(dataSimulationMenu);
		
		JMenuItem controlSimulationMenu = new JMenuItem("Control");
		controlSimulationMenu.setFont(font);
		viewMenu.add(controlSimulationMenu);
		
		JMenuItem resultsSimulationMenu = new JMenuItem("Results");
		resultsSimulationMenu.setFont(font);
		viewMenu.add(resultsSimulationMenu);
		
		JMenuItem logSimulationMenu = new JMenuItem("Log");
		logSimulationMenu.setFont(font);
		viewMenu.add(logSimulationMenu);
		
		
		
		// Learning menu
		JMenu learningMenu = new JMenu(" Learning ");
		learningMenu.setFont(font);
		menuBar.add(learningMenu);

		JMenuItem connectMenu = new JMenuItem("Connect");
		connectMenu.setFont(font);
		learningMenu.add(connectMenu);

		JMenuItem trainMenu = new JMenuItem("Train");
		trainMenu.setFont(font);
		learningMenu.add(trainMenu);

		JMenuItem compareMenu = new JMenuItem("Compare");
		compareMenu.setFont(font);
		learningMenu.add(compareMenu);



		// Help menu
		JMenu helpMenu = new JMenu(" Help ");
		helpMenu.setFont(font);
		
		JMenuItem contentsHelpMenu = new JMenuItem("Contents");
		contentsHelpMenu.setFont(font);
		
		JMenuItem aboutHelpMenu = new JMenuItem("About");
		aboutHelpMenu.setFont(font);
		
		helpMenu.add(contentsHelpMenu);
		helpMenu.add(aboutHelpMenu);
		menuBar.add(helpMenu);
		
		
		/*
		 *  Listeners
		 */
		
		
		// File menu listeners
		ActionListener saveSetupFileMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "saveMenu", "File", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		saveSetupFileMenu.addActionListener(saveSetupFileMenuListener);
		
		ActionListener loadSetupFileMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "loadMenu", "File", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		loadSetupFileMenu.addActionListener(loadSetupFileMenuListener);
		
		ActionListener databaseFileMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showDatabasePanel();
			}
		};
		databaseFileMenu.addActionListener(databaseFileMenuListener);
		
		ActionListener exitFileMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((JFrame) SwingUtilities.getWindowAncestor(menuBar)).dispose();
			}
		};
		exitFileMenu.addActionListener(exitFileMenuListener);

		
		
		//Libraries menu listeners
		ActionListener toolLibraryFileMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showToolLibraryPanel();
			}
		};
		toolLibraryFileMenu.addActionListener(toolLibraryFileMenuListener);
		
		ActionListener materialLibraryFileMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showMaterialLibraryPanel();
			}
		};
		materialLibraryFileMenu.addActionListener(materialLibraryFileMenuListener);
		
		ActionListener billetLibraryFileMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showBilletLibraryPanel();
			}
		};
		billetLibraryFileMenu.addActionListener(billetLibraryFileMenuListener);
		
		
		// Simulation menu listeners
		ActionListener controlViewMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showControlPanel();
			}
		};
		controlSimulationMenu.addActionListener(controlViewMenuListener);
		
		ActionListener processViewMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showProcessPanel();
			}
		};
		dataSimulationMenu.addActionListener(processViewMenuListener);
		
		ActionListener carouselLibraryMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showCarouselPanel();
			}
		};
		carouselLibraryMenu.addActionListener(carouselLibraryMenuListener);
		
		ActionListener resultsViewMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showResultsPanel();
			}
		};
		resultsSimulationMenu.addActionListener(resultsViewMenuListener);
	
		ActionListener machineLogViewMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showMachineLogPanel();
			}
		};
		logSimulationMenu.addActionListener(machineLogViewMenuListener);
		
		
		//Machine Learning menu listeners
		connectMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showDataConnectionPanel();
			}
		});

		trainMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showTrainPanel();

			}
		});

		compareMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBuilder.showComparePanel();

			}
		});
				
				
		// Help menu listeners
		ActionListener contentsHelpMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Show help contents", "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		contentsHelpMenu.addActionListener(contentsHelpMenuListener);
		
		ActionListener aboutHelpMenuListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpPanel.showAbout();
				
			}
		};
		aboutHelpMenu.addActionListener(aboutHelpMenuListener);

	}
}
