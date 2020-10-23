/**
 *This class represents the attributes and behaviors of an Aerobic fitness exercise, Swimming.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/

public class Swimming extends Aerobic {

	private SwimmingType technique;

	/**
	 *Default constructor that instantiates a new Swimming instance, setting the default technique to Freestyle.
	*/
	public Swimming() {

		this.technique = SwimmingType.Freestyle;
	}

	/**
	 *One-argument constructor that instantiates a new Swimming instance, setting the technique to st.
	 *@param st The SwimmingType to be set as the technique.
	*/
	public Swimming(SwimmingType st) {

		this.technique = st;
	}

	/**
	 *This method gets the muscles targeted for a Swimming fitness exercise based on the technique used.
	 *@return Returns an arrangement of all the muscles that are worked based on the swimming technique.
	*/
	@Override
	public Muscle[] muscleTargeted() {

		Muscle[] targetGroup;

		switch(technique) {

			case Freestyle:
				targetGroup = new Muscle[] {Muscle.Arms, Muscle.Legs, Muscle.Cardio}; //Instantiates and sets a new Muscle[] in one line based on the muscles worked by Freestyle.
				break;
			case Breaststroke:
				targetGroup = new Muscle[] {Muscle.Glutes, Muscle.Cardio}; //Instantiates and sets a new Muscle[] in one line based on the muscles worked by Breaststroke.
				break;
			case Butterflystroke:
				targetGroup = new Muscle[] {Muscle.Abs, Muscle.Back, Muscle.Shoulders, Muscle.Biceps, Muscle.Triceps}; //Instantiates and sets a new Muscle[] in one line based on the muscles worked by Butterflystroke.
				break;
			default:
				targetGroup = new Muscle[] {}; //If technique does not equal any of the other SwimmingTypes.
				break;
		}

		return targetGroup;
	}

	/**
	 *This method calculates the amount of calories burned from a Swimming fitness exercise based on MET.
	 *@param intensity The intensity of the given Swimming fitness exercise (LOW, MEDIUM, HIGH).
	 *@param weight The weight in kg of a given profile.
	 *@param duration The time of a Swimming fitness exercise in minutes.
	*/
	@Override
	public double calorieLoss(Intensity intensity, double weight, int duration) {

		double numOfHours = duration / 60.0; //Gets the number of hours from duration.
		double met = getCorrespondingMET(intensity) * numOfHours * weight; //MET calcuation.

		return met;
	}

	private double getCorrespondingMET(Intensity intensity) {

		String exercise = "Swimming"; //Sets the corresponding key for MET lookup.
		int index = -1;

		//Gets the corresponding index of MET value based on intensity.
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
	 *Sets the technique of a Swimming fitness exercise.
	 *@param type The new SwimmingType to be set as the new technique.
	*/
	public void setSwimmingType(SwimmingType type) {

		this.technique = type;
	}

	/**
	 *Gets the current technique of a Swimming fitness exercise.
	 *@return Returns the technique of a Swimming fitness exercise.
	*/
	public SwimmingType getSwimmingType() {

		return technique;
	}

	/**
	 *Gets the description of a Swimming instance.
	 *@return Returns the human representation literal of a Swimming instance.
	*/
	@Override
	public String description() {

		return this.getClass().getName(); //Returns class name of Swimming class.
	}
}