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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import uk.ac.cf.milling.gui.GUIBuilder;
import uk.ac.cf.milling.objects.CuttingTool;
import uk.ac.cf.milling.objects.CuttingToolProfile;
import uk.ac.cf.milling.utils.db.CuttingToolProfileUtils;
import uk.ac.cf.milling.utils.db.CuttingToolUtils;
import uk.ac.cf.milling.utils.simulation.ProfileBuilderUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class NewToolPanel {
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

		JLabel lblNewTool = new JLabel("New Tool Parameters :");
		lblNewTool.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(20, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblNewTool,constr);

		JLabel lblToolName = new JLabel("Tool Name :");
		lblToolName.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblToolName,constr);

		JTextField txtToolName = new JTextField("");
		txtToolName.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtToolName, constr);

		JLabel lblToolType = new JLabel("Tool Type :");
		lblToolType.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblToolType,constr);

		String[] toolTypes = { "", CuttingTool.END_MILL, CuttingTool.BALL_NOSE_MILL};
		JComboBox<String> cmbToolType = new JComboBox<String>(toolTypes);
		cmbToolType.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 2;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(cmbToolType,constr);
		
		JLabel lblToolSeries = new JLabel("Tool Series :");
		lblToolSeries.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblToolSeries,constr);

		JTextField txtToolSeries = new JTextField("");
		txtToolSeries.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtToolSeries, constr);

		JLabel lblToolTeeth = new JLabel("Number of teeth :");
		lblToolTeeth.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblToolTeeth,constr);

		JTextField txtToolTeeth = new JTextField("");
		txtToolTeeth.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 4;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtToolTeeth, constr);

		JLabel lblToolLength = new JLabel("Total length :");
		lblToolLength.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 5;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblToolLength,constr);
		
		JTextField txtToolLength = new JTextField("");
		txtToolLength.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 5;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtToolLength, constr);

		JButton btnSave = new JButton("Save");
		btnSave.setFont(fontPlain);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 2;
		constr.gridy = 8;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(btnSave, constr);

		//Tool specific input forms
		
		//Tool height label
		JLabel lblToolHeight = new JLabel("Cutting length :");
		lblToolHeight.setFont(fontPlain);
		lblToolHeight.setVisible(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 6;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblToolHeight,constr);
		
		//Tool height text field
		JTextField txtToolHeight = new JTextField(5);
		txtToolHeight.setFont(fontPlain);
		txtToolHeight.setVisible(false);
		constr = new GridBagConstraints();
		//		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 1;
		constr.gridy = 6;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtToolHeight,constr);

		//Tool diameter label
		JLabel lblToolDiameter = new JLabel("Diameter :");
		lblToolDiameter.setFont(fontPlain);
		lblToolDiameter.setVisible(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 7;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblToolDiameter,constr);

		//Tool diameter text field
		JTextField txtToolDiameter = new JTextField(5);
		txtToolDiameter.setFont(fontPlain);
		txtToolDiameter.setVisible(false);
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 7;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtToolDiameter,constr);

		//Nose radius label
		JLabel lblNoseRadius = new JLabel("Nose Radius :");
		lblNoseRadius.setFont(fontPlain);
		lblNoseRadius.setVisible(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.EAST;
		constr.gridx = 0;
		constr.gridy = 8;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblNoseRadius,constr);
		
		//Tool diameter text field
		JTextField txtNoseRadius = new JTextField(5);
		txtNoseRadius.setFont(fontPlain);
		txtNoseRadius.setVisible(false);
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 8;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(txtNoseRadius,constr);




		/*
		 * Listeners
		 */

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Parse form data (common for all tools)
				String toolName = txtToolName.getText();
				String toolType = cmbToolType.getSelectedItem().toString();
				String toolSeries = txtToolSeries.getText();
				int toolTeeth = Integer.parseInt(txtToolTeeth.getText());
				double toolLength = Double.parseDouble(txtToolLength.getText());
				double toolRadius = 0.5* Double.parseDouble(txtToolDiameter.getText());
				double toolHeight = Double.parseDouble(txtToolHeight.getText());
				
				//Create the cutting tool 
				//Create and add the profile as specified by the tool type
				if (cmbToolType.getSelectedItem().toString().equals(CuttingTool.END_MILL)){
					//Add the tool to the database
					int toolId = CuttingToolUtils.addCuttingTool(toolName, toolType, toolSeries, toolTeeth, toolLength);
					List<CuttingToolProfile> profiles = ProfileBuilderUtils.generateProfileEndMill(toolId, toolHeight, toolRadius);
					CuttingToolProfileUtils.addCuttingToolProfiles(profiles);
				}
				else if (cmbToolType.getSelectedItem().toString().equals(CuttingTool.BALL_NOSE_MILL)){
					double noseRadius = Double.parseDouble(txtNoseRadius.getText());
					int toolId = CuttingToolUtils.addCuttingTool(toolName, toolType, toolSeries, toolTeeth, toolLength);
					List<CuttingToolProfile> profiles = ProfileBuilderUtils.generateProfileBallNoseMill(toolId, toolHeight, toolRadius, noseRadius);
					CuttingToolProfileUtils.addCuttingToolProfiles(profiles);
				}
				
				//TODO Reset the form
				
				//Update the tools table. It is stored at GUIBuilder. Then remove reference.
				ToolLibraryPanel.updateTable((JTable) GUIBuilder.component); 
				GUIBuilder.component = null;

				// Notify panels that need to refresh
				GUIBuilder.refreshCarousel = true;
				GUIBuilder.showToolLibraryPanel();
			}
		});

		cmbToolType.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == 1){
					if(event.getItem().equals("")){
						lblToolHeight.setVisible(false);
						txtToolHeight.setVisible(false);
						lblToolDiameter.setVisible(false);
						txtToolDiameter.setVisible(false);
						lblNoseRadius.setVisible(false);
						txtNoseRadius.setVisible(false);
					}
					else if(event.getItem().equals(CuttingTool.END_MILL)){
						lblToolHeight.setVisible(true);
						txtToolHeight.setVisible(true);
						lblToolDiameter.setVisible(true);
						txtToolDiameter.setVisible(true);
						lblNoseRadius.setVisible(false);
						txtNoseRadius.setVisible(false);
					}
					else if(event.getItem().equals(CuttingTool.BALL_NOSE_MILL)){
						lblToolHeight.setVisible(true);
						txtToolHeight.setVisible(true);
						lblToolDiameter.setVisible(true);
						txtToolDiameter.setVisible(true);
						lblNoseRadius.setVisible(true);
						txtNoseRadius.setVisible(true);
					} 
				}
			}
		});

	}

}
