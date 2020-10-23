/**
 *This class represents an Endurance exercise, defining it as being all about sweat and patience.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public abstract class Endurance implements Fitness {
	
	/**
	 *This method gets the description of an Endurance exercise.
	 *@return Returns the description of an Endurance exercise("Endurance is all about sweat and patience.").
	*/
	@Override
	public String description() {

		return "Endurance is all about sweat and patience.";
	}
}