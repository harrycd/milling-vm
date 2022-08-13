/**
 * 
 */
package uk.ac.cf.milling.gui.simulation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import uk.ac.cf.milling.gui.DefaultPanelElements;
import uk.ac.cf.milling.utils.data.IoUtils;
import uk.ac.cf.milling.utils.runnables.ViewProcessRunnable;

/**
 * Builds the process and data analysis panel.
 * @author Theocharis Alexopoulos
 * @date 1 Sep 2020
 *
 */
public class ProcessPanel extends DefaultPanelElements{

	private JPanel panel;

	public JPanel getPanel(){
		createPanel();
		if (panel != null) return panel;
		return new JPanel();
	}

	private void createPanel() {
		panel = new JPanel(new GridBagLayout());
		
		JLabel lblProcess = getDefaultTitleLabel("Process View");
		GridBagConstraints constr = getDefaultConstraints(0, 0);
		panel.add(lblProcess, constr);
		
		JLabel lblDatafile = getDefaultLabel("Datafile:");
		constr = getDefaultConstraints(0, 1, GridBagConstraints.EAST);
		panel.add(lblDatafile, constr);
		
		JTextField txtDatafile = getDefaultTextField(30);
		constr = getDefaultConstraints(1, 1);
		panel.add(txtDatafile, constr);
		
		JButton btnBrowse = getDefaultButton("Browse");
		constr = getDefaultConstraints(2, 1);
		panel.add(btnBrowse, constr);
		
		JLabel lblYAxis = getDefaultLabel("Y Axis:");
		constr = getDefaultConstraints(0, 2, GridBagConstraints.EAST);
		panel.add(lblYAxis, constr);
		
		DefaultListModel<String> listYParamsModel = new DefaultListModel<String>();
		JList<String> listYParams = new JList<>(listYParamsModel);
		listYParams.setFont(getFontPlain());
		constr = getDefaultConstraints(1, 2);
		constr.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane panelParams = new JScrollPane(listYParams);
		panel.add(panelParams, constr);
		
		JLabel lblYSma = getDefaultLabel("Y-SMA:");
		constr = getDefaultConstraints(0, 3, GridBagConstraints.EAST);
		panel.add(lblYSma, constr);
		
		JTextField txtYSma = getDefaultTextField(5);
		txtYSma.setText("1");
		constr = getDefaultConstraints(1, 3, GridBagConstraints.WEST);
		panel.add(txtYSma, constr);
		
		JLabel lblXAxis = getDefaultLabel("X axis");
		constr = getDefaultConstraints(0, 4);
		panel.add(lblXAxis, constr);
		
		JComboBox<String> cmbXParam = new JComboBox<String>(); 
		cmbXParam.setFont(getFontPlain());
		constr = getDefaultConstraints(1, 4);
		panel.add(cmbXParam, constr);
		
		JCheckBox chkScatter = new JCheckBox("Scatter");
		constr = getDefaultConstraints(2, 4);
		panel.add(chkScatter, constr);
		
		JLabel lblXSma = getDefaultLabel("X-SMA:");
		constr = getDefaultConstraints(0, 5, GridBagConstraints.EAST);
		panel.add(lblXSma, constr);
		
		JTextField txtXSma = getDefaultTextField(5);
		txtXSma.setText("1");
		constr = getDefaultConstraints(1, 5, GridBagConstraints.WEST);
		panel.add(txtXSma, constr);
		
		JButton btnView = getDefaultButton("View");
		constr = getDefaultConstraints(2, 6);
		panel.add(btnView, constr);
		
		btnBrowse.addActionListener(getBtnBrowseListener(txtDatafile, listYParamsModel, cmbXParam));
		btnView.addActionListener(getBtnViewListener(txtDatafile, listYParams, cmbXParam, chkScatter, txtXSma, txtYSma));
	}

	/**
	 * @param txtDatafile 
	 * @param listParamsModel 
	 * @param cmbXAxis 
	 * @return
	 */
	private ActionListener getBtnBrowseListener(JTextField txtDatafile, DefaultListModel<String> listParamsModel, JComboBox<String> cmbXAxis) {
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if (chooser.getCurrentDirectory() == null){ 
					chooser.setCurrentDirectory(new java.io.File("."));
				}
				chooser.setDialogTitle("Select file");
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File datafile = chooser.getSelectedFile();
					txtDatafile.setText(datafile.getAbsolutePath());
					chooser.setCurrentDirectory(chooser.getCurrentDirectory());
					updateParamLists(datafile, listParamsModel, cmbXAxis);
				} else {
					System.out.println("No Selection ");
				}
			}
		};
		return listener;
	}
	
	
	
	/**
	 * @param datafile
	 * @param listParamsModel 
	 * @param cmbXAxis 
	 */
	private void updateParamLists(File datafile, DefaultListModel<String> listParamsModel, JComboBox<String> cmbXAxis) {
		String[] titles = IoUtils.getCSVTitles(datafile);
		listParamsModel.clear();
		cmbXAxis.removeAllItems();
		cmbXAxis.addItem("None"); //to use sample index instead
		Arrays.stream(titles).forEach(item -> {
    		listParamsModel.addElement(item);
    		cmbXAxis.addItem(item);
    		});		
	}
	
	/**
	 * @param txtDatafile 
	 * @param listYParams 
	 * @param cmbXParam 
	 * @param chkScatter 
	 * @param txtXSma 
	 * @param txtYSma 
	 * @return
	 */
	private ActionListener getBtnViewListener(
			JTextField txtDatafile, 
			JList<String> listYParams, 
			JComboBox<String> cmbXParam, 
			JCheckBox chkScatter, 
			JTextField txtXSma, 
			JTextField txtYSma) 
	{
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath = txtDatafile.getText();
				List<String> selectedYParameters = listYParams.getSelectedValuesList();
				ViewProcessRunnable runnable = new ViewProcessRunnable();
				runnable.setFilePath(filePath);
				runnable.setYParameters(selectedYParameters);
				runnable.setXParameter((String) cmbXParam.getSelectedItem());
				runnable.setScatterGraph(chkScatter.isSelected());
				runnable.setXSma(Integer.parseInt(txtXSma.getText()));
				runnable.setYSma(Integer.parseInt(txtYSma.getText()));
				Thread t = new Thread(runnable);
				t.start();
			}
		};
		return listener;
	}


}
