/**
 *This interface represents a basic overview of attributes of a Fitness exercise.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/

public interface Fitness {

	/**
	 *This method gets all muscles from a given Fitness exercise.
	 *@return Returns all muscles that are worked from a given Fitness exercise.
	*/
	public abstract Muscle[] muscleTargeted();
	/**
	 *This method calculates how many calories are burned from a given Fitness workout.
	 *@param intensity Represents the intensity of a given workout (LOW, MEDIUM, HIGH).
	 *@param weight Represents the weight in kg of a given person.
	 *@param duration Represents the time of a Fitness workout in minutes.
	 *@return Returns the calculated amount of calories burned from a certain Fitness exercise.
	*/
	public abstract double calorieLoss(Intensity intensity, double weight, int duration);

	/**
	 *Gets the description/identifier of a given Fitness
	 *@return Returns the name of a Fitness object.
	*/
	public abstract String description();
}