/**
 * 
 */
package uk.ac.cf.milling.gui.view;

import java.awt.Component;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import uk.ac.cf.milling.gui.ButtonTabComponent;
import uk.ac.cf.milling.utils.PlotterUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class ResultsPanel {
private static JTabbedPane tabbedPanel;
	
	public JTabbedPane getResultsPanel(){
		createPanel();
		if (tabbedPanel != null) return tabbedPanel;
		return new JTabbedPane();
	}

	/**
	 * 
	 */
	private void createPanel() {
		tabbedPanel = new JTabbedPane();
		tabbedPanel.addContainerListener(new ContainerListener() {
			
			@Override
			public void componentRemoved(ContainerEvent e) {
				String name = e.getChild().getName();
				if (name != null) {
					PlotterUtils.disposeAttachedCharts(name);
					System.out.println("Removed: " + name);
				} else {
					System.out.println("Closed tab without removing references.");
				}
			}
			
			@Override
			public void componentAdded(ContainerEvent e) {
				Component component = e.getChild();
				System.out.println("Added chart panel with ID: " + component.getName());
			}
		});
	}
	
	public static void addTab(String title, JPanel tabPanel){
		tabbedPanel.insertTab(title, null, tabPanel, title, 0);
		tabbedPanel.setTabComponentAt(0, new ButtonTabComponent(tabbedPanel));
	}
	
	public static void addTab(String title, JScrollPane tabPanel){
		int position = tabbedPanel.getTabCount();
		tabbedPanel.insertTab(title, null, tabPanel, title, position);
		tabbedPanel.setTabComponentAt(position, new ButtonTabComponent(tabbedPanel));
		tabbedPanel.setSelectedIndex(position);
	}

}
