/**
 * 
 */
package uk.ac.cf.milling.tests;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class Performance {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		int counter = 0;
		for (int i = -Integer.MAX_VALUE; i<Integer.MAX_VALUE; i++){
			Math.abs(i);
		}
		System.out.println(System.currentTimeMillis() - startTime);

	}

}
