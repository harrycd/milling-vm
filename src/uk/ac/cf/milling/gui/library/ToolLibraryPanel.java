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
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.objects.CuttingTool;
import uk.ac.cf.milling.utils.CuttingToolUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class ToolLibraryPanel {
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
		
		JLabel lblToolLibrary= new JLabel("Tool Library :");
		lblToolLibrary.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(20, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblToolLibrary,constr);
		
		//Table for tools loaded in carousel
		//Retrieve and populate table data
		Vector<String> columnNames = getColumnNames();
		Vector<Vector<String>> data = getTools();
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
		
		JButton btnNewTool = new JButton("New");
		btnNewTool.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnNewTool,constr);
		
		//Delete cutting tool button
		JButton btnDeleteTool = new JButton("Delete");
		btnDeleteTool.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnDeleteTool,constr);
		
		
		/*
		 * Listeners
		 */
		
			
		btnNewTool.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUIBuilder.component = table;
				GUIBuilder.showNewToolPanel();
			}
		});
		
		btnDeleteTool.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				int toolId = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
				CuttingToolUtils.deleteCuttingTool(toolId);
				tableModel.setDataVector(getTools(), columnNames);
				
				//Inform other panels that they need update
				GUIBuilder.refreshCarousel = true;
			}
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
		columnNames.addElement("ToolId" );
		columnNames.addElement("Description" );
		columnNames.addElement("Type" );
		columnNames.addElement("Teeth");
		columnNames.addElement("Length");
		return columnNames;
	}
	
	/**
	 * Retrieves the list of tools in the library and populates it into a vector
	 * @return Returns a Vector<Vector> with populated data to add to a JTable
	 */
	private static Vector<Vector<String>> getTools() {
		List<CuttingTool> tools = CuttingToolUtils.getAllCuttingTools();
		Vector<Vector<String>> data = new Vector<Vector<String>>();

		for (CuttingTool tool:tools){
			Vector<String> rowData = new Vector<String>();
			rowData.addElement(String.valueOf(tool.getToolId()));
			rowData.addElement(tool.getToolName());
			rowData.addElement(tool.getToolType());
			rowData.addElement(String.valueOf(tool.getToolTeeth()));
			rowData.addElement(String.valueOf(tool.getToolLength()));
			data.add(rowData);
		}
		return data;
	}
	
	public static void updateTable(JTable table){
		table.setModel(new DefaultTableModel(getTools(), getColumnNames()));
	}

}
