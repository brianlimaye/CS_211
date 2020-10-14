
/**
*This class stores and sets properties of different types of people.
*@author Brian Limaye
*@version 1.0
*@since 1.0 
*/

public class Person {

	private static int currentID;
	private final int id;
	private String name;
	private String birthday;
	private String ssn;
	private float income;

	/**
	*Instantiates a person instance and sets its current/next available ID.
	*@version 1.0
	*/

	public Person() {

		//Sets id to be a running count of incrementing ids across instances.
		this.id = ++currentID;
	}

	/**
	*Sets the name only if it contains letters/spaces.
	*@param newName the (non-null) new name must only contain letters and spaces.
	*@return returns true if newName is a valid name, false otherwise.
	*@version 1.0
	*/

	public boolean setName(String newName) {

		//Checks to see if newName is null
		if(newName == null) {

			return false;
		}

		//Removes all trailing and leading whitespace
		if(newName.trim().length() == 0) {

			return false;
		}

		for(int i = 0; i< newName.length(); i++) {

			//If the newName doesn't contain a letter or a space, it's an invalid name.
			if(!(Character.isLetter(newName.charAt(i))) && (!(Character.isWhitespace(newName.charAt(i))))) {

				return false;
			}
		}

		this.name = newName; //Sets the new name if valid,
		return true;
	}

	/**
	*Sets the birthday only if it follows the following format: xxxx/xx/xx (year, month, day), where x are numeric.
	*@param newBirthday the (non-null) new birthday must follow the following format, containing numerical values.
	*@return returns true if newBirthday is a valid literal, false otherwise.
	*@version 1.0
	*/


	public boolean setBirthday(String newBirthday) {

		//Checks if newBirthday is null first
		if(newBirthday == null) {

			return false;
		}

		//Splits newBirthday with a delimiter of /, from the format.
		String[] tokens = newBirthday.split("/", 3);

		//If birthday does not follow the format of having xxxx/xx/xx.
		if((tokens == null) || (tokens.length != 3)) {

			return false;
		}

		//Checks to make sure the year has a length of 4, and month and day have a length of 2.
		if((tokens[0].length() != 4) || (tokens[1].length() != 2) || (tokens[2].length() != 2)) {

			return false;
		}

		for(int i = 0; i< tokens.length; i++) {

			try {

				int currToken = Integer.parseInt(tokens[i]); //Attempts to parse each token to determine if each is a numerical value.
			}
			catch(NumberFormatException nfe) {

				return false;
			}
		}

		this.birthday = newBirthday;
		return true;	
	}

	/**
	*Sets the social security number only if it follows the following format: xxx-xx-xxxx, where x are numerical values
	*@param newSSN the (non-null) new ssn must follow the following format, containing numerical values.
	*@return returns true if newSSN is a valid social security number, false otherwise.
	*@version 1.0
	*/

	public boolean setSSN(String newSSN) {

		//Initial null check
		if(newSSN == null) {

			return false;
		}

		//Splits newSSN based on "-" as a delimeter.
		String[] tokens = newSSN.split("-", 3);

		//Checks to see if newSSN follows the format of xxx-xx-xxxx
		if((tokens == null) || (tokens.length != 3)) {

			return false;
		}

		//Makes sure that that the lengths of each part of the ssn are valid.
		if((tokens[0].length() != 3) || (tokens[1].length() != 2) || (tokens[2].length() != 4)) {

			return false;
		}

		for(int i = 0; i < tokens.length; i++) {

			try {

				Integer.parseInt(tokens[i]); //Attempts to parse each token to determine if it is numeric.
			}
			catch(NumberFormatException nfe) {

				return false;
			}
		}

		this.ssn = newSSN;
		return true;
	}

	/**
	*Sets the income only if newIncome is non-negative.
	*@param newIncome the new income to be set if non-negative.
	*@return returns true if new income is succesfully set, false otherwise
	*@version 1.0
	*/

	public boolean setIncome(float newIncome) {

		//Checks to see if newIncome is less than 0.
		if(Float.compare(newIncome, 0f) < 0) {

			return false;
		}
		this.income = newIncome;
		return true;
	}

	/**
	*Gets the current income
	*@return returns the current gross income of a given person.
	*@version 1.0
	*/

	public float getIncome() {

		return income;
	}

	/**
	*Gets the current ID
	*@return returns the current ID of a given person
	*@version 1.0
	*/

	public int getId() {

		return id;
	}

	/**
	*Represents the human interpretting representation of the given person.
	*@return returns the String that best represents the state of the given person.
	*@version 1.0
	*/

	public String toString() {

		//StringBuilder appends the literal to be printed.
		StringBuilder sb = new StringBuilder();

		//Null check for name
		if(name != null) {

			sb.append(name);
		}

		sb.append(" ");
		sb.append("xxx-xx-");
		

		if(ssn != null) {

			//Gets last 4 characters of ssn to complete censoring
			sb.append(ssn.substring(ssn.length() - 4) + " ");
		}
		
		if(birthday != null) {

			//Gets year of birthday for censoring.
			sb.append(birthday.substring(0, 4) + "/**/**");
		}

		return sb.toString();
	}

	/**
	*Placeholder for the overriden method in subsequent subclasses.
	*@param f Family where the deductions will apply to.
	*@return returns the value 0.0.
	*@version 1.0
	*/
	
	public float deduction(Family f) {

		//Placeholder
		return 0.0f;
	}
}