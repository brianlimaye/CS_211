/**
*This class represents and stores properties of a Family (Arrangment of Children and Adults).
*@author Brian Limaye
*@version 1.0
*@since 1.0 
*/

public class Family {

	private final  int numMembers;
	private final int filingStatus;
	private final Person[] people;

	/**
	*This initializes a new Family, creating an assortment of Adults and Children
	*@param members Represents the amount of people in a family
	*@param filingStatus Represents a family's status when filing taxes. 1 = single, 2 = married filing jointly, 3 = married filing separate.
	*@version 1.0
	*/

	public Family(int members, int filingStatus) {

		this.numMembers = members;
		people = new Person[members];
		this.filingStatus = filingStatus;
	}

	/**
	*Adds a new member to the assortment of family members
	*@param p Person to be added to the assortment, if the family has space.
	*@return returns true if adding the given person is successful, false otherwise.
	*@version 1.0
	*/

	public boolean addMember(Person p) {

		//Checks for a null person
		if(p == null) {

			return false;
		}

		for(int i = 0; i < people.length; i++) {

			//Looks for an empty space to add p to the family.
			if(people[i] == null) {

				people[i] = p;
				return true;
			}
		}
		return false;
	}

	/**
	*Gets how many adults are in a given family
	*@return returns the number of adults in a family.
	*@version 1.0
	*/

	public int getNumAdults() {

		int adultCount = 0;

		for(int i = 0; i < people.length; i++) {

			//Makes sure that people[i] is an adult.
			if(people[i] instanceof Adult) {

				++adultCount;
			}
		}
		return adultCount;
	}

	/**
	*Gets how many children are in a given family
	*@return returns the number of children in a family.
	*@version 1.0
	*/

	public int getNumChildren() {

		int childrenCount = 0;

		for(int i = 0; i < people.length; i++) {

			//Makes sure that people[i] is a child.
			if(people[i] instanceof Child) {

				++childrenCount;
			}
		}
		return childrenCount;
	}

	/**
	*Gets the filing status in a given family
	*@return returns the filing status based on the family's current situation. 1 = single, 2 = married filing jointly, 3 = married filing separate.
	*@version 1.0
	*/

	public int getFilingStatus() {

		return filingStatus;
	}

	/**
	*Calculates the total taxable income of all family members, subtracting deductions for each member.
	*@return returns the taxable income of the entire family.
	*@version 1.0
	*/

	public float getTaxableIncome() {

		float totalTaxableIncome = 0f;

		for(Person p: people) {

			if(p instanceof Adult) {

				//Adds all adjusted incomes from adults
				totalTaxableIncome += ((Adult) p).adjustedIncome();
				//Subtracts all deductions from each adult
				totalTaxableIncome -= ((Adult) p).deduction(this);
			}
			if(p instanceof Child) {

				//Adds all adjusted incomes from children
				totalTaxableIncome += ((Child) p).getIncome();
				//Subtracts all deduction from each child
				totalTaxableIncome -= ((Child) p).deduction(this);
			}
		}

		return totalTaxableIncome;
	}

	private float preTaxCredit() {

		float taxableIncome = getTaxableIncome();
		float halfMedianIncome = Taxation.medianIncomePerCapita / 2; //Value for the low 50%

		if(taxableIncome > halfMedianIncome) {

			return 0.0f;
		}

		int bracket = 1; //Regular case for income range

		//Special case where income falls into a different bracket because of a filing status of 1.
		if((filingStatus == 1) && (Float.compare(taxableIncome, 10000f) > 0)) {

			bracket = 2;
		}

		return taxableIncome * Taxation.bracketTaxRate(bracket, filingStatus);
	}	

	/**
	*Calculates the tax credit that applies to a given family based on their financial situation/children.
	*@return returns the tax credit that a family applies for.
	*@version 1.0
	*/

	public float taxCredit() {

		float totalCredit = 0f;
		float taxableIncome = this.getTaxableIncome();
		float halfMedianIncome = Taxation.medianIncomePerCapita / 2; //Value for the low 50%

		//Checks if family is eligible to receive tax credit.
		if(Float.compare(taxableIncome, halfMedianIncome) > 0) {

			return 0.0f;
		}

		//Algorithm for finding how many thousand dollars an income is.
		int diff = (int)(Math.floor(taxableIncome)) / 1000;

		if(diff <= 0) {

			return 0.0f;
		}

		//Credit for thousand dollars of income
		totalCredit = 30 * diff;

		for(Person p: people) {

			if(p instanceof Child) {

				//Credit for tuition/$1000, whichever is lower.
				totalCredit += Math.min(((Child) p).getTuition(), 1000);
			}
		}

		//Finds minimum of totalCredit and preTaxCredit
		float min = Math.min(totalCredit, preTaxCredit());

		//Special case where each parent claims half of credit.
		if(filingStatus == 3) {

			min /= 2;
		}

		return Math.min(min, 2000f);
	}

	/**
	*Calculates the total tax of a family based on their income ranges and their corresponding tax rates.
	*@return returns the total tax of a family based on income ranges/rates.
	*@version 1.0
	*/

	public float calculateTax() {

		float totalTax = 0f;
		float currentRate = 0f;
		float taxCredit = 0f;
		int maxBracket = Taxation.maxIncomeTaxBracket(this);

		for(int i = 1; i <= maxBracket; i++) {

			currentRate = Taxation.bracketTaxRate(i, this.filingStatus); //Gets corresponding rate based on bracket and filing
			totalTax += Taxation.bracketIncome(this, i) * currentRate; //Gets corresponding income portion based on family.
		}

		//Takes out credits received.
		totalTax -= taxCredit();

		for(Person p: people) {

			if(p instanceof Adult) {

				//Deducts withheld taxes.
				totalTax -= ((Adult) p).taxWithheld();
			}
		}

		return totalTax;
	}

	/**
	*Gets the assortment of people in a family
	*@return returns the array of people in a given family.
	*/

	public Person[] getPeople() {

		return people;
	}

}