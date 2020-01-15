/**
 * 
 */
package uk.ac.cf.milling.gui.ml;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.objects.Nc;
import uk.ac.cf.milling.utils.IoUtils;
import uk.ac.cf.milling.utils.NcUtils;
import uk.ac.cf.milling.utils.runnables.MLCompareRunnable;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class ComparePanel {
	private JPanel panel;

	public JPanel getPanel(){
		createPanel();
		if (panel != null) return panel;
		return new JPanel();
	}

	/**
	 * 
	 */
	private void createPanel() {
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints constr = new GridBagConstraints();
		Font fontPlain = new Font("Verdana", Font.PLAIN, 16);
		Font fontTitle = new Font("Verdana", Font.BOLD, 18);

		// Data files section title
		JLabel lblDataFiles = new JLabel("Files to compare");
		lblDataFiles.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 3;
		constr.insets = new Insets(20, 10, 10, 10);
		panel.add(lblDataFiles,constr);

		// Data file1 label
		JLabel lblDataFile1 = new JLabel("File 1:");
		lblDataFile1.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblDataFile1,constr);

		// Combobox for data file type
		String[] dataFileTypes = { "CSV file", "Analysis File" };
		JComboBox<String> cmbDataFileType1 = new JComboBox<String>(dataFileTypes);
		cmbDataFileType1.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(cmbDataFileType1,constr);

		//List of analysis files (used for the comboboxes below)
		String[] analysisFileList = getAnalysisFileList();
		
		// Combobox for analysis file selection
		JComboBox<String> cmbAnalysisFile1 = new JComboBox<String>(analysisFileList);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		cmbAnalysisFile1.setVisible(false);
		panel.add(cmbAnalysisFile1, constr);
		
		//Data file path text box
		JTextField txtDataFile1 = new JTextField(30);
		txtDataFile1.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(txtDataFile1, constr);

		//Data file browse button
		JButton btnBrowse1 = new JButton("Browse");
		btnBrowse1.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 3;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(btnBrowse1,constr);


		
		
		// Data file2 label
		JLabel lblDataFile2 = new JLabel("File 2:");
		lblDataFile2.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblDataFile2,constr);
		
		// Combobox for data file type
		JComboBox<String> cmbDataFileType2 = new JComboBox<String>(dataFileTypes);
		cmbDataFileType2.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(cmbDataFileType2,constr);
		
		// Combobox for analysis file selection
		JComboBox<String> cmbAnalysisFile2 = new JComboBox<String>(analysisFileList);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		cmbAnalysisFile2.setVisible(false);
		panel.add(cmbAnalysisFile2, constr);
		
		//Data file path text box
		JTextField txtDataFile2 = new JTextField(30);
		txtDataFile2.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(txtDataFile2, constr);
		
		// File 2 browse button
		JButton btnBrowse2 = new JButton("Browse");
		btnBrowse2.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 3;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(btnBrowse2,constr);
		

		
		// Synchronisation parameters label
		JLabel lblSynchParamName = new JLabel("Sync Params:");
		lblSynchParamName.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblSynchParamName, constr);
		
		
		DefaultListModel<String> listSynchParamsModel = new DefaultListModel<String>();
		JList<String> listSynchParams = new JList<>(listSynchParamsModel);
		listSynchParams.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		JScrollPane panelSynchParams = new JScrollPane(listSynchParams);
		panel.add(panelSynchParams, constr);
		
		
		/*
		 * This part will be amended
		 */
		// Parameter name label

		JLabel lblParamName = new JLabel("Target Param:");
		lblParamName.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblParamName,constr);
		
		// Parameter name combobox
		String[] paramList = { "Time", "Spindle Speed", "Spindle Load", "X Load", "Y Load", "Z Load", "XYZ Load" };
		JComboBox<String> cmbParam = new JComboBox<String>(paramList);
		cmbParam.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 2;
		constr.gridy = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(cmbParam,constr);
		
		/*
		 * End of part to be amended
		 */
		
		// Parameter name label
		JLabel lblShowDiff = new JLabel("Only difference:");
		lblShowDiff.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 5;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblShowDiff,constr);
		
		JCheckBox chkShowDiff = new JCheckBox("");
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 2;
		constr.gridy = 5;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(chkShowDiff,constr);
		
		// Parameter name label
		JLabel lblSmoothen = new JLabel("Smoothen:");
		lblSmoothen.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 6;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblSmoothen,constr);
		
		//Data file path text box
		JTextField txtMA = new JTextField("1");
		txtMA.setColumns(4);
		txtMA.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 2;
		constr.gridy = 6;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(txtMA, constr);
		
		// Button compare
		JButton btnCompare = new JButton("Compare");
		btnCompare.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 3;
		constr.gridy = 7;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(btnCompare,constr);

		
		/* Listeners */
		
		//Keeps the directory path (avoid navigating to the same directory twice)
		JFileChooser chooser = new JFileChooser();
		
		//File type combobox listener
		cmbDataFileType1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//"CSV file", "Analysis File"
				if (cmbDataFileType1.getSelectedItem().equals("CSV file")){
					txtDataFile1.setVisible(true);
					btnBrowse1.setVisible(true);
					cmbAnalysisFile1.setVisible(false);
				} else {
					txtDataFile1.setVisible(false);
					btnBrowse1.setVisible(false);
					cmbAnalysisFile1.setVisible(true);
				}
			}
		});

		//File type combobox listener
		cmbDataFileType2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//"CSV file", "Analysis File"
				if (cmbDataFileType2.getSelectedItem().equals("CSV file")){
					txtDataFile2.setVisible(true);
					btnBrowse2.setVisible(true);
					cmbAnalysisFile2.setVisible(false);
				} else {
					txtDataFile2.setVisible(false);
					btnBrowse2.setVisible(false);
					cmbAnalysisFile2.setVisible(true);
				}
			}
		});

		//Browse button 1 listener
		
		btnBrowse1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chooser.getCurrentDirectory() == null){ 
					chooser.setCurrentDirectory(new java.io.File("."));
				}
				chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Select file");
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	            	String filePath = chooser.getSelectedFile().toString();
	            	
	            	//Update the text field with the selected file path
	            	txtDataFile1.setText(filePath);
	            	
	            	//Update the list of parameters with the parameters obtained by the selected file
	            	updateParamList(txtDataFile1.getText(), txtDataFile2.getText(), listSynchParamsModel, cmbParam);
	            	
	            } else {
	                System.out.println("No Selection ");
	            }
			}

			
		});
		
		
		//Browse button 2 listener
		btnBrowse2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chooser.getCurrentDirectory() == null){ 
					chooser.setCurrentDirectory(new java.io.File("."));
				}
				chooser.setDialogTitle("Select file");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String filePath = chooser.getSelectedFile().toString();
					txtDataFile2.setText(filePath);
					chooser.setCurrentDirectory(chooser.getCurrentDirectory());
					
					updateParamList(txtDataFile1.getText(), txtDataFile2.getText(), listSynchParamsModel, cmbParam);
					
				} else {
					System.out.println("No Selection ");
				}
			}
		});
		
		
		//Compare button listener
		btnCompare.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbDataFileType1.getSelectedItem().equals("CSV file") && cmbDataFileType2.getSelectedItem().equals("CSV file")){
					MLCompareRunnable compareRunnable = new MLCompareRunnable();
					compareRunnable.setDataFiles(txtDataFile1.getText(), txtDataFile2.getText());
					compareRunnable.setMa(Integer.parseInt(txtMA.getText()));
					compareRunnable.setSynchParams(listSynchParams.getSelectedValuesList());
					compareRunnable.setCompareParam(cmbParam.getSelectedItem().toString());
					compareRunnable.setShowDiff(chkShowDiff.isSelected());
					Thread t = new Thread(compareRunnable);
					t.start();
				}
			}
		});
		
		panel.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				if (GUIBuilder.refreshCompare){
					// Refreshing the combo box
					cmbAnalysisFile1.removeAllItems();
					cmbAnalysisFile2.removeAllItems();
					
					String[] analysisFiles = getAnalysisFileList();
					for (String analysisfile:analysisFiles){
						cmbAnalysisFile1.addItem(analysisfile);
						cmbAnalysisFile2.addItem(analysisfile);
					}

					System.out.println("Compare Panel Refreshed");
					GUIBuilder.refreshCompare=false;
				}
			}

			@Override public void componentResized(ComponentEvent e) {}			
			@Override public void componentMoved(ComponentEvent e) {}			
			@Override public void componentHidden(ComponentEvent e) {}
		});
	}

	/**
	 * @return an Array of Strings containing the file names of the NC analysis files
	 */
	private String[] getAnalysisFileList() {
		List<Nc> ncs = NcUtils.getNcs();
		String[] analysisFileList = new String[ncs.size()];
		for (int i = 0; i < ncs.size(); i++){
			analysisFileList[i] = ncs.get(i).getAnalysisPath();
		}
		return analysisFileList;
	}
	
	/**
	 * @param filePath1 - filepath for first csv file
	 * @param filePath2 - filepath for second csv file
	 * @param listModel - the model of the JList to update
	 * @param combobox 
	 */
	private void updateParamList(String filePath1, String filePath2, DefaultListModel<String> listModel, JComboBox<String> combobox) {
		
		if (filePath1.equals("") || filePath2.equals("")) return;
		
		ArrayList<String> paramTypes1 = new ArrayList<String>(Arrays.asList(IoUtils.getCSVTitles(filePath1)));
		ArrayList<String> paramTypes2 = new ArrayList<String>(Arrays.asList(IoUtils.getCSVTitles(filePath2)));
		
		paramTypes1.retainAll(paramTypes2);
    	
		listModel.clear();
		combobox.removeAllItems();
    	paramTypes1.stream().forEach(item -> {
    		listModel.addElement(item);
    		combobox.addItem(item);
    		});
	}

	
}
