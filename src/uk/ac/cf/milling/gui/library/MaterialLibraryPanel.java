/**
 * 
 */
package uk.ac.cf.milling.gui.library;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.objects.Material;
import uk.ac.cf.milling.utils.db.MaterialUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class MaterialLibraryPanel {
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
		
		JLabel lblMaterialLibrary= new JLabel("Material Library :");
		lblMaterialLibrary.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(20, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblMaterialLibrary,constr);
		
		//Table for materials loaded in carousel
		//Retrieve and populate table data
		Vector<String> columnNames = getColumnNames();
		Vector<Vector<String>> data = getMaterials();
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
		
		//Configure table
		JTable table = new JTable(tableModel);
		table.setFont(fontPlain);
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridheight = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(new JScrollPane(table),constr);
		
		JButton btnNewMaterial = new JButton("New");
		btnNewMaterial.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnNewMaterial,constr);
		
		//Delete  material button
		JButton btnDeleteMaterial = new JButton("Delete");
		btnDeleteMaterial.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnDeleteMaterial,constr);
		
		
		/*
		 * Listeners
		 */
		
			
		btnNewMaterial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUIBuilder.component = table;
				GUIBuilder.showNewMaterialPanel();
			}
		});
		
		btnDeleteMaterial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				int materialId = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
				MaterialUtils.deleteMaterial(materialId);
				tableModel.setDataVector(getMaterials(), columnNames);
				
				//Inform other panels that they need update
				GUIBuilder.refreshControl = true;
			}
		});
		
		panel.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				if (GUIBuilder.refreshMaterialLibrary){
					tableModel.setDataVector(getMaterials(), columnNames);
					GUIBuilder.refreshMaterialLibrary=false;
				}
			}
			
			@Override public void componentResized(ComponentEvent e) {}			
			@Override public void componentMoved(ComponentEvent e) {}			
			@Override public void componentHidden(ComponentEvent e) {}
		});
	}
	
	/*
	 * Other supporting functions
	 */
	
	/**
	 * @return a Vector<String> containing the names of the table columns
	 */
	private static Vector<String> getColumnNames(){
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("MaterialId" );
		columnNames.addElement("Description" );
		columnNames.addElement("Torque Factor");
		return columnNames;
	}
	
	/**
	 * Retrieves the list of materials in the library and populates it into a vector
	 * @return Returns a Vector<Vector> with populated data to add to a JTable
	 */
	private static Vector<Vector<String>> getMaterials() {
		List<Material> materials = MaterialUtils.getAllMaterials();
		Vector<Vector<String>> data = new Vector<Vector<String>>();

		for (Material material:materials){
			Vector<String> rowData = new Vector<String>();
			rowData.addElement(String.valueOf(material.getMaterialId()));
			rowData.addElement(material.getMaterialName());
			rowData.addElement(String.valueOf(material.getTorqueFactor()));
			data.add(rowData);
		}
		return data;
	}
	
	public static void updateTable(JTable table){
		table.setModel(new DefaultTableModel(getMaterials(), getColumnNames()));
	}
	
	


}
