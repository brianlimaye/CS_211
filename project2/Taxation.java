
public class Taxation {

	public static final float socialSecurityRate = 0.124f;
	public static final float socialSecurityIncomeLimit = 137700f;
	public static final float medicareRate = 0.029f;
	public static final float adultBaseExemption = 3000f;
	public static final float childBaseExemption = 2000f;
	public static final float medianIncomePerCapita = 31099f;
	private static final IncomeBrackets[][] incomeBrackets = {
											   			     {new IncomeBrackets(0f, 10000f), new IncomeBrackets(0f, 20000f), new IncomeBrackets(0f, 12000f)},
											   			     {new IncomeBrackets(10000.01f, 40000f), new IncomeBrackets(20000.01f, 70000f), new IncomeBrackets(12000.01f, 44000f)},
											   			     {new IncomeBrackets(40000.01f, 80000f), new IncomeBrackets(70000.01f, 160000f), new IncomeBrackets(44000.01f, 88000f)},
											   			     {new IncomeBrackets(80000.01f, 160000f), new IncomeBrackets(160000.01f, 310000f), new IncomeBrackets(88000.01f, 170000f)},
											   			     {new IncomeBrackets(160000.01f, Float.MAX_VALUE), new IncomeBrackets(310000f, Float.MAX_VALUE), new IncomeBrackets(170000.01f, Float.MAX_VALUE)}
											  		         };
	private static final float[][] taxRates = { {0.1f, 0.1f, 0.1f}, {0.12f, 0.12f, 0.12f}, {0.22f, 0.23f, 0.24f}, {0.24f, 0.25f, 0.26f}, {0.32f, 0.33f, 0.35f}};

	public static int getNumTaxBrackets() {

		return taxRates.length;
	}	

	public static int maxIncomeTaxBracket(Family f) {

		float familyIncome = f.getTaxableIncome();
		int filingStatus = f.getFilingStatus();
		int bracketNum = -1;

		for(int row = 0; row < incomeBrackets.length; row++) {

			float upperBound = incomeBrackets[row][filingStatus - 1].getUpperBound();
			float lowerBound = incomeBrackets[row][filingStatus - 1].getLowerBound();

			if((Float.compare(familyIncome, lowerBound) >= 0) && (row == incomeBrackets.length - 1)) {

				bracketNum = 5;
				break;
			}

			else if((Float.compare(familyIncome, upperBound) <= 0) && (Float.compare(familyIncome, lowerBound) >= 0)) {

				bracketNum = row + 1;
				break;
			}
		}

		return bracketNum;
	}

	public static float bracketIncome(Family f, int b) {

		float familyIncome = f.getTaxableIncome();
		int currentRow = b - 1;
		float incomeRange = 0.0f;

		IncomeBrackets ib = incomeBrackets[currentRow][f.getFilingStatus() - 1];

		float upperBound = ib.getUpperBound();
		float lowerBound = ib.getLowerBound();

		if((Float.compare(familyIncome, lowerBound) >= 0) && (((Float.compare(familyIncome, upperBound) <= 0) || (b == 5)))){

			incomeRange = familyIncome - lowerBound;
		}
		else {

			if(b != 5) {

				return upperBound - lowerBound;
			}
		}

		return incomeRange;
	}

	public static float bracketTaxRate(int b, int f) {

		int correspondingCol = f - 1;

		return taxRates[b - 1][correspondingCol];
	}

	public static IncomeBrackets getIncomeRange(int b, int f) {

		return incomeBrackets[b - 1][f - 1];
	}


}