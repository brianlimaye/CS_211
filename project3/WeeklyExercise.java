import java.util.ArrayList;

/**
 *This class represents a customized plan that stores data on all exercises to be performed throughout a week
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class WeeklyExercise {
	
	private ArrayList<Fitness> exerciseList;
	private int days;
	private double weeklyCalorieTarget;
	private Profile profile;

	/**
	 *Four-arg constructor that initializes a new WeeklyExercise instance for a given profile.
	 *@param exerciseList The list of fitness exercises to be done throughout a week.
	 *@param days The duration in days for the exercises.
	 *@param weeklyCalorieTarget The amount of calories that are targeted to burn in a week.
	 *@param profile The profile that this workout plan pertains to.
	 *@version 1.0
	*/
	public WeeklyExercise(ArrayList<Fitness> exerciseList, int days, double weeklyCalorieTarget, Profile profile) {

		this.exerciseList = exerciseList;
		this.days = days;
		this.weeklyCalorieTarget = weeklyCalorieTarget;
		this.profile = profile;
	}

	/**
	 *Two-arg constructor that initializes a new WeeklyExercise instance for a given profile, assuming the number of days is 7 and the weekly calorie target is 3500.
	 *@param exerciseList The list of fitness exercises to be done throughout a week.
	 *@param profile The profile that this workout plan pertains to.
	 *@version 1.0
	*/
	public WeeklyExercise(ArrayList<Fitness> exerciseList, Profile profile) {

		this.exerciseList = exerciseList;
		this.profile = profile;
		this.weeklyCalorieTarget = 3500;
		this.days = 28 >> 2; //Another right shift :). Since 28 is being right-shifted by 2 complements of 2, 28 will be divided by 4, equaling 7. 
	}

	/**
	 *Adds an exercise to the exerciseList for the weekly workouts.
	 *@param ex The fitness exercise to be added (must be non-null).
	 *@version 1.0
	*/
	public void addExercise(Fitness ex) {

		if(ex != null) { //Null check to ensure non-null exercises in the list.

			exerciseList.add(ex);
		}
	}

	/**
	 *Removes an exercise from the exerciseList if it exists.
	 *@param ex The fitness exercise to be removed (if exists).
	 *@version 1.0
	*/
	public void removeExercise(Fitness ex) {

		exerciseList.remove(ex); //Removes ex if found in exerciseList.
	}

	/**
	 *Sets the exerciseList to a new list. If it exists.
	 *@param list The new exercise list to be set. If it exists.
	 *@version 1.0
	*/
	public void setExerciseList(ArrayList<Fitness> list) {

		if(list != null) {

			this.exerciseList = list;
		}
	}

	/**
	 *Sets the number of days of the weekly exercise (Must be greater than 0).
	 *@param days The new number of days of the weekly exercise (Must be greater than 0).
	 *@version 1.0
	*/
	public void setDays(int days) {

		if((days > 0) && (days < 8)) { //Validation to make sure that days is valid input.

			this.days = days;
		}
	}

	/**
	 *Sets the calorie target of the weekly exercises (Must be greater than 0).
	 *@param target The new calorie target to be set (Must be greater than 0).
	 *@version 1.0
	*/
	public void setWeeklyCalorieTarget(double target) {

		if(Double.compare(target, 0.0) > 0) { //Validates that target is greater than 0.0 calories.

			this.weeklyCalorieTarget = target;
		}
	}

	/**
	 *Sets the profile of the weekly exercises (Must be non-null).
	 *@param profile The new profile to be set (Must be non-null).
	 *@version 1.0
	*/
	public void setProfile(Profile profile) {

		if(profile != null) {

			this.profile = profile;
		}
	}

	/**
	 *Gets the list of exercises to be done weekly.
	 *@return Returns the list of exercises that are to be performed weekly.
	 *@version 1.0
	*/
	public ArrayList<Fitness> getExerciseList() {

		return exerciseList;
	} 

	
	/**
	 *Gets the number of days of the weekly exercises.
	 *@return Returns the number of days for the weekly exercises.
	 *@version 1.0
	*/
	public int getDays() {

		return days;
	}

	/**
	 *Gets the corresponding profile for a a given weekly exercise plan.
	 *@return Returns the profile for a weekly exercise plan.
	 *@version 1.0
	*/
	public Profile getProfile() {

		return profile;
	}

	/**
	 *Gets the weekly calorie target for a given plan.
	 *@return Returns the weekly calorie target.
	*/
	public double getWeeklyCalorieTarget() {

		return weeklyCalorieTarget;
	}

	/**
	 *Gets a list of daily exercises and their durations, based on intensity (LOW, MEDIUM, HIGH) in order to reach a calorie loss goal.
	 *@param intensity The intensity of the fitness exercises (LOW, MEDIUM, HIGH).
	 *@return Returns a list of daily exercises and the durations that allow a weekly burned calorie goal to be met.
	*/
	public ArrayList<DailyExercise> getWeeklyExercises(Intensity intensity) {

		ArrayList<DailyExercise> dailyExercises = new ArrayList<DailyExercise>();
		ArrayList<Fitness> activities = new ArrayList<Fitness>();
		double caloriesPerDay = weeklyCalorieTarget / days; //Calculates the amount of calories per day.
		int duration;

		int currentDay = 1; //Sets the current day to the first day of workouts.

		while(currentDay <= days) {

			activities.clear(); //Clears the list activities when on another day.
			
			Fitness currentExercise = exerciseList.get(currentDay - 1); //Gets the current exercise for the given day.
		    
		    duration = (int) (60 * (caloriesPerDay / (getCorrespondingMET(intensity, currentExercise) * profile.getWeight()))); //Calorie loss formula, solved for time :D.
			
			activities.add(currentExercise);
			
			dailyExercises.add(new DailyExercise(activities, duration, caloriesPerDay, profile)); //Adds a new daily exercise when the correct amount of calories are burned for the day.
			++currentDay; //Moves on to the next day.
		}
		return dailyExercises;
	}

	private double getCorrespondingMET(Intensity intensity, Fitness f) {

		String exercise = f.description(); //Gets corresponding key for MET lookup.
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
	 *Gets a list of daily exercises and their durations, based on a LOW intensity in order to reach a calorie loss goal.
	 *@return Returns a list of daily exercises and the durations that allow a weekly burned calorie goal to be met.
	*/
	public ArrayList<DailyExercise> getWeeklyExercises() {

		return this.getWeeklyExercises(Intensity.LOW); //Calls same method with a hard-coded intensity.
	}

	/**
	 *Gets a literal representing advice to reach a given target weight in an amount a time.
	 *@param targetWeight The weight that is desired by a given profile.
	 *@param withInDays The amount of time that is used to calculate calorie data.
	 *@return Returns a literal that contains advice to reach a weight loss goal.
	*/
	public String targetedCalorieLoss(double targetWeight, int withInDays) {

		if(Double.compare(targetWeight, profile.getWeight()) > 0) { //If targetWeight is bigger than the current weight. After all, the point is to lose weight.

			throw new TargetWeightException("Only works to lose weight");
		}

		double currentWeight = profile.getWeight();
		double lossPerDay = ((currentWeight - targetWeight) * 7000) / withInDays; //Formula to determine how many calories to lose per day. (7000 calories = 1kg).
		double intakePerDay = profile.dailyCalorieIntake(); //Gets daily calorie intake from profile.

		//Literal output from calculations. String.format("%.2f", args) is used to display only 2 decimal places after each double literal/expressions.
		return "You need to lose " + String.format("%.2f", lossPerDay) + " calories per day or decrease your intake from " + String.format("%.2f", intakePerDay) + " to " + String.format("%.2f", intakePerDay - lossPerDay) + " in order to lose " + String.format("%.2f", currentWeight - targetWeight) + " kg of weight";
	}
}