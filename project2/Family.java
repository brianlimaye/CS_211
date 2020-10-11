
public class Family {

	private int numMembers;
	private int filingStatus;
	private Person[] people;

	public Family(int members, int filingStatus) {

		people = new Person[members];
		this.filingStatus = filingStatus;
	}

	public boolean addMember(Person p) {

		if(p == null) {

			return false;
		}

		for(int i = 0; i < people.length; i++) {

			if(people[i] == null) {

				people[i] = p;
				return true;
			}
		}
		return false;
	}

	public int getNumAdults() {

		int adultCount = 0;

		for(int i = 0; i < people.length; i++) {

			if(people[i] instanceof Adult) {

				++adultCount;
			}
		}
		return adultCount;
	}

	public int getNumChildren() {

		int childrenCount = 0;

		for(int i = 0; i < people.length; i++) {

			if(people[i] instanceof Child) {

				++childrenCount;
			}
		}
		return childrenCount;
	}

	public int getFilingStatus() {

		return filingStatus;
	}

	public float getTaxableIncome() {

		float totalTaxableIncome = 0f;

		for(Person p: people) {

			if(p instanceof Adult) {

				totalTaxableIncome += ((Adult) p).adjustedIncome();
				totalTaxableIncome -= ((Adult) p).deduction(this);
			}
			if(p instanceof Child) {

				totalTaxableIncome += ((Child) p).getIncome();
				totalTaxableIncome -= ((Child) p).deduction(this);
			}
		}

		return totalTaxableIncome;
	}

	private float preTaxCredit() {

		float taxableIncome = getTaxableIncome();
		float halfMedianIncome = Taxation.medianIncomePerCapita / 2;
		int bracket = -1;

		if(taxableIncome > halfMedianIncome) {

			return 0.0f;
		}

		if((filingStatus == 1) && (Float.compare(taxableIncome, 10000f) > 0)) {

			bracket = 2;
		}
		else {

			bracket = 1;
		}

		return taxableIncome * Taxation.bracketTaxRate(bracket, filingStatus);
	}	

	public float taxCredit() {

		float totalCredit = 0f;
		float taxableIncome = this.getTaxableIncome();
		float halfMedianIncome = Taxation.medianIncomePerCapita / 2;

		if(Float.compare(taxableIncome, halfMedianIncome) > 0) {

			return 0.0f;
		}

		int diff = (int)(Math.floor(taxableIncome)) / 1000;

		if(diff <= 0) {

			return 0.0f;
		}

		totalCredit = 30 * diff;

		for(Person p: people) {

			if(p instanceof Child) {

				totalCredit += Math.min(((Child) p).getTuition(), 1000);
			}
		}

		float min = Math.min(totalCredit, preTaxCredit());

		if(filingStatus == 3) {

			min /= 2;
		}

		return Math.min(min, 2000f);
	}

	public float calculateTax() {

		float totalTax = 0f;
		float currentRate = 0f;
		float taxCredit = 0f;
		float taxableIncome = this.getTaxableIncome();
		int maxBracket = Taxation.maxIncomeTaxBracket(this);

		for(int i = 1; i <= maxBracket; i++) {

			currentRate = Taxation.bracketTaxRate(i, this.filingStatus);
			totalTax += Taxation.bracketIncome(this, i) * currentRate;
		}

		totalTax -= taxCredit();

		for(Person p: people) {

			if(p instanceof Adult) {

				totalTax -= ((Adult) p).taxWithheld();
			}
		}

		return totalTax;
	}

	public Person[] getPeople() {

		return people;
	}

}