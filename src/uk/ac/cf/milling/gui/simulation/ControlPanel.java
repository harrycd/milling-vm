/**
 * 
 */
package uk.ac.cf.milling.gui.simulation;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.objects.Billet;
import uk.ac.cf.milling.objects.SimulatorConfig;
import uk.ac.cf.milling.utils.data.DataManipulationUtils;
import uk.ac.cf.milling.utils.data.IoUtils;
import uk.ac.cf.milling.utils.db.BilletUtils;
import uk.ac.cf.milling.utils.db.SettingUtils;
import uk.ac.cf.milling.utils.runnables.SimulateProcessRunnable;

/**
 * Builds the panel that contains simulator setup and run functions.
 * @author Theocharis Alexopoulos
 *
 */
public class ControlPanel {
	private JPanel panel;
	
	public JPanel getPanel(){
		createPanel();
		if (panel != null) return panel;
		return new JPanel();
	}

	/**
	 * @return
	 */
	private void createPanel() {
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints constr = new GridBagConstraints();
		Font fontPlain = new Font("Verdana", Font.PLAIN, 16);
		Font fontTitle = new Font("Verdana", Font.BOLD, 18);
		
		// Input files section title
		JLabel lblInputFiles = new JLabel("Input to Machine");
		lblInputFiles.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 3;
		constr.insets = new Insets(20, 10, 10, 10);
		panel.add(lblInputFiles,constr);

		// Combobox for Input file type
		String[] inputFileTypes = { "GCode file", "CSV file" };
		JComboBox<String> cmbInputFileType = new JComboBox<String>(inputFileTypes);
		cmbInputFileType.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(cmbInputFileType,constr);
		
		//GCode file path text box
		JTextField txtInputFilePath = new JTextField("");
		txtInputFilePath.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.gridwidth = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(txtInputFilePath, constr);
		
		//GCode file browse button
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 5;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(btnBrowse,constr);
		
		// Billet label
		JLabel lblBilletSize = new JLabel("Billet: ");
		lblBilletSize.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 6;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletSize,constr);
		
		
		// Billet combobox
		JComboBox<Billet> cmbBillet = new JComboBox<Billet>(getBillets());
		cmbBillet.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 6;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.gridwidth = 3;
		panel.add(cmbBillet,constr);

				
		
		// Configuration section title
		JLabel lblConfig = new JLabel("Configuration");
		lblConfig.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 7;
		constr.insets = new Insets(20, 10, 10, 10);
		panel.add(lblConfig,constr);
		
