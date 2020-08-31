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
import uk.ac.cf.milling.objects.Billet;
import uk.ac.cf.milling.utils.db.BilletUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class BilletLibraryPanel {
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
		
		JLabel lblBilletLibrary= new JLabel("Billet Library :");
		lblBilletLibrary.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(20, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblBilletLibrary,constr);
		
		//Table for available billet setups
		//Retrieve and populate table data
		Vector<String> columnNames = getColumnNames();
		Vector<Vector<String>> data = getBillets();
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
		
		//Configure table
		JTable table = new JTable(tableModel);
		table.setFont(fontPlain);
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridheight = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		table.getColumnModel().removeColumn( table.getColumnModel().getColumn(0));
		panel.add(new JScrollPane(table),constr);
		
		JButton btnNewBillet = new JButton("New");
		btnNewBillet.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnNewBillet,constr);
		
		//Delete  billet button
		JButton btnDeleteBillet = new JButton("Delete");
		btnDeleteBillet.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnDeleteBillet,constr);
		
		
		/*
		 * Listeners
		 */
		
			
		btnNewBillet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUIBuilder.component = table;
				GUIBuilder.showNewBilletPanel();
			}
		});
		
		btnDeleteBillet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				int billetId = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
				BilletUtils.deleteBillet(billetId);
				tableModel.setDataVector(getBillets(), columnNames);
				
				//Inform other panels that they need update
				GUIBuilder.refreshControl = true;
			}
		});
		
		panel.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				if (GUIBuilder.refreshBilletLibrary){
					tableModel.setDataVector(getBillets(), columnNames);
					GUIBuilder.refreshBilletLibrary=false;
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
		columnNames.addElement("BilletId" );
		columnNames.addElement("Description" );
		columnNames.addElement("Xmin" );
		columnNames.addElement("Xmax" );
		columnNames.addElement("Ymin" );
		columnNames.addElement("Ymax" );
		columnNames.addElement("Zmin" );
		columnNames.addElement("Zmax" );
		return columnNames;
	}
	
	/**
	 * Retrieves the list of billets in the library and populates it into a vector
	 * @return Returns a Vector<Vector> with populated data to add to a JTable
	 */
	private static Vector<Vector<String>> getBillets() {
		List<Billet> billets = BilletUtils.getAllBillets();
		Vector<Vector<String>> data = new Vector<Vector<String>>();

		for (Billet billet:billets){
			Vector<String> rowData = new Vector<String>();
			rowData.addElement(String.valueOf(billet.getBilletId()));
			rowData.addElement(billet.getBilletName());
			rowData.addElement(String.valueOf(billet.getXBilletMin()));
			rowData.addElement(String.valueOf(billet.getXBilletMax()));
			rowData.addElement(String.valueOf(billet.getYBilletMin()));
			rowData.addElement(String.valueOf(billet.getYBilletMax()));
			rowData.addElement(String.valueOf(billet.getZBilletMin()));
			rowData.addElement(String.valueOf(billet.getZBilletMax()));
			
			data.add(rowData);
		}
		return data;
	}
	
	public static void updateTable(JTable table){
		table.setModel(new DefaultTableModel(getBillets(), getColumnNames()));
	}
	
	


}
