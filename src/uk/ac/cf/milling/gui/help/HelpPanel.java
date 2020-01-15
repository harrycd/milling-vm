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
		panel.add(new JLabel("Help Contents"));
		return panel;
	}
	
	public static void showAbout(){
		StringBuilder sb = new StringBuilder();
		sb.append("Version 0.2.1\n");
		sb.append("Known restrictions / problems:\n");
		sb.append("* All offsets are not supported\n");
		sb.append("* G2 and G3 are supported only for XY plane\n");
		sb.append("* Arc centre in G2 and G3 should be specified in absolute coordinates (not incremental)\n");
		sb.append("* Helical cuts are not supported\n");
		sb.append("* Billet is a cuboid\n");
		sb.append("* Cutting tool types are hard coded (user cannot select tool parameters)\n");
		sb.append("* Collision between cutting tool and billet is not detected\n");
		sb.append("* The cutting tool is homogenous, does not deflect and has infinite number of 0 height teeth.\n");
		sb.append("\n");
		JTextArea message = new JTextArea(sb.toString());
		message.setEditable(false);
		message.setBackground(UIManager.getColor ( "Panel.background" ));
		message.setFont(new Font("Verdana", Font.PLAIN, 16));
		JOptionPane.showMessageDialog(null, message, "Version Info:", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	

}
