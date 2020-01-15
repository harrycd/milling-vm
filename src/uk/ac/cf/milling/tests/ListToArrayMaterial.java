/**
 * 
 */
package uk.ac.cf.milling.tests;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cf.milling.objects.Material;

/**
 * @author Theocharis Alexopoulos
 *
 */
public class ListToArrayMaterial {
	public static void main(String[] args){
		List<Material> ml = new ArrayList<Material>();
		Material mat = new Material();
		mat.setMaterialName("papaproa");
		ml.add(mat);
		ml.add(new Material());
		Material[] ma = ml.toArray(new Material[0]);
		System.out.println(ma[0]);
		
	}
	

}
