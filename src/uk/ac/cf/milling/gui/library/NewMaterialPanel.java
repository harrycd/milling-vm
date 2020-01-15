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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.utils.MaterialUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class NewMaterialPanel {
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

		JLabel lblNewMaterial = new JLabel("New Material Parameters :");
		lblNewMaterial.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(20, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblNewMaterial,constr);

		JLabel lblMaterialName = new JLabel("Material Name :");
		lblMaterialName.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblMaterialName,constr);

		JTextField txtMaterialName = new JTextField("");
		txtMaterialName.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtMaterialName, constr);

		JLabel lblTorqueFactor = new JLabel("Torque Factor :");
		lblTorqueFactor.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblTorqueFactor,constr);

		JTextField txtTorqueFactor = new JTextField("1");
		txtTorqueFactor.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtTorqueFactor, constr);

		JButton btnSave = new JButton("Save");
		btnSave.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnSave, constr);


		
		/*
		 * Listeners
		 */

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Parse form data (common for all materials)
				String materialName = txtMaterialName.getText();
				double torqueFactor = Double.parseDouble(txtTorqueFactor.getText());
				
				MaterialUtils.addMaterial(materialName, torqueFactor);
				txtMaterialName.setText("");
				txtTorqueFactor.setText("");
				
				GUIBuilder.refreshMaterialLibrary = true;
				GUIBuilder.refreshNewBillet = true;
				GUIBuilder.showMaterialLibraryPanel();
			}
		});

	}

}
