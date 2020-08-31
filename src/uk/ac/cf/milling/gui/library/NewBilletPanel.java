/**
 * 
 */
package uk.ac.cf.milling.gui.library;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.objects.Billet;
import uk.ac.cf.milling.objects.Material;
import uk.ac.cf.milling.utils.db.BilletUtils;
import uk.ac.cf.milling.utils.db.MaterialUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class NewBilletPanel {
	private JPanel panel;

	public JPanel getPanel(){
		createPanel();
		if (panel != null) return panel;
		return new JPanel();
	}

	/**
	 * Create the panel and add components
	 */
	private void createPanel() {
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints constr = new GridBagConstraints();
		Font fontPlain = new Font("Verdana", Font.PLAIN, 16);
		Font fontTitle = new Font("Verdana", Font.BOLD, 18);

		
		// Billet section title
		JLabel lblBillet = new JLabel("Billet (mm)");
		lblBillet.setFont(fontTitle);
		constr.anchor = GridBagConstraints.WEST;
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(20, 10, 10, 10);
		panel.add(lblBillet,constr);
		
		// Billet name label
		JLabel lblBilletName = new JLabel("Description: ");
		lblBilletName.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletName,constr);
		
		// Billet name text box
		JTextField txtBilletName = new JTextField("", 20);
		txtBilletName.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.gridwidth = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(txtBilletName, constr);

		// Billet shape label
		JLabel lblBilletShape = new JLabel("Shape: ");
		lblBilletShape.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletShape,constr);
		
		// Billet shape combobox
		JComboBox<String> cmbBilletShape = new JComboBox<String>(getShapes());
		cmbBilletShape.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.gridwidth = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(cmbBilletShape,constr);
		
		/*
		 * Min/max GUI option
		 */
		
		// Billet x min label
		JLabel lblBilletXmin = new JLabel("X min: ");
		lblBilletXmin.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletXmin,constr);
		lblBilletXmin.setVisible(false);
		
		// Billet x min text box
		JTextField txtBilletXmin = new JTextField("0", 5);
		txtBilletXmin.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
//		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(txtBilletXmin, constr);
		txtBilletXmin.setVisible(false);
		
		// Billet x max label
		JLabel lblBilletXmax = new JLabel("X max: ");
		lblBilletXmax.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 2;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletXmax,constr);
		lblBilletXmax.setVisible(false);
		
		// Billet x max text box
		JTextField txtBilletXmax = new JTextField("50", 5);
		txtBilletXmax.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
