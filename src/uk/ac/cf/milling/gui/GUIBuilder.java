/**
 * 
 */
package uk.ac.cf.milling.gui;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import uk.ac.cf.milling.gui.file.DatabasePanel;
import uk.ac.cf.milling.gui.library.BilletLibraryPanel;
import uk.ac.cf.milling.gui.library.CarouselPanel;
import uk.ac.cf.milling.gui.library.MaterialLibraryPanel;
import uk.ac.cf.milling.gui.library.NewBilletPanel;
import uk.ac.cf.milling.gui.library.NewMaterialPanel;
import uk.ac.cf.milling.gui.library.NewToolPanel;
import uk.ac.cf.milling.gui.library.ToolLibraryPanel;
import uk.ac.cf.milling.gui.ml.ComparePanel;
import uk.ac.cf.milling.gui.ml.DataConnectionPanel;
import uk.ac.cf.milling.gui.ml.TrainPanel;
import uk.ac.cf.milling.gui.view.ControlPanel;
import uk.ac.cf.milling.gui.view.MachineLogPanel;
import uk.ac.cf.milling.gui.view.ResultsPanel;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class GUIBuilder {
	private static JFrame frame = new JFrame("Milling VM");

	private static CardLayout cardLayout = new CardLayout();
	private static JPanel mainPanel = new JPanel(cardLayout);
	
	private static final String DATABASE = "Database";
	private static final String CONTROL = "Control";
	private static final String CAROUSEL = "Carousel";
	private static final String RESULTS = "Results";
	private static final String TOOL_LIBRARY = "Tool Library";
	private static final String NEW_TOOL = "New Tool";
	private static final String MATERIAL_LIBRARY = "Material Library";
	private static final String NEW_MATERIAL = "New Material";
	private static final String BILLET_LIBRARY = "Billet Library";
	private static final String NEW_BILLET = "New Billet";
	private static final String MACHINE_LOG = "Machine Log";
	private static final String TRAIN = "Train";
	private static final String COMPARE = "Compare";
	private static final String CONNECT = "Connect";
	
	
	// Objects to access from various points
	public static Component component = null; //Store the component to update
	
	// Flags to indicate if the panel needs refreshing
	public static boolean refreshDatabase = false;
	public static boolean refreshControl = false;
	public static boolean refreshCarousel = false;
	public static boolean refreshResults = false;
	public static boolean refreshToolLibrary = false;
	public static boolean refreshNewTool = false;
	public static boolean refreshMaterialLibrary = false;
	public static boolean refreshNewMaterial = false;
	public static boolean refreshBilletLibrary = false;
	public static boolean refreshNewBillet = false;
	public static boolean refreshMachineLog = false;
	public static boolean refreshCompare = false;
	public static boolean refreshDataConnect = false;
	public static boolean refreshTrain = false;
	
	public static void createAndShowGUI(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initially, only the database panel is needed to load a database
		JPanel databasePanel = new DatabasePanel().getDatabasePanel();
		mainPanel.add(databasePanel, DATABASE);
		
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Loads all panels of the GUI after a database has been selected.
	 */
	public static void createAndShowFullGUI(){
		// The menubar
		JMenuBar menubar = new MenuBar().getMenuBar();
		frame.setJMenuBar(menubar);

		// The normal panels
		JPanel controlsPanel = new ControlPanel().getPanel();
		mainPanel.add(controlsPanel, CONTROL);
		
		JPanel carouselPanel = new CarouselPanel().getCarouselPanel();
		mainPanel.add(carouselPanel, CAROUSEL);

		JPanel toolLibraryPanel = new ToolLibraryPanel().getPanel();
		mainPanel.add(toolLibraryPanel, TOOL_LIBRARY);
		
		JPanel newToolPanel = new NewToolPanel().getPanel();
		mainPanel.add(newToolPanel, NEW_TOOL);

		JPanel materialLibraryPanel = new MaterialLibraryPanel().getPanel();
		mainPanel.add(materialLibraryPanel, MATERIAL_LIBRARY);
		
		JPanel newMaterialPanel = new NewMaterialPanel().getPanel();
		mainPanel.add(newMaterialPanel, NEW_MATERIAL);

		JPanel billetLibraryPanel = new BilletLibraryPanel().getPanel();
		mainPanel.add(billetLibraryPanel, BILLET_LIBRARY);
		
		JPanel newBilletPanel = new NewBilletPanel().getPanel();
		mainPanel.add(newBilletPanel, NEW_BILLET);

		JTabbedPane resultsPanel = new ResultsPanel().getResultsPanel();
		mainPanel.add(resultsPanel, RESULTS);

		JPanel machineLogPanel = new MachineLogPanel().getPanel();
		mainPanel.add(machineLogPanel, MACHINE_LOG);

		JPanel trainPanel = new TrainPanel().getPanel();
		mainPanel.add(trainPanel, TRAIN);
		
		JPanel comparePanel = new ComparePanel().getPanel();
		mainPanel.add(comparePanel, COMPARE);

		JPanel dataConnectionPanel = new DataConnectionPanel().getPanel();
		mainPanel.add(dataConnectionPanel, CONNECT);
		
		frame.pack();
		showControlPanel();
	}
	
	/*
	 * The following methods are used to show the relevant panel according to used actions
	 */
	
	public static void showDatabasePanel(){
		cardLayout.show(mainPanel, DATABASE);
	}
	
	public static void showControlPanel(){
		frame.getJMenuBar().setVisible(true);
		cardLayout.show(mainPanel, CONTROL);
	}
	
	public static void showCarouselPanel(){
		cardLayout.show(mainPanel, CAROUSEL);
	}
	
	public static void showToolLibraryPanel(){
		cardLayout.show(mainPanel, TOOL_LIBRARY);
	}
	
	public static void showNewToolPanel(){
		cardLayout.show(mainPanel, NEW_TOOL);
	}
	
	public static void showMaterialLibraryPanel(){
		cardLayout.show(mainPanel, MATERIAL_LIBRARY);
	}
	
	public static void showNewMaterialPanel(){
		cardLayout.show(mainPanel, NEW_MATERIAL);
	}

	public static void showBilletLibraryPanel(){
		cardLayout.show(mainPanel, BILLET_LIBRARY);
	}
	
	public static void showNewBilletPanel(){
		cardLayout.show(mainPanel, NEW_BILLET);
	}
	
	public static void showResultsPanel(){
		cardLayout.show(mainPanel, RESULTS);
	}

	public static void showMachineLogPanel(){
		cardLayout.show(mainPanel, MACHINE_LOG);
	}

	public static void showTrainPanel(){
		cardLayout.show(mainPanel, TRAIN);
	}

	public static void showComparePanel(){
		cardLayout.show(mainPanel, COMPARE);
	}

	public static void showDataConnectionPanel(){
		cardLayout.show(mainPanel, CONNECT);
	}
	
	/*
	 * General utility methods
	 */
	
	public static void packFrame(){
		frame.pack();
	}
	
	public static void exit(){
		frame.dispose();
	}
	
	public static void redrawComponents(){
		frame.revalidate();
		frame.repaint();
	}
}
