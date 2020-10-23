/**
 *This class represents the attributes and behaviors of an Aerobic fitness exercise, Cycling.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class Cycling extends Aerobic {

	
	/**
	 *This method gets the muscles targeted for a Cycling fitness exercise.
	 *@return Returns an arrangement of all the muscles that are worked on by Cycling. These muscles being Glutes, Cardio, and Legs.
	*/
	@Override
	public Muscle[] muscleTargeted() {

		return new Muscle[] {Muscle.Glutes, Muscle.Cardio, Muscle.Legs};
	}

	/**
	 *This method calculates the amount of calories burned from a Cycling fitness exercise based on MET.
	 *@param intensity The intensity of the given Cycling fitness exercise (LOW, MEDIUM, HIGH).
	 *@param weight The weight in kg of a given profile.
	 *@param duration The time of a Cycling fitness exercise in minutes.
	*/
	@Override
	public double calorieLoss(Intensity intensity, double weight, int duration) {

		double numOfHours = duration / 60.0; //Gets number of hours from duration.
		double met = getCorrespondingMET(intensity) * numOfHours * weight;

		return met;
	}

	private double getCorrespondingMET(Intensity intensity) {

		String exercise = "Cycling"; //Sets the corresponding key for MET lookup.
		int index = -1;

		//Gets the index for MET lookup based on intensity.
		switch(intensity) {

			case HIGH:
				index = 0;
				break;
			case MEDIUM:
				index = 1;
				break;
			case LOW:
				index = 2;
				break;
		}

		return METInfo.metValues.get(exercise)[index];
	}

	/**
	 *Gets the description of a Cycling instance.
	 *@return Returns the human representation literal of a Cycling instance.
	*/
	@Override 
	public String description() {

		return this.getClass().getName(); //Returns class name of Cycling class.
	}


}