//		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 3;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(txtBilletXmax, constr);
		txtBilletXmax.setVisible(false);
		
		// Billet Y min label
		JLabel lblBilletYmin = new JLabel("Y min: ");
		lblBilletYmin.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletYmin,constr);
		lblBilletYmin.setVisible(false);
		
		// Billet Y min text box
		JTextField txtBilletYmin = new JTextField("0", 5);
		txtBilletYmin.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 1;
		constr.gridy = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtBilletYmin, constr);
		txtBilletYmin.setVisible(false);
		
		// Billet Y max label
		JLabel lblBilletYmax = new JLabel("Y max: ");
		lblBilletYmax.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 2;
		constr.gridy = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletYmax,constr);
		lblBilletYmax.setVisible(false);
		
		// Billet Y max text box
		JTextField txtBilletYmax = new JTextField("50", 5);
		txtBilletYmax.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 3;
		constr.gridy = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtBilletYmax, constr);
		txtBilletYmax.setVisible(false);
		
		// Billet Z min label
		JLabel lblBilletZmin = new JLabel("Z min: ");
		lblBilletZmin.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 5;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletZmin,constr);
		lblBilletZmin.setVisible(false);
		
		// Billet Z min text box
		JTextField txtBilletZmin = new JTextField("0", 5);
		txtBilletZmin.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 1;
		constr.gridy = 5;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtBilletZmin, constr);
		txtBilletZmin.setVisible(false);
		
		// Billet Z max label
		JLabel lblBilletZmax = new JLabel("Z max: ");
		lblBilletZmax.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 2;
		constr.gridy = 5;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletZmax,constr);
		lblBilletZmax.setVisible(false);
		
		// Billet Z max text box
		JTextField txtBilletZmax = new JTextField("20", 5);
		txtBilletZmax.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 3;
		constr.gridy = 5;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtBilletZmax, constr);
		txtBilletZmax.setVisible(false);
		
		/*
		 * Complex billet option
		 */
		//Available billets label
		JLabel lblBillets = new JLabel("Billets: ");
		lblBillets.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBillets,constr);
		lblBillets.setVisible(false);
		
		//Available billets combobox
		JComboBox<Billet> cmbBillets = new JComboBox<Billet>(getBillets());
		cmbBillets.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 3;
		constr.gridwidth = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(cmbBillets,constr);
		cmbBillets.setVisible(false);
		
		//Add billet to mesh button
		JButton btnAddMesh = new JButton("Add");
		btnAddMesh.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 4;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnAddMesh, constr);
		btnAddMesh.setVisible(false);
		
		
		//Create table
		String[] columnNames = new String[] {"Billet Name"};
		String[][] data = new String[][] {};
		DefaultTableModel tblMeshModel = new DefaultTableModel();
		tblMeshModel.setDataVector(data, columnNames);
		JTable tblMesh = new JTable(tblMeshModel);
		tblMesh.setFont(fontPlain);
				
				
		//Add table to Scroll pane and all in mainPanel
		JScrollPane billetScrollPane = new JScrollPane(tblMesh);
		billetScrollPane.setMinimumSize(new Dimension(100, 100));
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 4;
		constr.gridheight = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(billetScrollPane, constr);
		billetScrollPane.setVisible(false);
		
		JButton btnRemoveMesh = new JButton("Remove");
		btnRemoveMesh.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 4;
		constr.gridy = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnRemoveMesh, constr);
		btnRemoveMesh.setVisible(false);
		/*
		 * End of options
		 */

		// Billet material label
		JLabel lblBilletMaterial = new JLabel("Material: ");
		lblBilletMaterial.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 6;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblBilletMaterial,constr);
		
		// Billet material combobox
		JComboBox<Material> cmbBilletMaterial = new JComboBox<Material>(getMaterials());
		cmbBilletMaterial.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 6;
		constr.gridwidth = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(cmbBilletMaterial,constr);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 4;
		constr.gridy = 7;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnSave, constr);


		
		/*
		 * Listeners
		 */

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String billetName = txtBilletName.getText();
				Material material = (Material) cmbBilletMaterial.getSelectedItem();

				if (cmbBilletShape.getSelectedIndex() == Billet.CYLINDRICAL || cmbBilletShape.getSelectedIndex() == Billet.RECTANGULAR) {
					// Create billet
					int billetShape = cmbBilletShape.getSelectedIndex(); //Indexes match billetShape id
					double billetXMin = Double.parseDouble(txtBilletXmin.getText());
					double billetXMax = Double.parseDouble(txtBilletXmax.getText());
					double billetYMin = Double.parseDouble(txtBilletYmin.getText());
					double billetYMax = Double.parseDouble(txtBilletYmax.getText());
					double billetZMin = Double.parseDouble(txtBilletZmin.getText());
					double billetZMax = Double.parseDouble(txtBilletZmax.getText());
					
					BilletUtils.addBillet(billetName, billetShape, material.getMaterialId(), billetXMin, billetXMax, billetYMin, billetYMax, billetZMin, billetZMax);
				}
				
				if (cmbBilletShape.getSelectedIndex() == Billet.COMPLEX) {
					List<Billet> billets = new ArrayList<Billet>();
					
					for (int row = 0; row < tblMeshModel.getRowCount(); row++) {
						Billet billet = (Billet) tblMeshModel.getValueAt(row, 0);
						billets.add(billet);
					}
					BilletUtils.addComplexBillet(billetName, billets, material.getMaterialId());
				}

				
				GUIBuilder.refreshBilletLibrary = true;
				GUIBuilder.refreshNewBillet = true;
				GUIBuilder.refreshControl = true;
				GUIBuilder.showBilletLibraryPanel();
			}
		});
		
		btnAddMesh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Billet billet = (Billet) cmbBillets.getSelectedItem();
				tblMeshModel.addRow(new Billet[] {billet});
			}
		});
		
		btnRemoveMesh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tblMeshModel.removeRow(tblMesh.getSelectedRow());
			}
		});
		
		cmbBilletShape.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean showComplexFields = cmbBilletShape.getSelectedIndex() == Billet.COMPLEX;
				
				boolean showMinMaxFields =  cmbBilletShape.getSelectedIndex() == Billet.CYLINDRICAL ||
											cmbBilletShape.getSelectedIndex() == Billet.RECTANGULAR;
				//Show/hide min max labels and boxes
				txtBilletXmin.setVisible(showMinMaxFields);
				txtBilletXmax.setVisible(showMinMaxFields);
				txtBilletYmin.setVisible(showMinMaxFields);
				txtBilletYmax.setVisible(showMinMaxFields);
				txtBilletZmin.setVisible(showMinMaxFields);
				txtBilletZmax.setVisible(showMinMaxFields);
				lblBilletXmin.setVisible(showMinMaxFields);
				lblBilletXmax.setVisible(showMinMaxFields);
				lblBilletYmin.setVisible(showMinMaxFields);
				lblBilletYmax.setVisible(showMinMaxFields);
				lblBilletZmin.setVisible(showMinMaxFields);
				lblBilletZmax.setVisible(showMinMaxFields);
				
				//Show/hide complex billet constructor
				lblBillets.setVisible(showComplexFields);
				cmbBillets.setVisible(showComplexFields);
				btnAddMesh.setVisible(showComplexFields);
				btnRemoveMesh.setVisible(showComplexFields);
				billetScrollPane.setVisible(showComplexFields);
				
			}
		});
		
		panel.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				if (GUIBuilder.refreshNewBillet){
					//Refreshing the available billets combobox
					cmbBillets.removeAllItems();
					Billet[] billets = getBillets();
					for (Billet billet : billets) {
						cmbBillets.addItem(billet);
					}
					
					// Refreshing the material combo box
					cmbBilletMaterial.removeAllItems();
					Material[] materials = getMaterials();
					for (Material material:materials){
						cmbBilletMaterial.addItem(material);
					}

					System.out.println("Billet Materials Refreshed");
					GUIBuilder.refreshNewBillet=false;
				}
			}

			@Override public void componentResized(ComponentEvent e) {}			
			@Override public void componentMoved(ComponentEvent e) {}			
			@Override public void componentHidden(ComponentEvent e) {}
		});
		
		FocusListener focusListener = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
				if (component instanceof JTextField){
					JTextField txtOnFocus = (JTextField) component;
					txtOnFocus.select(0, 0);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
				if (component instanceof JTextField){
					JTextField txtOnFocus = (JTextField) component;
					txtOnFocus.select(0, txtOnFocus.getText().length());
				}
			}
		};
		txtBilletXmin.addFocusListener(focusListener);
		txtBilletXmax.addFocusListener(focusListener);
		txtBilletYmin.addFocusListener(focusListener);
		txtBilletYmax.addFocusListener(focusListener);
		txtBilletZmin.addFocusListener(focusListener);
		txtBilletZmax.addFocusListener(focusListener);

	}
	
	/**
	 * @return names of all billets in the database
	 */
	private Billet[] getBillets() {
		List<Billet> billets = BilletUtils.getAllBillets();
		return billets.toArray(new Billet[0]);
	}

	/**
	 * @return the list of all available materials
	 */
	private Material[] getMaterials() {
		List<Material> materials = MaterialUtils.getAllMaterials();
		return materials.toArray(new Material[0]);
	}
	
	/**
	 * @return list of all available shapes
	 */
	private String[] getShapes() {
		return new String[] {"Undefined", "Complex", "Rectangular", "Cylindrical"};
	}

}
