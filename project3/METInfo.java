import java.util.HashMap;

/**
 *This class stores/encapsulates data for given MET values of certain exercises at varying intensities.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class METInfo {

	/**
	 *A collection of exercise and MET assortment key-value pairs that can accessed by direct lookup. The array of double MET values being index 0 for HIGH intensity, 1 for MEDIUM, 2 for LOW.
	*/
	protected static HashMap<String, Double[]> metValues = new HashMap<String, Double[]>() {{ //Data is organized as a key value pair. Where each fitness exercise is the key, while an array of intensity values is the value. 
        put("Swimming", new Double[] {10.0, 8.3, 6.0}); //Index 0 for HIGH intensity, 1 for MEDIUM, 2 for LOW.
        put("Cycling", new Double[] {14.0, 8.5, 4.0});
        put("Yoga", new Double[] {4.0, 3.0, 2.0});
        put("WeightLifting", new Double[] {6.0, 5.0, 3.5});
        put("Plyometrics", new Double[] {7.4, 4.8, 2.5});
        put("TaiChi", new Double[] {5.0, 3.0, 1.5});
        put("Squat", new Double[] {7.0, 5.0, 2.5});
        put("PullUp", new Double[] {7.5, 6.0, 4.8});
    }};
}