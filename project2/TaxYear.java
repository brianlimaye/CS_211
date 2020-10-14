/**
*This class represents and stores a tax filing of a collection of Families
*@author Brian Limaye
*@version 1.0
*@since 1.0 
*/

public class TaxYear {

	private Family[] families;
	private int nextPos;
	
	/**
	*This initializes a new TaxYear instance, creating an assorment of families.
	*@param max Represents the max number of families to be filed.
	*@version 1.0
	*/

	public TaxYear(int max) {

		//Checks to make sure max is non-negative
		if(max < 0) {

			max = 1; 
		}

		families = new Family[max];
		nextPos = 0;
	}

	/**
	*Determines whether a family can be filed for taxes based on their filing status.
	*@param f Family to be reviewed for filing before adding to the assortment.
	*@return returns true if a family is eligible for filing, false otherwise.
	*@version 1.0
	*/
	public boolean taxFiling(Family f) {

		//Makes sure family is non-null
		if(f == null) {

			return false;
		}

		int filingStatus = f.getFilingStatus();
		int numOfAdults = f.getNumAdults();
		boolean isValidFiling = true;

		switch(filingStatus) {

			//Case where there is a potential single parent family
			case 1:
			case 3: 
				if(numOfAdults > 1) {

					isValidFiling = false;
				}
				break;
			//Case where adults are married and filing together.
			case 2:
				if(numOfAdults != 2) {

					isValidFiling = false;
				}
				break;
		}

		//Case where there are no adults in the family
		if(numOfAdults < 1) {

			isValidFiling = false;
		}

		//Makes sure there the filing is valid and there is space in the array of families to be filed.
		if((isValidFiling) && (nextPos < families.length)) {

			families[nextPos++] = f;
		}

		return isValidFiling;
	}

	/**
	*Calculates the total tax withheld among all adults in all the families.
	*@return returns the amount of tax withheld among all adults.
	*@version 1.0
	*/
	public double taxWithheld() {

		double totalWithheld = 0;

		for(Family f: families) {

			//Checks for a null family, families that haven't started being filed.
			if(f == null) {

				break;
			}

			Person[] people = f.getPeople();

			for(Person p: people) {

				if(p instanceof Adult) {

					//Adds all the total withheld taxes among all families.
					totalWithheld += ((Adult) p).taxWithheld();
				}
			}
		}

		return totalWithheld;
	}

	/**
	*Calculates the total calculated tax among all families filed.
	*@return returns the total calculated tax for all families.
	*@version 1.0
	*/
	public float taxDue() {

		float taxDue = 0f;
		float currentTax = 0f;

		for(Family f: families) {

			//Checks for a null family, families that haven't started being filed.
			if(f == null) {

				break;
			}
			//Adds up all the calculated taxes among all families.
			currentTax = f.calculateTax();
			taxDue += currentTax;
		}

		return taxDue;
	}

	/**
	*Calculates the total tax owed based on all the families' taxable incomes.
	*@return returns the tax owed based strictly on taxable incomes of all families.
	*@version 1.0
	*/
	public float taxOwed() {

		float totalTax = 0f;
		float currentTax = 0f;
		float currentRate = 0f;
		float taxCredit = 0f;

		for(Family f: families) {

			//Checks for a null family, families that haven't started being filed.
			if(f == null) {

				break;
			}

			int maxBracket = Taxation.maxIncomeTaxBracket(f); //Gets max bracket for family income
			float taxableIncome = f.getTaxableIncome();

			currentTax = 0f;

			for(int i = 1; i <= maxBracket; i++) {

				currentRate = Taxation.bracketTaxRate(i, f.getFilingStatus()); //Gets the tax rate based on filing status and income
				currentTax += (Taxation.bracketIncome(f, i) * currentRate); //Gets the portion of income that the taxable falls into.
			}

			totalTax += currentTax; 
		}

		return totalTax;
	}

	/**
	*Calculates the total tax credits that all families are eligible for
	*@return returns the total tax credits among all families.
	*@version 1.0
	*/
	public float taxCredits() {

		float taxCredits = 0f;

		for(Family f: families) {

			//Checks for a null family, families that haven't started being filed.
			if(f == null) {

				break;
			}
			taxCredits += f.taxCredit(); //Sums up all the credits in all families.
		}

		return taxCredits;
	}

	/**
	*Gets the total number of families that are eligible for filing.
	*@return returns the number of families allowed to file taxes.
	*/

	public int numberOfReturnsFiled() {

		return this.nextPos;
	}

	/**
	*Gets the total number of people that are filing for taxes in all the families.
	*@return returns the number of total people involved in filing taxes.
	*/

	public int numberOfPersonsFiled() {

		int totalPeople = 0;

		for(int i = 0; i< nextPos; i++) {

			Family f = families[i];
			totalPeople += (f.getNumAdults() + f.getNumChildren()); //Adds the total adults and children that have filed their taxes.
		}

		return totalPeople;
	}

	/**
	*Gets a given family at an index in range.
	*@param index position in array to get the given family.
	*@return returns the family at a given index.
	*/

	public Family getTaxReturn(int index) {

		//Checks to see if index is not in range.
		if((index < 0) || (index > families.length)) {

			return null;
		}

		return families[index];
	}

	/**
	*Gets the assortment of families in a tax year.
	*@return returns the assortment of families.
	*/

	public Family[] getFamilies() {

		return families;
	}

}