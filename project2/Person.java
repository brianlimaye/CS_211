
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

		this.id = ++currentID;
	}

	/**
	*Sets the name only if it contains letters/spaces.
	*@param newName the (non-null) new name must only contain letters and spaces.
	*@return returns true if newName is a valid name, false otherwise.
	*@version 1.0
	*/

	public boolean setName(String newName) {

		if(newName == null) {

			return false;
		}

		if(newName.trim().length() == 0) {

			return false;
		}

		for(int i = 0; i< newName.length(); i++) {

			if(!(Character.isLetter(newName.charAt(i))) && (!(Character.isWhitespace(newName.charAt(i))))) {

				return false;
			}
		}

		this.name = newName;
		return true;
	}

	/**
	*Sets the birthday only if it follows the following format: xxxx/xx/xx (year, month, day), where x are numeric.
	*@param newBirthday the (non-null) new birthday must follow the following format, containing numerical values.
	*@return returns true if newBirthday is a valid literal, false otherwise.
	*@version 1.0
	*/


	public boolean setBirthday(String newBirthday) {

		if(newBirthday == null) {

			return false;
		}

		String[] tokens = newBirthday.split("/", 3);

		if((tokens == null) || (tokens.length != 3)) {

			return false;
		}

		if((tokens[0].length() != 4) || (tokens[1].length() != 2) || (tokens[2].length() != 2)) {

			return false;
		}

		for(int i = 0; i< tokens.length; i++) {

			try {

				int currToken = Integer.parseInt(tokens[i]);
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

		if(newSSN == null) {

			return false;
		}

		String[] tokens = newSSN.split("-", 3);

		if((tokens == null) || (tokens.length != 3)) {

			return false;
		}

		if((tokens[0].length() != 3) || (tokens[1].length() != 2) || (tokens[2].length() != 4)) {

			return false;
		}

		for(int i = 0; i < tokens.length; i++) {

			try {

				Integer.parseInt(tokens[i]);
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

		StringBuilder sb = new StringBuilder();

		if(name != null) {

			sb.append(name);
		}

		sb.append(" ");
		sb.append("xxx-xx-");
		

		if(ssn != null) {

			sb.append(ssn.substring(ssn.length() - 4) + " ");
		}
		
		if(birthday != null) {

			sb.append(birthday.substring(0, 4) + "/**/**");
		}

		return sb.toString();
	}

	/**
	*Placeholder for the overriden method in subsequent subclasses.
	*@return returns the value 0.0.
	*@version 1.0
	*/
	
	public float deduction(Family f) {

		return 0.0f;
	}
}