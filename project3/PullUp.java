/**
 *This class represents the attributes and behaviors of an Endurance fitness exercise, Pull-ups.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class PullUp extends Endurance {
	
	/**
	 *This method gets the muscles targeted for a Pull-up fitness exercise.
	 *@return Returns an arrangement of all the muscles that are worked on by Pull-ups. These muscles being Biceps and Arms.
	*/
	@Override
	public Muscle[] muscleTargeted() {

		return new Muscle[] {Muscle.Biceps, Muscle.Arms};
	}
	
	/**
	 *This method calculates the amount of calories burned from a Pull-up fitness exercise based on MET.
	 *@param intensity The intensity of the given Pull-up fitness exercise (LOW, MEDIUM, HIGH).
	 *@param weight The weight in kg of a given profile.
	 *@param duration The time of a Pull-up fitness exercise in minutes.
	*/
	@Override
	public double calorieLoss(Intensity intensity, double weight, int duration) {

		double numOfHours = duration / 60.0; //Gets number of hours based on duration.
		double met = getCorrespondingMET(intensity) * numOfHours * weight;

		return met;
	}

	private double getCorrespondingMET(Intensity intensity) {

		String exercise = "Pull-Up"; //Gets corresponding key for MET lookup.
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
	 *Gets the description of a Pull-up instance.
	 *@return Returns the human representation literal of a Pull-up instance.
	*/
	@Override 
	public String description() {

		return this.getClass().getName(); //Gets the class name of the Squat class.
	}
}