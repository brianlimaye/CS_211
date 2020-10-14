/**
*This class represents analytics of a given tax year.
*@author Brian Limaye
*@version 1.0
*@since 1.0 
*/

public class Analytics {

	private float povertyThreshold;
	private TaxYear taxYear;

	/**
	*This initializes a new Analytics instance, setting the tax year.
	*@param ty Represents the tax year to analyze.
	*@version 1.0
	*/

	public Analytics(TaxYear ty) {

		this.povertyThreshold = 26200; //Default poverity threshold
		this.taxYear = ty;
	}

	/**
	*Sets the poverty threshold.
	*@param newThreshold the new threshold to be set (must be non-negative)
	*@version 1.0
	*/
	public void setPovertyThreshold(float newThreshold) {

		//Checks if negative
		if(newThreshold < 0) {

			return;
		}

		this.povertyThreshold = newThreshold;
	}

	/**
	*Calculates the average family income from a collection of families
	*@return returns the average family income from all families.
	*@version 1.0
	*/
	public double averageFamilyIncome() {

		Family[] families = taxYear.getFamilies();
		double incomes = 0;
		int numOfIncomes = 0;

		for(Family f: families) {

			//Checks for a null family, families that haven't started being filed.
			if(f == null) {

				break;
			}

			incomes += f.getTaxableIncome(); //Sums up all the total taxable income for all families
			++numOfIncomes;
		}

		//Prevents a divide by 0 error.
		if(numOfIncomes == 0) {

			return 0.0;
		}

		return (incomes / numOfIncomes); //Average formula
	}

	/**
	*Calculates the average person income from a collection of families
	*@return returns the average person income from all families.
	*@version 1.0
	*/
	public double averagePersonIncome() {

		Family[] families = taxYear.getFamilies();
		double incomes = 0;
		int numOfIncomes = 0;
		
		for(Family f: families) {

			//Checks for a null family, families that haven't started being filed.
			if(f == null) {

				break;
			}

			Person[] people = f.getPeople();

			for(Person p: people) {

				//Sums up the taxable income for each individual members of family
				if(p instanceof Adult) {

					incomes += ((Adult) p).adjustedIncome();
					incomes -= ((Adult) p).deduction(f);	
				}

				if(p instanceof Child) {

					incomes += ((Child) p).getIncome();
					incomes -= ((Child) p).deduction(f);
				}

				numOfIncomes++;
			}
		}

		//Divide by zero error
		if(numOfIncomes == 0) {

			return 0.0;
		}

		return (incomes / numOfIncomes); //average
	}

	/**
	*Calculates the maximum family income among all families.
	*@return returns the maximum family income.
	*@version 1.0
	*/
	public double maxFamilyIncome() {

		Family[] families = taxYear.getFamilies();
		double currentIncome = 0;
		double maxIncome = 0;

		for(Family f: families) {

			//Checks for a null family, families that haven't started being filed.
			if(f == null) {

				break;
			}

			currentIncome = f.getTaxableIncome();
			
			//Checks if the current income is more than the max
			if(Double.compare(currentIncome, maxIncome) > 0) {

				maxIncome = currentIncome;
			}
		}

		return maxIncome;
	}

	/**
	*Calculates the maximum person income among all families.
	*@return returns the maximum person income.
	*@version 1.0
	*/
	public double maxPersonIncome() {

		Family[] families = taxYear.getFamilies();
		double currentIncome = 0;
		double maxIncome = 0;
		
		for(Family f: families) {

			//Checks for a null family, families that haven't started being filed.
			if(f == null) {

				break;
			}

			Person[] people = f.getPeople();

			for(Person p: people) {

				//Sums up income for an individual.
				if(p instanceof Adult) {

					currentIncome = ((Adult) p).adjustedIncome();
					currentIncome -= ((Adult) p).deduction(f);
				}

				if(p instanceof Child) {

					currentIncome = ((Child) p).getIncome();
					currentIncome -= ((Child) p).deduction(f);	
				}

				//Checks if current income is more than the maximum income
				if(Double.compare(currentIncome, maxIncome) > 0) {

					maxIncome = currentIncome;
				}
				currentIncome = 0; //resets income for the next member
			}

			
		}

		return maxIncome;
	}

	/**
	*Calculates the number of familes below the current poverty limit.
	*@return returns the number of families below poverty limit.
	*@version 1.0
	*/
	public int familiesBelowPovertyLimit() {

		Family[] families = taxYear.getFamilies();
		int povFamilyCount = 0;

		for(Family f: families) {

			//Checks for a null family, families that haven't started being filed.
			if(f == null) {

				break;
			}

			double currentIncome = f.getTaxableIncome();
			
			//Checks if a given family income is below the set povertyThreshold
			if(Double.compare(currentIncome, povertyThreshold) < 0) {

				++povFamilyCount;
			}
		}

		return povFamilyCount;
	}

	/**
	*Calculates the family rank of a given family
	*@param f Family to be ranked.
	*@return returns the rank of a family
	*@version 1.0
	*/
	public int familyRank(Family f) {

		//Checks for null family
		if(f == null) {

			return 0;
		}

		int currentRank = 1;
		double currentIncome = f.getTaxableIncome();

		Family[] families = taxYear.getFamilies();

		for(Family fam: families) {

			if(fam == null) {

				break;
			}

			//After finding a family with a greater income than the current family, the rank increases or drops.
			if(Double.compare(fam.getTaxableIncome(), currentIncome) > 0) {

				++currentRank;
			}
		}

		return currentRank;
	}
}