		// Element size label
		JLabel lblElemSize = new JLabel("Element size: ");
		lblElemSize.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 8;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblElemSize,constr);
		
		// Element size text box
		JTextField txtElemSize = new JTextField(5);
		txtElemSize.setFont(fontPlain);
		txtElemSize.setMinimumSize(new Dimension(81, 27));
		txtElemSize.setText(SettingUtils.getSetting("elementSize"));
		txtElemSize.setEditable(false);
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 8;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtElemSize, constr);
		
		// Element size units
		JLabel lblElemSizeUnits = new JLabel("mm");
		lblElemSizeUnits.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 2;
		constr.gridy = 8;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblElemSizeUnits,constr);
		
		// Show 3d machined part checkbox
		JCheckBox chkbxShow3dPart = new JCheckBox("Show 3D part: ");
		chkbxShow3dPart.setFont(fontPlain);
		chkbxShow3dPart.setHorizontalTextPosition(SwingConstants.LEFT);
		chkbxShow3dPart.setSelected(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 3;
		constr.gridy = 8;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(chkbxShow3dPart,constr);
		
		// Time step label
//		JLabel lblTimeStep = new JLabel("Time step: ");
//		lblTimeStep.setFont(fontPlain);
//		constr = new GridBagConstraints();
//		constr.anchor = GridBagConstraints.EAST;
//		constr.gridx = 0;
//		constr.gridy = 9;
//		constr.insets = new Insets(10, 10, 10, 10);
//		panel.add(lblTimeStep,constr);
		
		// Time step auto calc button
//		JButton btnTimeStepCalc = new JButton("ơ");
//		btnTimeStepCalc.setFont(fontPlain);
//		btnTimeStepCalc.setVisible(false);
//		constr = new GridBagConstraints();
//		constr.anchor = GridBagConstraints.WEST;
//		constr.gridx = 1;
//		constr.gridy = 9;
//		constr.insets = new Insets(10, 10, 10, 10);
//		panel.add(btnTimeStepCalc, constr);
		
		
		// Time step text box
//		JTextField txtTimeStep = new JTextField(5);
//		txtTimeStep.setFont(fontPlain);
//		txtTimeStep.setEditable(true);
//		txtTimeStep.setMinimumSize(new Dimension(81, 27));
//		txtTimeStep.setText(SettingUtils.getSetting("timeStep"));
//		constr = new GridBagConstraints();
////		constr.anchor = GridBagConstraints.EAST;
//		constr.gridx = 1;
//		constr.gridy = 9;
//		constr.insets = new Insets(10, 10, 10, 10);
//		panel.add(txtTimeStep, constr);
		
		// Time step units label
		JLabel lblTimeStepUnits = new JLabel("sec");
		lblTimeStepUnits.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 2;
		constr.gridy = 9;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblTimeStepUnits,constr);

		// Show 3d machined part checkbox
		JCheckBox chkbxShow2dGraphs = new JCheckBox("Show 2D graphs: ");
		chkbxShow2dGraphs.setFont(fontPlain);
		chkbxShow2dGraphs.setHorizontalTextPosition(SwingConstants.LEFT);
		chkbxShow2dGraphs.setSelected(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 3;
		constr.gridy = 9;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(chkbxShow2dGraphs,constr);

		//Run machine button
		JButton btnRun = new JButton("Run");
		btnRun.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 5;
		constr.gridy = 10;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(btnRun,constr);
		
		// Keep track of last file location (for button browse listener)
		JFileChooser chooser = new JFileChooser("."); 

		/* Listeners */
		btnBrowse.addActionListener(getBtnBrowseListener(txtInputFilePath, cmbInputFileType, chooser));

		btnRun.addActionListener(getBtnRunListener(cmbBillet, txtInputFilePath, cmbInputFileType, chkbxShow3dPart, chkbxShow2dGraphs));

//		TODO Cancel process if it takes too long or other issues.		
//		btnCancel.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				es.shutdownNow();
//				System.out.println("Simulation terminated by user.");
//			}
//		});
		
//		cmbInputFileType.addItemListener(new ItemListener() {
//			
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				if (e.getStateChange() == 1 && !cmbInputFileType.getSelectedItem().equals("GCode file")){
//					btnTimeStepCalc.setVisible(true);
//					JOptionPane.showMessageDialog(null, "Please use autocalc [ơ] button to ensure that the sampling rate of the input file matches the one of the selected database", "Warning:", JOptionPane.WARNING_MESSAGE);
//				} else {
//					btnTimeStepCalc.setVisible(false);
//				}
//			}
//		});
		
		panel.addComponentListener(new ComponentListener() {
			

			@Override
			public void componentShown(ComponentEvent e) {
				if (GUIBuilder.refreshControl){
					// Refreshing the combo box
					cmbBillet.removeAllItems();
					Billet[] billets = getBillets();
					for (Billet billet:billets){
						cmbBillet.addItem(billet);
					}

					System.out.println("Control Refreshed");
					GUIBuilder.refreshControl=false;
				}
			}

			@Override public void componentResized(ComponentEvent e) {}			
			@Override public void componentMoved(ComponentEvent e) {}			
			@Override public void componentHidden(ComponentEvent e) {}
		});
	}
	
	/**
	 * @param chkbxShow2dGraphs 
	 * @param chkbxShow3dPart 
	 * @param cmbInputFileType 
	 * @param txtInputFilePath 
	 * @param cmbBillet 
	 * @param txtTimeStep 
	 * @return
	 */
	private ActionListener getBtnRunListener(JComboBox<Billet> cmbBillet, JTextField txtInputFilePath, JComboBox<String> cmbInputFileType, JCheckBox chkbxShow3dPart, JCheckBox chkbxShow2dGraphs) {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Save timestep setting, 
				Billet billet = (Billet) cmbBillet.getSelectedItem();
				boolean inputOK = checkInputOK();
				
				
				if (inputOK) {
					GUIBuilder.showMachineLogPanel();
					
					// Create the simulator configuration
					SimulatorConfig config = new SimulatorConfig();
					config.setInputFilePath(txtInputFilePath.getText());
					config.setInputFileType(cmbInputFileType.getSelectedItem().toString());
					config.setBillet(billet);
					config.setShow3dPart(chkbxShow3dPart.isSelected());
					config.setShow2dGraphs(chkbxShow2dGraphs.isSelected());
					
					Thread t = new Thread(new SimulateProcessRunnable(config));
					t.start();
				}
			}

			/**
			 * @return
			 */
			private boolean checkInputOK() {
				boolean inputOK = true;
				
				if (cmbInputFileType.getSelectedItem().toString().equals("CSV file")) {
					String[] titles = IoUtils.getCSVTitles(txtInputFilePath.getText());
					if (!DataManipulationUtils.containsEqual(titles, "t")) {
						JOptionPane.showMessageDialog(null, "Time (t) column missing! MRR cannot be calculated", "Insufficient input params:", JOptionPane.ERROR_MESSAGE);
					}
					if (!DataManipulationUtils.containsEqual(titles, "T")) {
						JOptionPane.showMessageDialog(null, "Tool (T) column is missing", "Insufficient input params:", JOptionPane.ERROR_MESSAGE);
						inputOK = false;
					}
					if (!DataManipulationUtils.containsEqual(titles, "X") 
							|| !DataManipulationUtils.containsEqual(titles, "Y") 
							|| !DataManipulationUtils.containsEqual(titles, "Z")) {
						
						JOptionPane.showMessageDialog(null, "Coordinates (X,Y,Z) columns are missing", "Insufficient input params:", JOptionPane.ERROR_MESSAGE);
						inputOK = false;
					}
				}
				
				if (txtInputFilePath.getText().equals("")){
					JOptionPane.showMessageDialog(null, "No input file selected!", "Input file empty:", JOptionPane.ERROR_MESSAGE);
					inputOK = false;
				} 

				if ((Billet) cmbBillet.getSelectedItem() == null){
					JOptionPane.showMessageDialog(null, "No billet selected!", "Billet empty:", JOptionPane.ERROR_MESSAGE);
					inputOK = false;
				}
				return inputOK;
			}
		};
		return listener;
	}

	/**
	 * @param cmbInputFileType 
	 * @param txtInputFilePath 
	 * @return
	 */
	private ActionListener getBtnBrowseListener(JTextField txtInputFilePath, JComboBox<String> cmbInputFileType, JFileChooser chooser) {
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
	            chooser.setDialogTitle("Select GCode file");
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	            	String selectedFile = chooser.getSelectedFile().toString();
	            	txtInputFilePath.setText(selectedFile);
	            	chooser.setCurrentDirectory(chooser.getCurrentDirectory());
	            	
	            	//Auto change to .csv input
	            	if (selectedFile.substring(selectedFile.length()-4).equals(".csv")) {
	            		cmbInputFileType.setSelectedIndex(1);
	            	}
	            } else {
	                System.out.println("No Selection ");
	            }
			}
		};
		return listener;
	}

	/**
	 * @return the list of all available materials
	 */
	private Billet[] getBillets() {
		List<Billet> billets = BilletUtils.getAllBillets();
		return billets.toArray(new Billet[0]);
	}

}
