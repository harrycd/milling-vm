/**
 * 
 */
package uk.ac.cf.milling.gui.help;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * Builds the help section panel.
 * @author Theocharis Alexopoulos
 *
 */
public class HelpPanel {
	
	public JPanel getHelpContentsPanel(){
		JPanel panel = createHelpContentsPanel();
		if (panel != null) return panel;
		return new JPanel();
	}

	private JPanel createHelpContentsPanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Please check Appendix A of Theocharis Alexopoulos' thesis."));
		return panel;
	}
	
	public static void showAbout(){
		StringBuilder sb = new StringBuilder();
		sb.append("Version 0.6.6\n");
		sb.append("Based on the PhD thesis of Theocharis Alexopoulos\n");
		sb.append("Contact email: tacontact@gmail.com\n");
		sb.append("\n");
		JTextArea message = new JTextArea(sb.toString());
		message.setEditable(false);
		message.setBackground(UIManager.getColor ( "Panel.background" ));
		message.setFont(new Font("Verdana", Font.PLAIN, 16));
		JOptionPane.showMessageDialog(null, message, "Version Info:", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	

}
