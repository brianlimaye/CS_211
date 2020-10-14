
/**
*This class represents and stores properties of a Child.
*@author Brian Limaye
*@version 1.0
*@since 1.0 
*/

public class Child extends Person {
	
	private String school;
	private float tuition;

	/**
	*This initializes a new Child Instance, setting its name, birthday, ssn, income, school, tuition, only if valid input.
	*@param name Child's name (non-digit)
	*@param birthday Represents birthday of adult, although must follow format of xxxx/xx/xx (year, month, day), where x are numeric.
	*@param ssn Represents the social security number of a child, although must follow format of xxx-xx-xxxx, where x are numerical values.
	*@param income Represents an income/allowance of a child (must be non-negative).
	*@param school Represents the school attended by the child.
	*@param tuition Represents the tuition of attending the school (if-any).
	*@version 1.0
	*/

	public Child(String name, String birthday, String ssn, float income, String school, float tuition) {

		//Calls Person's constructor
		super();
		setName(name);
		setBirthday(birthday);
		setSSN(ssn);
		setIncome(income);
		this.school = school;
		this.tuition = tuition;
	}

	/**
	*This gets a human interpretted representation of the current Child instance.
	*@return returns the String that best represents the Child instance.
	*@version 1.0
	*/

	public String toString() {

		return super.toString() + " " + ((school == null) ? "" : school); //Tertiary Operator instead of if, to determine school's string representation.
	}

	/**
	*This gets the current tuition of a Child's school.
	*@return returns the tuition of a school attended by the current child.
	*@version 1.0
	*/

	public float getTuition() {

		return tuition;
	}

	/**
	*This represents the deductions of a given child based on their income and number of children
	*@param f Family to be involved in the deduction calculation.
	*@return returns the deductions of a child based on their sibling count and income.
	*@version 1.0
	*/

	@Override
	public float deduction(Family f) {

		float exemptionReduction = 0f;
		float childBaseExemption = Taxation.childBaseExemption;
		int numOfChildren = f.getNumChildren();
		float exemptedIncome = this.getIncome();

		for(int i = 3; i <= numOfChildren; i++) {

			//Checks to see if exemptionReduction never is greater than 50%
			if(Float.compare(exemptionReduction, 0.5f) > 0) {

				break;
			}

			exemptionReduction += 0.05f;
		}

		//Checks to see if the adjusted child base exemption is greater than exemptedIncome.
		if(Float.compare((childBaseExemption * (1.0f - exemptionReduction)), exemptedIncome) > 0) {

			return exemptedIncome;
		}
		
		//Returns adjusted exemption.
		return childBaseExemption * (1.0f - exemptionReduction);
	}

}