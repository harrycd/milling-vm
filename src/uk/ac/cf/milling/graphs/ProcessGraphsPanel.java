/**
 * 
 */
package uk.ac.cf.milling.graphs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot2d.primitives.Serie2d;
import org.jzy3d.plot3d.primitives.Scatter;

import com.jidesoft.swing.RangeSlider;

import uk.ac.cf.milling.gui.DefaultPanelElements;
import uk.ac.cf.milling.utils.data.ConvertUtils;
import uk.ac.cf.milling.utils.plotting.ColourScatter3D;
import uk.ac.cf.milling.utils.plotting.Plotter2D;

/**
 * Constructs the panels for 2D and 3D graphs.
 * @author Theocharis Alexopoulos
 * @date 21 Sep 2020
 *
 */
public class ProcessGraphsPanel extends DefaultPanelElements {
	List<JPanel> innerPanel2dGraphs = new ArrayList<JPanel>();
	List<JPanel> innerPanel3dGraphs = new ArrayList<JPanel>();

	public JPanel getProcessGraphsPanel(
			double[][] coords, 
			Map<String, double[]> parameters, 
			double[] xAxisParameter, 
			boolean scatterGraph)
	{
		JPanel outerPanel = new JPanel(new BorderLayout());
		outerPanel.setBackground(new Color(0, 0, 200));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		
		innerPanel.add(getPanelControls(coords[0].length));
		
		
		for (Map.Entry<String, double[]> parameter : parameters.entrySet()) {
			//Create the 3d graph of parameter
			JPanel panel3d = ColourScatter3D.getParameter3DColourMap(coords, parameter.getKey(), parameter.getValue());
			panel3d.setPreferredSize(new Dimension(500, 500));
			innerPanel.add(panel3d);
			innerPanel3dGraphs.add(panel3d);
			
			//Create a 2d graph parameter vs sample number
			JPanel panel2d = Plotter2D.get2dPlotPanel(
					parameter.getKey(),
					ConvertUtils.castToFloatArray(xAxisParameter),
					ConvertUtils.castToFloatArray(parameter.getValue()),
					scatterGraph);
			panel2d.setPreferredSize(new Dimension(500, 500));
			innerPanel.add(panel2d);
			innerPanel2dGraphs.add(panel2d);
		}
		
		if (innerPanel != null) {
			JScrollPane scrollPanel = new JScrollPane(innerPanel);
			outerPanel.add(scrollPanel);
		}
		return outerPanel;
	}

	private JPanel getPanelControls(int samplesCount) {
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(0, 0, 0));
		
		
		JTextField txtLow = getDefaultTextField(5);
		GridBagConstraints constr = getDefaultConstraints(0, 0, GridBagConstraints.WEST);
		txtLow.setText("0");
		txtLow.setEnabled(false);
		panel.add(txtLow, constr);
		
		JTextField txtHigh = getDefaultTextField(5);
		txtHigh.setText(String.valueOf(samplesCount));
		txtHigh.setEnabled(false);
		constr = getDefaultConstraints(1, 0, GridBagConstraints.EAST);
		panel.add(txtHigh, constr);
		
		//Slider
		RangeSlider slider = new RangeSlider(0, samplesCount, 0, samplesCount);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(samplesCount/5);
		slider.setMinorTickSpacing(samplesCount/20);
		slider.addChangeListener(getSliderListener(txtLow, txtHigh));
		
		constr = getDefaultConstraints(0, 1);
		constr.gridwidth = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.weightx = 1;
		constr.weighty = 1;
		panel.add(slider, constr);
		
		return panel;
		
	}

	/**
	 * @param txtHigh 
	 * @param txtLow 
	 * @return
	 */
	private ChangeListener getSliderListener(JTextField txtLow, JTextField txtHigh) {
		ChangeListener listener = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				RangeSlider slider = (RangeSlider) e.getSource();
				if(!slider.getValueIsAdjusting()) {
					//Modify the 2d panels
					for (JPanel panel2d : innerPanel2dGraphs) {
						Serie2d series = (Serie2d) panel2d.getClientProperty("series");
						float[] dataX = (float[]) panel2d.getClientProperty("dataX");
						float[] dataY = (float[]) panel2d.getClientProperty("dataY");
						float[] dataXSelected = Arrays.copyOfRange(dataX, slider.getLowValue(), slider.getHighValue());
						float[] dataYSelected = Arrays.copyOfRange(dataY, slider.getLowValue(), slider.getHighValue());
						series.clear();
						series = Plotter2D.populateSeries(series, dataXSelected, dataYSelected);
					}
					
					//Modify the 3d panels
					for (JPanel panel3d : innerPanel3dGraphs) {
						Scatter scatter = (Scatter) panel3d.getClientProperty("scatter");
						org.jzy3d.colors.Color[] colours = (org.jzy3d.colors.Color[]) panel3d.getClientProperty("colours");
						Coord3d[] coordinates = (Coord3d[]) panel3d.getClientProperty("coordinates");
						
						Coord3d[] coordinatesPart = Arrays.copyOfRange(coordinates, slider.getLowValue(), slider.getHighValue());
						org.jzy3d.colors.Color[] coloursPart = Arrays.copyOfRange(colours, slider.getLowValue(), slider.getHighValue());
						scatter.coordinates = coordinatesPart;
						scatter.setColors(coloursPart);
						
					}
				}
				txtLow.setText(String.valueOf(slider.getLowValue()));
				txtHigh.setText(String.valueOf(slider.getHighValue()));
			}
		};
		return listener;
	}

}
