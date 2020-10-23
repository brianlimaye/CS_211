/**
 *This class represents an Anaerobic exercise, defining it as without oxygen.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public abstract class Anaerobic implements Fitness {

	/**
	 *This method gets the description of an Anaerobic exercise.
	 *@return Returns the description of an Anaerobic exercise("without oxygen.").
	*/
	@Override
	public String description() {

		StringBuilder sb = new StringBuilder(); //Appending rather than direct concatenation.
		sb.append("without");
		sb.append(" ");
		sb.append("oxygen.");

		return sb.toString();
	}
}