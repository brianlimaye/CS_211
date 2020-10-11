import java.util.Scanner;

public class Person {

	private static int currentID;
	private int id;
	private String name;
	private String birthday;
	private String ssn;
	private float income;

	public Person() {

		++currentID;
		this.id = currentID;
	}

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

	public boolean setBirthday(String newBirthday) {

		if(newBirthday == null) {

			return false;
		}

		String[] tokens = newBirthday.split("/", 3);
		int[] digits = new int[3];

		if((tokens == null) || (tokens.length != 3)) {

			return false;
		}

		if((tokens[0].length() != 4) || (tokens[1].length() != 2) || (tokens[2].length() != 2)) {

			return false;
		}

		for(int i = 0; i< tokens.length; i++) {

			try {

				int currToken = Integer.parseInt(tokens[i]);
				digits[i] = currToken;
			}
			catch(NumberFormatException nfe) {

				return false;
			}
		}

		this.birthday = newBirthday;
		return true;	
	}

	public boolean setSSN(String newSSN) {

		if(newSSN == null) {

			return false;
		}

		String[] tokens = newSSN.split("-", 3);
		int[] intTokens = new int[3];

		if((tokens == null) || (tokens.length != 3)) {

			return false;
		}

		if((tokens[0].length() != 3) || (tokens[1].length() != 2) || (tokens[2].length() != 4)) {

			return false;
		}

		for(int i = 0; i < tokens.length; i++) {

			try {

				intTokens[i] = Integer.parseInt(tokens[i]);
			}
			catch(NumberFormatException nfe) {

				return false;
			}
		}

		this.ssn = newSSN;
		return true;
	}

	public boolean setIncome(float newIncome) {

		if(Float.compare(newIncome, 0f) < 0) {

			return false;
		}
		this.income = newIncome;
		return true;
	}

	public float getIncome() {

		return income;
	}

	public int getId() {

		return id;
	}

	public String toString() {

		if((birthday == null) || (ssn == null)) {

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(name);
		sb.append(" ");
		sb.append("xxx-xx-");
		sb.append(ssn.substring(ssn.length() - 4) + " ");
		sb.append(birthday.substring(0, 4) + "/**/**");

		return sb.toString();
	}

	
	public float deduction(Family f) {

		return 0.0f;
	}

	public static void main(String[] args) {

	}


}