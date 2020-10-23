/**
 *This class represents the attributes and behaviors of an Anaerobic fitness exercise, Plyometrics.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class Plyometrics extends Anaerobic {

	/**
	 *This method gets the muscles targeted for a Plyometrics fitness exercise.
	 *@return Returns an arrangement of all the muscles that are worked on by Plyometrics. These muscles being Abs, Legs, and Glutes.
	*/
	@Override
	public Muscle[] muscleTargeted() {

		return new Muscle[] {Muscle.Abs, Muscle.Legs, Muscle.Glutes};
	}
	
	/**
	 *This method calculates the amount of calories burned from a Plyometrics fitness exercise based on MET.
	 *@param intensity The intensity of the given Plyometrics fitness exercise (LOW, MEDIUM, HIGH).
	 *@param weight The weight in kg of a given profile.
	 *@param duration The time of a Plyometrics fitness exercise in minutes.
	*/
	@Override
	public double calorieLoss(Intensity intensity, double weight, int duration) {

		double numOfHours = duration / 60.0; //Gets hours based on duration
		double met = getCorrespondingMET(intensity) * numOfHours * weight;

		return met;
	}

	private double getCorrespondingMET(Intensity intensity) {

		String exercise = "Plyometrics"; //Sets corresponding key for MET lookup.
		int index = -1;

		//Gets the corresponding index for MET lookup based on intensity.
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
	 *Gets the description of a Plyometrics instance.
	 *@return Returns the human representation literal of a Plyometrics instance.
	*/
	@Override 
	public String description() {

		return this.getClass().getName(); //Gets class name of Plyometrics class.
	}
	
}