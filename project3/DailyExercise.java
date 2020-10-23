import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 *This class represents a customized plan that stores data on all exercises to be performed on a given day.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class DailyExercise {

	private ArrayList<Fitness> exerciseList;
	private int duration;
	private double calorieTarget;
	private Profile profile;

	/**
	 *Four-arg constructor that initializes a new DailyExercise instance for a given profile.
	 *@param exerciseList The list of fitness exercises to be done on a day.
	 *@param duration The duration in minutes for the exercise.
	 *@param calorieTarget The amount of calories that are targeted to burn in a day throughout this plan.
	 *@param profile The profile that this workout plan pertains to.
	 *@version 1.0
	*/
	public DailyExercise(ArrayList<Fitness> exerciseList, int duration, double calorieTarget, Profile profile) {

		this.exerciseList = exerciseList;
		this.duration = duration;
		this.calorieTarget = calorieTarget;
		this.profile = profile;
	}

	/**
	 *Two-arg constructor that initializes a new DailyExercise instance for a given profile, assuming the duration is an hour and the calorie target is 500 calories.
	 *@param exerciseList The list of fitness exercises to be done on a day.
	 *@param profile The profile that this workout plan pertains to.
	 *@version 1.0
	*/
	public DailyExercise(ArrayList<Fitness> exerciseList, Profile profile) {

		this.exerciseList = exerciseList;
		this.profile = profile;
		this.duration = 120 >> 1; //A right shift, setting duration to 120 / 2 = 60 seconds. Since 120 is only right shifted once, 120 is divided by the first complement of two.
		this.calorieTarget = 500.0;
	}

	/**
	 *Adds an exercise to the exerciseList for the daily workouts.
	 *@param ex The fitness exercise to be added (must be non-null).
	 *@version 1.0
	*/

	public void addExercise(Fitness ex) {

		if(ex != null) {

			exerciseList.add(ex);
		}
	}

	/**
	 *Removes an exercise from the exerciseList if it exists.
	 *@param ex The fitness exercise to be removed (if exists).
	 *@version 1.0
	*/
	public void removeExercise(Fitness ex) {

		exerciseList.remove(ex); //Removes ex from exerciseList if found.
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
	 *Sets the duration of the daily exercises in minutes (Must be greater than 0).
	 *@param duration The new duration of the daily exercises (Must be greater than 0).
	 *@version 1.0
	*/
	public void setDuration(int duration) {

		if(duration > 0) {

			this.duration = duration;
		}
	}

	/**
	 *Sets the calorie target of the daily exercise (Must be greater than 0).
	 *@param target The new calorie tarrget to be set (Must be greater than 0).
	 *@version 1.0
	*/
	public void setCalorieTarget(double target) {

		if(Double.compare(target, 0.0) > 0) {

			this.calorieTarget = target;
		}
	}

	/**
	 *Sets the profile of the daily exercise (Must be non-null).
	 *@param profile The new profile to be set (Must be non-null).
	 *@version 1.0
	*/
	public void setProfile(Profile profile) {

		if(profile != null) {

			this.profile = profile;
		}
	}

	/**
	 *Gets the profile that corresponds to the daily exercise plan.
	 *@return Returns the profile linked to the daily exercise plan.
	 *@version 1.0
	*/
	public Profile getProfile() {

		return profile;
	}
	
	/**
	 *Gets the list of exercises to be done daily.
	 *@return Returns the list of exercises that are to be performed daily.
	 *@version 1.0
	*/
	public ArrayList<Fitness> getExerciseList() {

		return exerciseList;
	}

	/**
	 *Gets the duration of the daily exercise.
	 *@return Returns the duration of the daily exercise.
	 *@version 1.0
	*/
	public int getDuration() {

		return duration;
	}

	public double getCalorieTarget() {

		return calorieTarget;
	}

	/**
	 *Gets the an assortment of fitness exercises that fulfill ALL requested muscle groups.
	 *@param targetMuscle An assortment of muscles that will be used to match corresponding exercises.
	 *@return Returns an assortment of fitness exercises that work ALL muscles included in targetMuscle.
	 *@version 1.0
	*/
	public Fitness[] getExercises(Muscle[] targetMuscle) {

		ArrayList<Fitness> metExercises = new ArrayList<Fitness>();

		for(Fitness f: exerciseList) {

			if(deepEquals(targetMuscle, f.muscleTargeted())) { //Custom method that will check equality of two arrays based on their contents, rather than order.

				metExercises.add(f);
			}
		}

		if(metExercises.size() == 0) {

			return null;
		}
		return metExercises.toArray(new Fitness[metExercises.size()]); //Converts list to a Fitness array of the same size of metExercises.size().
	}

	
	/**
	 *Gets the an assortment of fitness exercises that fulfill some of the requested muscle groups.
	 *@param targetMuscle An assortment of muscles that will be used to match corresponding exercises.
	 *@return Returns an assortment of fitness exercises that work some of the muscles included in targetMuscle.
	 *@version 1.0
	*/
	public Fitness[] getAllExercises(Muscle[] targetMuscle) {

		ArrayList<Fitness> metExercises = new ArrayList<Fitness>(); //Stores list of exercises that meet one/more muscles from targetMuscle.

		ArrayList<Muscle> targetedMuscles = toList(targetMuscle); //Custom method using generics to convert an array to an ArrayList.

		for(int i = 0; i< exerciseList.size(); i++) {

			Fitness currentExercise = exerciseList.get(i);
			ArrayList<Muscle> workedMuscles = toList(currentExercise.muscleTargeted()); //Converts from an array to a list containing all muscle groups from a given fitness exercise.

			if(containsMatch(targetedMuscles, workedMuscles)) { //Another custom method that checks for a match between two Muscle lists.

				metExercises.add(currentExercise);
			}
		}

		return metExercises.toArray(new Fitness[metExercises.size()]); //Conversion to an array of Fitness exercises.
	}

	private boolean deepEquals(Muscle[] a, Muscle[] b) {

		List<Muscle> listA = Arrays.<Muscle>asList(a); //Conversion to list of Muscles.
		List<Muscle> listB = Arrays.<Muscle>asList(b); //Conversion to list of Muscles.

		int minIndex = Math.min(listA.size(), listB.size()); //Finds smaller list.
		boolean aIsBigger = false; 

		if(minIndex == listB.size()) {

			aIsBigger = true; //Flag to determine the smaller list.
		}

		for(int i = 0; i< minIndex; i++) {

			if(aIsBigger) { //Branch that will execute if b is smaller list, as it will iterate through all its elements.

				if(!listA.contains(listB.get(i))){

					return false;
				}
			}
			else {

				if(!listB.contains(listA.get(i))) { //Branch that will execute if a is smaller list, as it will iterate through all its elements.

					return false;
				}
			}
		}	

		return true;
	}

	private <T> ArrayList<T> toList(T[] list) {

		ArrayList<T> tmp = new ArrayList<T>();

		for(int i = 0; i< list.length; i++) {

			tmp.add(list[i]); //Copies elements from list to tmp.
		}

		return tmp;
	}

	private boolean containsMatch(ArrayList<Muscle> a, ArrayList<Muscle> b) {

		if((a == null) || (b == null) || (a.size() == 0) || (b.size() == 0)){ //First check to make sure of valid input.

			return false;
		}

		int minIndex = Math.min(a.size(), b.size()); //Find smaller size of the two lists.
		boolean aIsBigger = false;
		
		if(Double.compare(minIndex, b.size()) == 0) {

			aIsBigger = true; //Flag to indicate that a is the bigger list.
		}

		for(int i = 0; i< minIndex; i++) {

			if(aIsBigger) { //Logical branch that executes by using b as the smaller list, iterating through each of its elements.

				if(a.contains(b.get(i))) {

					return true;
				}
			}
			else {

				if(b.contains(a.get(i))) { //Logical branch that executes by using a as the smaller list, iterating through each of its elements.

					return true;
				}
			}
		}
		return false;
	}
}