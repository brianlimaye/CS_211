/**
 *This class represents a Flexibility exercise, defining it as being uncomfortable and taking time, making it so people don't like to do it.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public abstract class Flexibility implements Fitness {
	
	/**
	 *This method gets the description of a Flexibility exercise.
	 *@return Returns the description of a Flexibility exercise("Flexibility is uncomfortable and it takes time, so people don't like to do it.").
	*/
	@Override
	public String description() {

		return "Flexibility is uncomfortable and it takes time, so people don't like to do it.";
	}
}