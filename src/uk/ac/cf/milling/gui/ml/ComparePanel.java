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
import java.io.File;
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
import uk.ac.cf.milling.utils.data.IoUtils;
import uk.ac.cf.milling.utils.db.NcUtils;
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
		JComboBox<String> cmbParam = new JComboBox<String>();
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
		
		cmbDataFileType1.addActionListener(getCmbDataFileTypeListener(cmbDataFileType1, txtDataFile1, btnBrowse1, cmbAnalysisFile1));

		cmbDataFileType2.addActionListener(getCmbDataFileTypeListener(cmbDataFileType2, txtDataFile2, btnBrowse2, cmbAnalysisFile2));
		
		btnBrowse1.addActionListener(getBtnBrowse1Listener(chooser, txtDataFile1, txtDataFile2, listSynchParamsModel, cmbParam));
		
		btnBrowse2.addActionListener(getBtnBrowse2Listener(chooser, txtDataFile1, txtDataFile2, listSynchParamsModel, cmbParam));

		btnCompare.addActionListener(getBtnCompareListener(
												cmbDataFileType1, cmbDataFileType2, 
												txtDataFile1, txtDataFile2,
												txtMA, listSynchParams, 
												cmbParam, chkShowDiff));
		
		panel.addComponentListener(getPanelComponentListener(cmbAnalysisFile1, cmbAnalysisFile2));
	}
	

	/**
	 * @param cmbAnalysisFile 
	 * @param btnBrowse 
	 * @param txtDataFile 
	 * @param cmbDataFileType 
	 * @return file type combobox listener
	 */
	private ActionListener getCmbDataFileTypeListener(JComboBox<String> cmbDataFileType, JTextField txtDataFile, JButton btnBrowse, JComboBox<String> cmbAnalysisFile) {
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//"CSV file", "Analysis File"
				if (cmbDataFileType.getSelectedItem().equals("CSV file")){
					txtDataFile.setVisible(true);
					btnBrowse.setVisible(true);
					cmbAnalysisFile.setVisible(false);
				} else {
					txtDataFile.setVisible(false);
					btnBrowse.setVisible(false);
					cmbAnalysisFile.setVisible(true);
				}
			}
		};
		return listener;
	}

	/**
	 * @param chooser 
	 * @param txtDataFile1 
	 * @param cmbParam 
	 * @param listSynchParamsModel 
	 * @return
	 */
	private ActionListener getBtnBrowse1Listener(JFileChooser chooser, JTextField txtDataFile1, JTextField txtDataFile2, DefaultListModel<String> listSynchParamsModel, JComboBox<String> cmbParam) {
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chooser.getCurrentDirectory() == null){ 
					chooser.setCurrentDirectory(new java.io.File("."));
				}
				chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Select file");
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	            	//store the selected file as property of the text field
	            	File benchmarkFile = chooser.getSelectedFile();
	            	File[] dataFiles = (File[]) txtDataFile2.getClientProperty("dataFiles");
	            	
	            	txtDataFile1.putClientProperty("benchmarkFile", benchmarkFile);
	            	
	            	//Update the text field with the selected file path
	            	txtDataFile1.setText(benchmarkFile.toString());
	            	
	            	//Update the list of parameters with the parameters obtained by the selected file
	            	updateParamList(benchmarkFile, dataFiles, listSynchParamsModel, cmbParam);
	            	
	            } else {
	                System.out.println("No Selection ");
	            }
			}

			
		};
		return listener;
	}

	/**
	 * @param cmbParam 
	 * @param listSynchParamsModel 
	 * @param txtDataFile2 
	 * @param chooser 
	 * @return
	 */
	private ActionListener getBtnBrowse2Listener(JFileChooser chooser, JTextField txtDataFile1, JTextField txtDataFile2, DefaultListModel<String> listSynchParamsModel, JComboBox<String> cmbParam) {
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chooser.getCurrentDirectory() == null){ 
					chooser.setCurrentDirectory(new java.io.File("."));
				}
				chooser.setDialogTitle("Select file");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(true);
				
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					
					File benchmarkFile = (File) txtDataFile1.getClientProperty("benchmarkFile");
					File[] dataFiles = chooser.getSelectedFiles();
					
					//store the selected file as property of the text field
					txtDataFile2.putClientProperty("dataFiles", dataFiles);
					
					txtDataFile2.setText(dataFiles.length + " files selected");
					chooser.setCurrentDirectory(chooser.getCurrentDirectory());
					
					updateParamList(benchmarkFile, dataFiles, listSynchParamsModel, cmbParam);
					
				} else {
					System.out.println("No Selection ");
				}
			}
		};
		return listener;
	}

	/**
	 * @param cmbDataFileType1
	 * @param cmbDataFileType2
	 * @param txtDataFile1
	 * @param txtDataFile2
	 * @param txtMA
	 * @param listSynchParams
	 * @param cmbParam
	 * @param chkShowDiff
	 * @return compare button listener
	 */
	private ActionListener getBtnCompareListener(
			JComboBox<String> cmbDataFileType1, 
			JComboBox<String> cmbDataFileType2, 
			JTextField txtDataFile1, 
			JTextField txtDataFile2, 
			JTextField txtMA, 
			JList<String> listSynchParams, 
			JComboBox<String> cmbParam, 
			JCheckBox chkShowDiff) {
		
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbDataFileType1.getSelectedItem().equals("CSV file") && cmbDataFileType2.getSelectedItem().equals("CSV file")){
					File benchmarkFile = (File) txtDataFile1.getClientProperty("benchmarkFile");
					File[] dataFiles = (File[]) txtDataFile2.getClientProperty("dataFiles");
					
					MLCompareRunnable compareRunnable = new MLCompareRunnable();
					compareRunnable.setDataFiles(benchmarkFile, dataFiles);
					compareRunnable.setMa(Integer.parseInt(txtMA.getText()));
					compareRunnable.setSynchParams(listSynchParams.getSelectedValuesList());
					compareRunnable.setCompareParam(cmbParam.getSelectedItem().toString());
					compareRunnable.setShowDiff(chkShowDiff.isSelected());
					Thread t = new Thread(compareRunnable);
					t.start();
				}
			}
		};
		return listener;
	}

	/**
	 * @param cmbAnalysisFile1 
	 * @param cmbAnalysisFile2 
	 * @return refresh panel listener (for comboboxes only)
	 */
	private ComponentListener getPanelComponentListener(JComboBox<String> cmbAnalysisFile1, JComboBox<String> cmbAnalysisFile2) {
		ComponentListener listener = new ComponentListener() {

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
		};
		
		return listener;
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
	private void updateParamList(File benchmarkFile, File[] dataFiles, DefaultListModel<String> listModel, JComboBox<String> combobox) {
		
		if (benchmarkFile == null || dataFiles == null) return;
		
		File[] files = new File[dataFiles.length + 1];
		files[0] = benchmarkFile;
		System.arraycopy(dataFiles, 0, files, 1, dataFiles.length);
		
		String[] titles = IoUtils.getCommonCSVTitles(files);
		
		listModel.clear();
		combobox.removeAllItems();
		Arrays.stream(titles).forEach(item -> {
    		listModel.addElement(item);
    		combobox.addItem(item);
    		});
	}

	
}
