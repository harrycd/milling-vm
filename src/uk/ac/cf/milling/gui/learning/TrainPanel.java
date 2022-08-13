/**
 * 
 */
package uk.ac.cf.milling.gui.learning;

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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import uk.ac.cf.milling.gui.DefaultPanelElements;
import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.objects.Material;
import uk.ac.cf.milling.utils.data.IoUtils;
import uk.ac.cf.milling.utils.db.MaterialUtils;
import uk.ac.cf.milling.utils.runnables.MLTrainRunnable;

/**
 * Builds the train model panel.
 * @author Theocharis Alexopoulos
 *
 */
public class TrainPanel extends DefaultPanelElements{
private JPanel panel;
	
	public JPanel getPanel(){
		createPanel();
		if (panel != null) return panel;
		return new JPanel();
	}

	private void createPanel() {
		panel = new JPanel(new GridBagLayout());
		
		// Train section title
		JLabel lblConnection = getDefaultTitleLabel("Train");
		GridBagConstraints constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 2;
		constr.insets = new Insets(20, 10, 10, 10);
		panel.add(lblConnection,constr);
		
		// Material label
		JLabel lblMaterial = getDefaultLabel("Material");
		constr = getDefaultConstraints(0, 1, GridBagConstraints.EAST);
		panel.add(lblMaterial, constr);
		
		// Materials combobox
		JComboBox<Material> cmbBilletMaterial = new JComboBox<Material>(getMaterials());
		cmbBilletMaterial.setFont(getFontPlain());
		constr = getDefaultConstraints(1, 1);
		constr.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cmbBilletMaterial, constr);

		// Data Files label
		JLabel lblDataFiles = getDefaultLabel("Data Files:");
		constr = getDefaultConstraints(0, 2, GridBagConstraints.EAST);
		panel.add(lblDataFiles,constr);
		
		//Data files text box
		JTextField txtDataFiles = getDefaultTextField(30);
		constr = getDefaultConstraints(1, 2);
		constr.fill = GridBagConstraints.HORIZONTAL;
		panel.add(txtDataFiles, constr);
		
		// Data File browse button
		JButton btnBrowse = getDefaultButton("Browse");
		constr = getDefaultConstraints(2, 2, GridBagConstraints.WEST);
		panel.add(btnBrowse,constr);
		
		// Inputs label
		JLabel lblInputs = getDefaultLabel("Inputs:");
		constr = getDefaultConstraints(0, 3, GridBagConstraints.EAST);
		panel.add(lblInputs, constr);
		
		// Inputs list
		DefaultListModel<String> lstInputsModel = new DefaultListModel<String>();
		JList<String> listInputs = new JList<String>(lstInputsModel);
		listInputs.setFont(getFontPlain());
		constr = getDefaultConstraints(1, 3);
		constr.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane panelInputs = new JScrollPane(listInputs);
		panel.add(panelInputs, constr);
		
		// Targets label
		JLabel lblTargets = getDefaultLabel("Targets");
		constr = getDefaultConstraints(0, 4, GridBagConstraints.EAST);
		panel.add(lblTargets, constr);
		
		// Targets list
		DefaultListModel<String> lstTargetsModel = new DefaultListModel<String>();
		JList<String> listTargets = new JList<String>(lstTargetsModel);
		listTargets.setFont(getFontPlain());
		constr = getDefaultConstraints(1, 4);
		constr.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane panelTargets = new JScrollPane(listTargets);
		panel.add(panelTargets, constr);
		
		// Button train
		JButton btnTrain = getDefaultButton("Train");
		constr = getDefaultConstraints(2, 5);
		constr.fill = GridBagConstraints.HORIZONTAL;
		panel.add(btnTrain,constr);

		/* Listeners */
		
		btnBrowse.addActionListener(getBtnBrowseListener(txtDataFiles, lstInputsModel, lstTargetsModel));
		btnTrain.addActionListener(getBtnTrainActionListener(cmbBilletMaterial, txtDataFiles, listInputs, listTargets));
		panel.addComponentListener(getRefreshPanelListener(cmbBilletMaterial));
	}

	/**
	 * @param txtDataFile 
	 * @return
	 */
	private ActionListener getBtnBrowseListener(JTextField txtDataFile, DefaultListModel<String> lstInputsModel, DefaultListModel<String> lstTargetsModel) {
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if (chooser.getCurrentDirectory() == null){ 
					chooser.setCurrentDirectory(new java.io.File("."));
				}
				chooser.setDialogTitle("Select file");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setMultiSelectionEnabled(true);
				
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File[] dataFiles = chooser.getSelectedFiles();
					txtDataFile.setText(dataFiles.length + " files selected");
					txtDataFile.putClientProperty("dataFiles", dataFiles);
					chooser.setCurrentDirectory(chooser.getCurrentDirectory());
					updateParamLists(dataFiles, lstInputsModel, lstTargetsModel);
				} else {
					System.out.println("No Selection ");
				}
			}
		};
		return listener;
	}
	
	/**
	 * @param txtDataFile 
	 * @return
	 */
	private ActionListener getBtnTrainActionListener(
			JComboBox<Material> cmbMaterials, 
			JTextField txtDataFiles, 
			JList<String> listInputs, 
			JList<String> listTargets) 
	{
		
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MLTrainRunnable runnable = new MLTrainRunnable();
				runnable.setMaterialId( ( (Material)cmbMaterials.getSelectedItem() ).getMaterialId() );
				runnable.setDataFiles( (File[]) txtDataFiles.getClientProperty("dataFiles") );
				runnable.setInputNames(listInputs.getSelectedValuesList().toArray(new String[0]));
				runnable.setTargetNames(listTargets.getSelectedValuesList().toArray(new String[0]));
				
				Thread t = new Thread(runnable);
				t.start();
				GUIBuilder.showMachineLogPanel();
			}
		};
		return listener;
	}
	
	private ComponentListener getRefreshPanelListener(JComboBox<Material> cmbBilletMaterial) {
		ComponentListener listener = new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				if (GUIBuilder.refreshTrain){
					// Refreshing the material combo box
					cmbBilletMaterial.removeAllItems();
					Material[] materials = getMaterials();
					for (Material material:materials){
						cmbBilletMaterial.addItem(material);
					}
					GUIBuilder.refreshTrain = false;
				}
			}
			
			@Override public void componentResized(ComponentEvent e) {}			
			@Override public void componentMoved(ComponentEvent e) {}			
			@Override public void componentHidden(ComponentEvent e) {}
		};
		return listener;
	}

	/**
	 * 
	 */
	protected void updateParamLists(File[] dataFiles, DefaultListModel<String> lstInputsModel, DefaultListModel<String> lstTargetsModel) {
			
			if (dataFiles == null) return;
			
			String[] titles = IoUtils.getCommonCSVTitles(dataFiles);
			
			lstInputsModel.clear();
			lstTargetsModel.clear();
			Arrays.stream(titles).forEach(item -> {
				lstInputsModel.addElement(item);
				lstTargetsModel.addElement(item);
	    		});
	}

	/**
	 * @return the list of all available materials
	 */
	private Material[] getMaterials() {
		List<Material> materials = MaterialUtils.getAllMaterials();
		return materials.toArray(new Material[0]);
	}

}
