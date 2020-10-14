/**
*This class represents and stores properties of an Adult.
*@author Brian Limaye
*@version 1.0
*@since 1.0 
*/

public class Adult extends Person {

	private String employer;

	/**
	*This initializes a new Adult Instance, setting its name, birthday, ssn, income, and employer.
	*@param name Adult's name (non-digit)
	*@param birthday Represents birthday of adult, although must follow format of xxxx/xx/xx (year, month, day), where x are numeric.
	*@param ssn Represents the social security number of an adult, although must follow format of xxx-xx-xxxx, where x are numerical values.
	*@param income Represents a gross income/wage of an Adult (must be non-negative).
	*@param employer Represents the employer of the given Adult.
	*@version 1.0
	*/

	public Adult(String name, String birthday, String ssn, float income, String employer) {

		//Calls Person's constructor
		super();
		setName(name);
		setBirthday(birthday);
		setSSN(ssn);
		setIncome(income);
		this.employer = employer;
	}

	/**
	*This gets a human interpretted representation of the current Adult instance.
	*@return returns the String that best represents the Adult instance.
	*@version 1.0
	*/

	public String toString() {

		return super.toString() + " " + this.getIncome();
	}

	/**
	*Calculates the income of an adult subject to social security and medicare taxes.
	*@return returns the income after ss and medicare taxes.
	*@version 1.0
	*/

	public float adjustedIncome() {

		
		float taxableIncome = this.getIncome();
		float ssIncome = taxableIncome;

		//Checks to see if taxable income is greater than the social security limit.
		if(Float.compare(taxableIncome, Taxation.socialSecurityIncomeLimit) > 0) {

			ssIncome = Taxation.socialSecurityIncomeLimit;
		}

		//Calculates medicare and ssTax.
		float medicareTax = taxableIncome * (Taxation.medicareRate / 2); //Splits medicareRate in half for employer
		float ssTax = ssIncome * (Taxation.socialSecurityRate / 2); //Splits ssTax in half for employer

		return (taxableIncome - (medicareTax + ssTax));
	}

	/**
	*Calculates the amount of income that is withheld from an Adult, based on their income amount.
	*@return returns the income deducted from withholding.
	*@version 1.0
	*/

	public float taxWithheld() {

		float taxableIncome = getIncome();
		float first50K = 0f;
		float next100K = 0f;
		float remaining = 0f;

		//Checks to see if $50,000 is apart of the taxableIncome range.
		if(Float.compare(taxableIncome, 50000f) > 0) {

			first50K = 50000 * 0.1f;
			taxableIncome -= 50000f;
		}
		else {

			first50K = taxableIncome * 0.1f;
			return first50K;
		}

		
		//Checks to see if $100,000 is apart of the taxableIncome range.
		if(Float.compare(taxableIncome, 100000f) > 0) {

			next100K = 100000 * 0.15f;
			taxableIncome -= 100000;
		}
		else {

			next100K = taxableIncome * 0.15f;
			return first50K + next100K;
		}

		//Uses the remaining taxableIncome for the last rate if applicable.
		remaining = taxableIncome * 0.2f;
		return (first50K + next100K + remaining);
	}

	/**
	*This represents the deductions of a given adult based on their income and their marital status/children.
	*@param f Family to be involved in the deduction calculation.
	*@return returns the deductions of an adult based on given factors.
	*@version 1.0
	*/

	@Override
	public float deduction(Family f) {
		
		float exemptedIncome = adjustedIncome();
		float exemptionReduction = 0.0f;
		float adultBaseExemption = Taxation.adultBaseExemption;

		//If a family is a single parent family.
		if((f.getNumAdults() == 1) && (f.getNumChildren() > 0)) {

			adultBaseExemption *= 2;
		}

		//Determines how many thousand dollars above $100,000 the adjustedIncome is.
		int diff = (int)(Math.floor(exemptedIncome - 100000f) / 1000);

		//Checks to make sure that adjustedIncome is actually above $100,000
		if(diff > 0) {

			exemptionReduction = 0.005f * diff;

			if(Float.compare(exemptionReduction, 0.30f) > 0) {

				exemptionReduction = 0.30f;
			}
		}
		
		//Calculates the actual deduction
		float deduction = adultBaseExemption * (1.0f - exemptionReduction);

		//Makes sure deduction is not greater than original adjustedIncome
		if(deduction > adjustedIncome()) {

			return adjustedIncome();
		}
		return deduction;
	}

	/**
	*This represents the employer of the current Adult instance
	*@return returns the employer of the Adult
	*@version 1.0
	*/

	public String getEmployer() {

		return employer;
	}
	
}