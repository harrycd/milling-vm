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

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.objects.CuttingTool;
import uk.ac.cf.milling.utils.db.CarouselUtils;
import uk.ac.cf.milling.utils.db.CuttingToolUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class CarouselPanel {
	private JPanel panel;
	
	public JPanel getCarouselPanel(){
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
		
		// Carousel section title
		JLabel lblCarousel = new JLabel("Carousel");
		lblCarousel.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(20, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblCarousel,constr);
		
		//Prepare carousel table data
		Vector<String> columnNames = getColumnNames();
		Vector<Vector<String>> data = getTools();
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.setDataVector(data, columnNames);
		
		//Configure table appearance
		JTable table = new JTable(tableModel);
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridheight = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		
		//Create the combobox with available cutting tools
		JComboBox<CuttingTool> cmbTools = new JComboBox<CuttingTool>();
		List<CuttingTool> tools = CuttingToolUtils.getAllCuttingTools();
		cmbTools.addItem(new CuttingTool()); //The option to keep the pocket empty
		for (CuttingTool tool:tools){
			cmbTools.addItem(tool);
		}
		table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cmbTools));

		//Add table to mainPanel
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, constr);
		
		//Hide column toolID
//		TableColumnModel tcm = table.getColumnModel();
//		TableColumn column = table.getColumn("Id");
//		tcm.removeColumn(column);
		
		JButton btnNewPocket = new JButton("New");
		btnNewPocket.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnNewPocket,constr);
		
		JButton btnDeletePocket = new JButton("Delete");
		btnDeletePocket.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnDeletePocket,constr);

		
		/*
		 * Listeners
		 */
		
		cmbTools.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CuttingTool selectedTool = (CuttingTool) cmbTools.getSelectedItem();
				int selectedRow = table.getSelectedRow();
				
				if (selectedTool != null && selectedRow >= 0){
					int currentRowToolId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
					int selectedToolId = selectedTool.getToolId();
					
					if (selectedToolId != currentRowToolId){
						int positionToPutTool = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString());
						int positionCurrentOfSelectedTool = CarouselUtils.getCarouselPocketPosition(selectedToolId);
						
						if (	(selectedToolId == 0) || 
								(selectedToolId != 0 && positionCurrentOfSelectedTool == 0) 
							) {
							CarouselUtils.updateCarouselPocket(positionToPutTool, selectedToolId);
							tableModel.setValueAt(selectedToolId, selectedRow, 0);
							System.out.println("Tool changed");
						} else {
							System.out.println("The tool is already at pocket: " + positionCurrentOfSelectedTool);
							JOptionPane.showMessageDialog(null, "The tool is already at another pocket", "Error:", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}
			}
		});
		
		btnNewPocket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int position = table.getRowCount()+1;
				CarouselUtils.addCarouselPocket(position, 0);
				tableModel.setDataVector(getTools(), columnNames);
				table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cmbTools));
			}
		});
		
		btnDeletePocket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int position = table.getRowCount();
				CarouselUtils.deleteCarouselPocket(position);
				tableModel.setDataVector(getTools(), columnNames);
				table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cmbTools));
			}
		});
		
		panel.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				if (GUIBuilder.refreshCarousel){
					// Refreshing the combo box
					List<CuttingTool> toolsRefresh = CuttingToolUtils.getAllCuttingTools();
					cmbTools.removeAllItems();
					cmbTools.addItem(new CuttingTool()); //The option to keep the pocket empty
					for (CuttingTool tool:toolsRefresh){
						cmbTools.addItem(tool);
					}
					
					System.out.println("Carousel Refresh");
					GUIBuilder.refreshCarousel=false;
				}
			}
			
			@Override public void componentResized(ComponentEvent e) {}			
			@Override public void componentMoved(ComponentEvent e) {}			
			@Override public void componentHidden(ComponentEvent e) {}
		});

	}
	
	/**
	 * @return a Vector<String> containing the names of the table columns
	 */
	private Vector<String> getColumnNames() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("ToolId" );
		columnNames.addElement("Position" );
		columnNames.addElement("Description");
		return columnNames;
	}

	/**
	 * Retrieves the list of tools in the carousel and populates it into a vector
	 * @return Returns a Vector<Vector> with populated data to add to a JTable
	 */
	private Vector<Vector<String>> getTools() {
		List<CuttingTool> tools = CarouselUtils.getAllPockets();
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		int counter = 0;
		
		for (CuttingTool tool:tools){
			Vector<String> rowData = new Vector<String>();
			rowData.addElement(String.valueOf(tool.getToolId()));
			rowData.addElement(String.valueOf(++counter));
			rowData.addElement(tool.getToolName());
			data.addElement(rowData);
		}
		
		return data;
	}
	
	
	

	/**
	 * A custom renderer for the combobox (show correct tool name)
	 * @author Theocharis Alexopoulos
	 * 
	 * based on https://stackoverflow.com/questions/24179793/java-how-to-make-jcombobox-of-non-string-objects-display-string-names
	 * 
	 */
//	Add the following line when creating the combobox
//	cmbTools.setRenderer(new ToolListCellRenderer());

//	Add the class below to implement a custom renderer
//
//	class ToolListCellRenderer extends DefaultListCellRenderer {
//		private static final long serialVersionUID = -4561450335487461782L;
//
//		/* (non-Javadoc)
//		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
//		 */
//		@Override
//		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
//				boolean cellHasFocus) {
//			
//			if (value instanceof CuttingTool) {
//				value = ((CuttingTool)value).getToolName();
//			}
//			
//			return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//		}
//	}
}
