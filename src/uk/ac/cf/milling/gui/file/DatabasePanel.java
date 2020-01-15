/**
 * 
 */
package uk.ac.cf.milling.gui.file;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.utils.DatabaseUtils;
import uk.ac.cf.milling.utils.SettingUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class DatabasePanel {
	private JPanel panel;

	public JPanel getDatabasePanel(){
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

		JLabel lblDatabase = new JLabel("Database :");
		lblDatabase.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(20, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblDatabase,constr);

		//Database file text box label
		JLabel lblSelected = new JLabel("Selected: ");
		lblSelected.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblSelected,constr);

		//Database file text box
		JTextField txtSelectedDatabase = new JTextField(30);
		txtSelectedDatabase.setFont(fontTitle);
		txtSelectedDatabase.setEditable(false);
		txtSelectedDatabase.setText("Please select a database...");
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtSelectedDatabase,constr);

		//New database button
		JButton btnNewDatabase = new JButton("New");
		btnNewDatabase.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnNewDatabase,constr);

		//Open existing database button 
		JButton btnOpenDatabase = new JButton("Open");
		btnOpenDatabase.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.CENTER;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnOpenDatabase,constr);

		//Save the new database button
		JButton btnSaveDatabase = new JButton("Save");
		btnSaveDatabase.setFont(fontPlain);
		btnSaveDatabase.setVisible(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnSaveDatabase,constr);

		//Element size text box label
		JLabel lblElementSize = new JLabel("Element Size (mm) : ");
		lblElementSize.setFont(fontPlain);
		lblElementSize.setVisible(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblElementSize,constr);

		//Database file text box
		JTextField txtElementSize = new JTextField(5);
		txtElementSize.setFont(fontTitle);
		txtElementSize.setText("0.3");
		txtElementSize.setVisible(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtElementSize, constr);

		//Element size text box label
		JLabel lblTimeStep = new JLabel("Time step (sec) : ");
		lblTimeStep.setFont(fontPlain);
		lblTimeStep.setVisible(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblTimeStep, constr);
		
		//Database file text box
		JTextField txtTimeStep = new JTextField(5);
		txtTimeStep.setFont(fontTitle);
		txtTimeStep.setText("1.0");
		txtTimeStep.setVisible(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
//		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtTimeStep, constr);





		/* Listeners */

		ActionListener btnNewDatabaseListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtSelectedDatabase.setText("");
				txtSelectedDatabase.setColumns(25);
				txtSelectedDatabase.setEditable(true);
				btnSaveDatabase.setVisible(true);
				lblElementSize.setVisible(true);
				txtElementSize.setVisible(true);
				lblTimeStep.setVisible(true);
				txtTimeStep.setVisible(true);
				GUIBuilder.packFrame();
			}
		};
		btnNewDatabase.addActionListener(btnNewDatabaseListener);


		ActionListener btnOpenDatabaseListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select database file");
				chooser.setFileFilter(new FileNameExtensionFilter("db file", "db"));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					
					SettingUtils.setDbFilePath(chooser.getSelectedFile().toString());
					txtSelectedDatabase.setText(chooser.getSelectedFile().toString());
					GUIBuilder.createAndShowFullGUI();
				} else {
					System.out.println("No Selection ");
				}
			}
		};
		btnOpenDatabase.addActionListener(btnOpenDatabaseListener);


		ActionListener btnSaveDatabaseListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String dbName = txtSelectedDatabase.getText();
				if (!dbName.substring(dbName.length()-3).equals(".db")){
					txtSelectedDatabase.setText(dbName + ".db");
				}
				txtSelectedDatabase.setEditable(false);
				btnSaveDatabase.setVisible(false);
				lblElementSize.setVisible(false);
				txtElementSize.setVisible(false);
				lblTimeStep.setVisible(false);
				txtTimeStep.setVisible(false);
				SettingUtils.setDbFilePath(txtSelectedDatabase.getText());
				DatabaseUtils.createDB(txtSelectedDatabase.getText(), txtElementSize.getText(), txtTimeStep.getText());
				GUIBuilder.createAndShowFullGUI();
			}
		};
		btnSaveDatabase.addActionListener(btnSaveDatabaseListener);

	}




}
