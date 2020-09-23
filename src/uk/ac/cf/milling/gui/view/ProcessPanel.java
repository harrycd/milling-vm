/**
 * 
 */
package uk.ac.cf.milling.gui.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
		
		JLabel lblParameters = getDefaultLabel("Parameters:");
		constr = getDefaultConstraints(0, 2, GridBagConstraints.EAST);
		panel.add(lblParameters, constr);
		
		DefaultListModel<String> listParamsModel = new DefaultListModel<String>();
		JList<String> listParams = new JList<>(listParamsModel);
		listParams.setFont(getFontPlain());
		constr = getDefaultConstraints(1, 2);
		constr.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane panelParams = new JScrollPane(listParams);
		panel.add(panelParams, constr);
		
		JLabel lblSma = getDefaultLabel("SMA:");
		constr = getDefaultConstraints(0, 3, GridBagConstraints.EAST);
		panel.add(lblSma, constr);
		
		JTextField txtMA = getDefaultTextField(5);
		txtMA.setText("1");
		constr = getDefaultConstraints(1, 3, GridBagConstraints.WEST);
		panel.add(txtMA, constr);
		
		JButton btnView = getDefaultButton("View");
		constr = getDefaultConstraints(2, 4);
		panel.add(btnView, constr);
		
		btnBrowse.addActionListener(getBtnBrowseListener(txtDatafile, listParamsModel));
		btnView.addActionListener(getBtnViewListener(txtDatafile, listParams, txtMA));
	}

	/**
	 * @param txtDatafile 
	 * @param listParamsModel 
	 * @param cmbParameter 
	 * @return
	 */
	private ActionListener getBtnBrowseListener(JTextField txtDatafile, DefaultListModel<String> listParamsModel) {
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
					updateParamLists(datafile, listParamsModel);
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
	 */
	private void updateParamLists(File datafile, DefaultListModel<String> listParamsModel) {
		String[] titles = IoUtils.getCSVTitles(datafile);
		listParamsModel.clear();
		Arrays.stream(titles).forEach(item -> {
    		listParamsModel.addElement(item);
    		});		
	}
	
	/**
	 * @param txtDatafile 
	 * @param listParams 
	 * @param txtMA 
	 * @return
	 */
	private ActionListener getBtnViewListener(JTextField txtDatafile, JList<String> listParams, JTextField txtMA) {
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath = txtDatafile.getText();
				List<String> selectedParameters = listParams.getSelectedValuesList();
				ViewProcessRunnable runnable = new ViewProcessRunnable();
				runnable.setFilePath(filePath);
				runnable.setParameterList(selectedParameters);
				runnable.setMa(Integer.parseInt(txtMA.getText()));
				Thread t = new Thread(runnable);
				t.start();
			}
		};
		return listener;
	}


}
