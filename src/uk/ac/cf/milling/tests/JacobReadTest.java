/**
 * 
 */
package uk.ac.cf.milling.tests;

import uk.ac.cf.milling.objects.KPIs;
import uk.ac.cf.milling.utils.data.DataManipulationUtils;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class JacobReadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		KPIs kpis = DataManipulationUtils.parseDataFile("C:\\Users\\Alexo\\OneDrive\\PhD\\CylWearTest_180510\\posi_20180510_1009.csv");
		System.out.println("done: " + kpis.getTimePoints().length);
	}

}
