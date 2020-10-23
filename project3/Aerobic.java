/**
 *This class represents an Aerobic exercise, defining it as with oxygen.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public abstract class Aerobic implements Fitness {

	/**
	 *This method gets the description of an Aerobic exercise.
	 *@return Returns the description of an Aerobic exercise("with oxygen.").
	*/
	@Override
	public String description() {

		StringBuilder sb = new StringBuilder();
		sb.append("with");
		sb.append(" ");
		sb.append("oxygen."); //Used to append the message instead of directly using concatenation.

		return sb.toString();
	}
}