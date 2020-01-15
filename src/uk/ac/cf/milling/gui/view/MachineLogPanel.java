/**
 * 
 */
package uk.ac.cf.milling.gui.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import uk.ac.cf.milling.gui.CustomOutputStream;
import uk.ac.cf.milling.utils.SettingUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class MachineLogPanel {
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
		
		// Application log section title
		JLabel lblLog = new JLabel("Machine Log");
		lblLog.setFont(fontTitle);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.gridx = 0;
		constr.gridy = 1;
		constr.insets = new Insets(20, 10, 10, 10);
		constr.weightx = 1;
		panel.add(lblLog,constr);

		//Progress bar
		JProgressBar progressBar = new JProgressBar();
		progressBar.setFont(fontPlain);
		progressBar.setVisible(false);
		constr = new GridBagConstraints();
		constr.anchor = GridBagConstraints.WEST;
		constr.fill = GridBagConstraints.BOTH;
		constr.gridx = 1;
		constr.gridy = 1;
		constr.gridwidth = 3;
		constr.insets = new Insets(10, 10, 10, 10);
		constr.weightx = 1;
		panel.add(progressBar,constr);
		SettingUtils.setProgressBar(progressBar);

		//Log text area
		JTextArea txtaLog = new JTextArea();
		txtaLog.setColumns(40);
		txtaLog.setRows(25);
		txtaLog.setFont(fontPlain);
		txtaLog.setEditable(false);
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.BOTH;
		constr.gridx = 0;
		constr.gridy = 2;
		constr.gridwidth = 5;
		constr.insets = new Insets(20, 10, 10, 10);
		constr.weightx = 1;
		constr.weighty = 1;
		panel.add(new JScrollPane(txtaLog),constr);
		PrintStream printStream = new PrintStream(new CustomOutputStream(txtaLog));
		System.setOut(printStream);
		System.setErr(printStream);
	}
}
