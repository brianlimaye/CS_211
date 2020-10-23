/**
 *This class represents the attributes and behaviors of an Anaerobic fitness exercise, WeightLifting.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class WeightLifting extends Anaerobic {

	/**
	 *This method gets the muscles targeted for a WeightLifting fitness exercise.
	 *@return Returns an arrangement of all the muscles that are worked on by WeightLifting. These muscles being Shoulders, Legs, Arms, and Triceps.
	*/
	@Override
	public Muscle[] muscleTargeted() {

		return new Muscle[] {Muscle.Shoulders, Muscle.Legs, Muscle.Arms, Muscle.Triceps};
	}
	
	/**
	 *This method calculates the amount of calories burned from a WeightLifting fitness exercise based on MET.
	 *@param intensity The intensity of the given WeightLifting fitness exercise (LOW, MEDIUM, HIGH).
	 *@param weight The weight in kg of a given profile.
	 *@param duration The time of a WeightLifting fitness exercise in minutes.
	*/
	@Override
	public double calorieLoss(Intensity intensity, double weight, int duration) {

		double numOfHours = duration / 60.0; //Gets hours from duration.
		double met = getCorrespondingMET(intensity) * numOfHours * weight;

		return met;
	}

	private double getCorrespondingMET(Intensity intensity) {

		String exercise = "Weightlifting"; //Sets corresponding key for MET lookup.
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
	 *Gets the description of a WeightLifting instance.
	 *@return Returns the human representation literal of a WeightLifting instance.
	*/
	@Override 
	public String description() {

		return this.getClass().getName(); //Gets class name of WeightLifting class.
	}
	
}