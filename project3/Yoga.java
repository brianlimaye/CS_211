/**
 *This class represents the attributes and behaviors of a Flexibility fitness exercise, Yoga.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class Yoga extends Flexibility {

	/**
	 *This method gets the muscles targeted for a Yoga fitness exercise.
	 *@return Returns an arrangement of all the muscles that are worked on by Yoga. These muscles being Triceps and Biceps.
	*/
	@Override
	public Muscle[] muscleTargeted() {

		return new Muscle[] {Muscle.Triceps, Muscle.Biceps};
	}
	
	/**
	 *This method calculates the amount of calories burned from a Yoga fitness exercise based on MET.
	 *@param intensity The intensity of the given Yoga fitness exercise (LOW, MEDIUM, HIGH).
	 *@param weight The weight in kg of a given profile.
	 *@param duration The time of a Yoga fitness exercise in minutes.
	*/
	@Override
	public double calorieLoss(Intensity intensity, double weight, int duration) {

		double numOfHours = duration / 60.0; //Get hours from duration.
		double met = getCorrespondingMET(intensity) * numOfHours * weight;

		return met;
	}

	private double getCorrespondingMET(Intensity intensity) {

		String exercise = "Yoga"; //Gets corresponding key for MET lookup.
		int index = -1;

		//Gets corresponding index for MET lookup based on intensity.
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
	 *Gets the description of a Yoga instance.
	 *@return Returns the human representation literal of a Yoga instance.
	*/
	@Override 
	public String description() {

		return this.getClass().getName(); //Gets the class name of the Yoga class.
	}
}