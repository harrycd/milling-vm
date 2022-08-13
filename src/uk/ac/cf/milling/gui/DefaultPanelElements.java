/**
 * 
 */
package uk.ac.cf.milling.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Convenient methods that facilitate reuse of GUI elements.
 * @author Theocharis Alexopoulos
 * @date 26 Aug 2020
 *
 */
public class DefaultPanelElements {
	
	protected Font getFontPlain() {
		return new Font("Verdana", Font.PLAIN, 16);
	}

	protected Font getFontTitle() {
		return new Font("Verdana", Font.BOLD, 18);
	}
	
	protected JLabel getDefaultLabel(String labelText) {
		JLabel label = new JLabel(labelText);
		label.setFont(getFontPlain());
		return label;
	}

	protected JLabel getDefaultTitleLabel(String labelText) {
		JLabel label = new JLabel(labelText);
		label.setFont(getFontTitle());
		return label;
	}
	
	protected JTextField getDefaultTextField(int width) {
		JTextField textField = new JTextField(width);
		textField.setFont(getFontPlain());
		return textField;
	}
	
	protected JButton getDefaultButton(String text) {
		JButton button = new JButton(text);
		button.setFont(getFontPlain());
		return button;
	}
	
	protected GridBagConstraints getDefaultConstraints(int gridx, int gridy) {
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = gridx;
		constr.gridy = gridy;
		constr.insets = new Insets(10, 10, 10, 10);
		return constr;
	}

	protected GridBagConstraints getDefaultConstraints(int gridx, int gridy, int anchor) {
		GridBagConstraints constr = getDefaultConstraints(gridx, gridy);
		constr.anchor = anchor;
		return constr;
	}

}
