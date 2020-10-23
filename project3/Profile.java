/**
 *This class represents a profile of an individual.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/

public class Profile {
	
	private int age;
	private double height;
	private double weight;
	private Gender gender;

	/**
	 *Four-arg constructor that creates a new Profile instance.
	 *@param age The age of the certain individual.
	 *@param height The height in m of the individual.
	 *@param weight The weight in kg of the individual.
	 *@param gender The gender (MALE or FEMALE) of the individual.
	*/
	public Profile(int age, double height, double weight, Gender gender) {

		this.age = age;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
	}

	/**
	 *Sets the new height of the profile.
	 *@param height The new height to be set (Must be non zero/negative).
	 *@version 1.0
	*/ 
	public void setHeight(double height) {

		if(Double.compare(height, 0.0) > 0) { //Checks to see if height is greater than 0.0m (for validity).

			this.height = height;
		}
	}

	/**
	 *Sets the new weight of the profile.
	 *@param weight The new weight to be set (Must be non zero/negative).
	 *@version 1.0
	*/
	public void setWeight(double weight) {

		if(Double.compare(weight, 0.0) > 0) { //Checks to see if weight is greater than 0.0kg (for validity).

			this.weight = weight;
		}
	}

	/**
	 *Sets the new age of the profile.
	 *@param age The new age to be set (Must be greater than 0).
	 *@version 1.0
	*/
	public void setAge(int age) {

		if(age > 0) {

			this.age = age;
		}
	}

	/**
	 *Sets the gender of the profile.
	 *@param gender The new gender to be set (MALE or FEMALE).
	 *@version 1.0
	*/
	public void setGender(Gender gender) {

		this.gender = gender;
	}

	/**
	 *Gets the height of the profile.
	 *@return Returns the current height in m.
	 *@version 1.0
	*/
	public double getHeight() {

		return height;
	}

	/**
	 *Gets the weight of the profile.
	 *@return Returns the current weight in kg.
	 *@version 1.0
	*/
	public double getWeight() {

		return weight;
	}

	/**
	 *Gets the age of the profile.
	 *@return Returns the current age in years.
	 *@version 1.0
	*/
	public int getAge() {

		return age;
	}

	/**
	 *Gets the gender of the profile.
	 *@return Returns the current gender (MALE or FEMALE).
	 *@version 1.0
	*/
	public Gender getGender() {

		return gender;
	}

	/**
	 *Gets the human interpretted literal of the Profile instance.
	 *@return Returns the literal that represents a Profile.
	 *@version 1.0
	*/
	@Override 
	public String toString() {

		//String.format("%.1f", args) was used to only print out info up to one decimal place.
		return "Age " + age + ", Weight " + String.format("%.1f", weight) + "kg, Height " + String.format("%.1f", height) + "m, Gender " + gender;
	}

	/**
	 *Calculates the BMI based on weight and height.
	 *@return Returns the calculated BMI.
	*/
	public double calcBMI() {

		if(Double.compare(height, 0.0) == 0) { //Checks to see if height is equal to 0.0, in order to avoid a divide by zero.

			return 0.0;
		}

		return weight / Math.pow(height, 2.0);
	}

	/**
	 *Calculates the current calorie intake per day based on weight, height, and age.
	 *@return Returns the calculated calorie intake based on given factors.
	*/
	public double dailyCalorieIntake() {

		if(gender == Gender.MALE) {

			return 66.47 + (13.75 * weight) + (5.003 * (height * 100)) - (6.755 * age); //Formula for calorie intake for males (specs).
		}

		if(gender == Gender.FEMALE) {

			return 655.1 + ( 9.563 * weight) + (1.85 * (height * 100)) - ( 4.676 * age); //Formula for calorie intake for females (specs).
		}

		return 0.0;
	}
}