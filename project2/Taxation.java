/**
*This class stores and provides functionality for rates and income portions.
*@author Brian Limaye
*@version 1.0
*@since 1.0 
*/

public class Taxation {

	/**
	*Base Social Security Rate
	*/
	public static final float socialSecurityRate = 0.124f;
	/**
	*Base Social Security Income Limit that taxes apply to
	*/
	public static final float socialSecurityIncomeLimit = 137700f;
	/**
	*Base Medicare Rate
	*/
	public static final float medicareRate = 0.029f;
	/**
	*Base Adult Exemption without any factors applied
	*/
	public static final float adultBaseExemption = 3000f;
	/**
	*Base Child Exemption without any factors applied
	*/
	public static final float childBaseExemption = 2000f;
	/**
	*Current Median Income Per Capita for 2020 (Subject to change).
	*/
	public static final float medianIncomePerCapita = 31099f;

	private static final IncomeBrackets[][] incomeBrackets = {
											   			     {new IncomeBrackets(0f, 10000f), new IncomeBrackets(0f, 20000f), new IncomeBrackets(0f, 12000f)},
											   			     {new IncomeBrackets(10000.01f, 40000f), new IncomeBrackets(20000.01f, 70000f), new IncomeBrackets(12000.01f, 44000f)},
											   			     {new IncomeBrackets(40000.01f, 80000f), new IncomeBrackets(70000.01f, 160000f), new IncomeBrackets(44000.01f, 88000f)},
											   			     {new IncomeBrackets(80000.01f, 160000f), new IncomeBrackets(160000.01f, 310000f), new IncomeBrackets(88000.01f, 170000f)},
											   			     {new IncomeBrackets(160000.01f, Float.MAX_VALUE), new IncomeBrackets(310000f, Float.MAX_VALUE), new IncomeBrackets(170000.01f, Float.MAX_VALUE)}
											  		         };
	private static final float[][] taxRates = { {0.1f, 0.1f, 0.1f}, {0.12f, 0.12f, 0.12f}, {0.22f, 0.23f, 0.24f}, {0.24f, 0.25f, 0.26f}, {0.32f, 0.33f, 0.35f}};

	
	/**
	*Gets the number of tax brackets for all the income ranges.
	*@return returns the max bracket that an income corresponds to
	*/

	public static int getNumTaxBrackets() {

		return taxRates.length;
	}	

	/**
	*Gets the max bracket that a family's income belongs to, based on their filing status as well.
	*@param f The family involved in the max bracket calculation.
	*@return returns the max bracket a family falls into.
	*/

	public static int maxIncomeTaxBracket(Family f) {

		if(f == null) {

			return 0;
		}

		float familyIncome = f.getTaxableIncome();
		int filingStatus = f.getFilingStatus();
		int bracketNum = -1;

		for(int row = 0; row < incomeBrackets.length; row++) {

			float upperBound = incomeBrackets[row][filingStatus - 1].getUpperBound(); //Gets lower bound of income range
			float lowerBound = incomeBrackets[row][filingStatus - 1].getLowerBound(); //Gets upper bound of income range

			//Special case for when bracket is 5 and family income is greater than the lower bound.
			if((Float.compare(familyIncome, lowerBound) >= 0) && (row == incomeBrackets.length - 1)) {

				bracketNum = 5;
				break;
			}

			//Regular case for checking whether family income is in range.
			else if((Float.compare(familyIncome, upperBound) <= 0) && (Float.compare(familyIncome, lowerBound) >= 0)) {

				bracketNum = row + 1;
				break;
			}
		}

		return bracketNum;
	}

	/**
	*Gets the portion of income that a family's taxable income falls into.
	*@param f The family involved in the bracket portion calculation.
	*@param b The bracket number that determines the income portion.
	*@return returns the portion of income that a taxable income falls into.
	*/

	public static float bracketIncome(Family f, int b) {

		if(b < 1) {

			return 0.0f;
		}

		float familyIncome = f.getTaxableIncome();
		int currentRow = b - 1;
		float incomeRange = 0.0f;

		IncomeBrackets ib = incomeBrackets[currentRow][f.getFilingStatus() - 1];

		float upperBound = ib.getUpperBound();
		float lowerBound = ib.getLowerBound();

		//Case for when a family income falls into a portion of income not entirely.
		if((Float.compare(familyIncome, lowerBound) >= 0) && (((Float.compare(familyIncome, upperBound) <= 0) || (b == 5)))){

			incomeRange = familyIncome - lowerBound;
		}
		else {

			//When a family income falls into a portion of income entirely.
			if(b != 5) {

				return upperBound - lowerBound;
			}
		}

		return incomeRange;
	}

	/**
	*Gets the corresponding tax rate based on a bracket number/filing status
	*@param b Represents the bracket number
	*@param f Represents the filing status of a family: 1 = single, 2 = married filing jointly, 3 = married filing separate.
	*@return returns the corresponding tax rate based on bracket/filing status.
	*/

	public static float bracketTaxRate(int b, int f) {

		//Checks for invalid input
		if((b < 1) || (f < 1)) {

			return 0.0f;
		}

		int correspondingCol = f - 1;

		return taxRates[b - 1][correspondingCol];
	}

	/**
	*Gets an object representation of a certain income range based on bracket number/filing status
	*@param b Represents the bracket number.
	*@param f Represents the filing status of a family: 1 = single, 2 = married filing jointly, 3 = married filing separate.
	*@return returns the object representation of a given income range.
	*/

	public static IncomeBrackets getIncomeRange(int b, int f) {

		//Checks for invalid input
		if((b < 1) || (f < 1)) {

			return null;
		}

		return incomeBrackets[b - 1][f - 1];
	}


}