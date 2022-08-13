/**
 * 
 */
package uk.ac.cf.milling.gui.learning;

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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.objects.Nc;
import uk.ac.cf.milling.utils.data.IoUtils;
import uk.ac.cf.milling.utils.db.NcUtils;

/**
 * Builds the NC and analysis data connection panel.
 * @author Theocharis Alexopoulos
 *
 */
public class DataConnectionPanel {
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

		// Connection section title
		JLabel lblConnection = new JLabel("Connection");
		lblConnection.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 2;
		constr.insets = new Insets(20, 10, 10, 10);
		panel.add(lblConnection,constr);

		// NC file label
		JLabel lblNcFile = new JLabel("Nc File:");
		lblNcFile.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblNcFile,constr);


		//List of nc files (used for the combobox below)
		List<Nc> ncs = NcUtils.getNcs();
		
		// Combobox for nc file selection
		JComboBox<Nc> cmbNcFile = new JComboBox<Nc>(ncs.stream().toArray( Nc[] :: new ));
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(cmbNcFile, constr);
		
		
		// Connection label
		JLabel lblDataFile = new JLabel("Connection:");
		lblDataFile.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(lblDataFile,constr);
		
		//Data file path text box
		JTextField txtDataFile = new JTextField(30);
		txtDataFile.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(txtDataFile, constr);
		
		// Data connection File browse button
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 2;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(btnBrowse,constr);
		

		// Button connect
		JButton btnConnect = new JButton("Connect");
		btnConnect.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		panel.add(btnConnect,constr);

		
		/* Listeners */
		
		//Keeps the directory path (avoid navigating to the same directory twice)
		JFileChooser chooser = new JFileChooser();
		
		//Browse button listener
		btnBrowse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chooser.getCurrentDirectory() == null){ 
					chooser.setCurrentDirectory(new java.io.File("."));
				}
				chooser.setDialogTitle("Select file");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtDataFile.setText(chooser.getSelectedFile().toString());
					chooser.setCurrentDirectory(chooser.getCurrentDirectory());
				} else {
					System.out.println("No Selection ");
				}
			}
		});
		
		
		//Connect button listener
		btnConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Get selected NC
				//Update the monitoring file path
				Nc nc = (Nc) cmbNcFile.getSelectedItem();
				NcUtils.updateNc(nc.getNcId(), nc.getNcPath(), nc.getAnalysisPath(), txtDataFile.getText(), nc.getBilletId());

				if(IoUtils.checkFileExists(txtDataFile.getText())) {
					System.out.println("Connected to "+ txtDataFile.getText());
				} else {
					System.out.println("Connection set but file does not exist!");
				}
				GUIBuilder.showMachineLogPanel();
			}
		});
		
		panel.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				if (GUIBuilder.refreshDataConnect){
					// Refreshing the combo box
					cmbNcFile.removeAllItems();
					
					List<Nc> ncs = NcUtils.getNcs();
					for (Nc nc:ncs){
						cmbNcFile.addItem(nc);
					}

					System.out.println("Data Connection Panel Refreshed");
					GUIBuilder.refreshDataConnect=false;
				}
			}

			@Override public void componentResized(ComponentEvent e) {}			
			@Override public void componentMoved(ComponentEvent e) {}			
			@Override public void componentHidden(ComponentEvent e) {}
		});
	}
}
