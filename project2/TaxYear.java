
public class TaxYear {

	private Family[] families;
	private int nextPos;
	
	public TaxYear(int max) {

		if(max < 0) {

			max = 1; 
		}

		families = new Family[max];
		nextPos = 0;
	}

	public boolean taxFiling(Family f) {

		if(f == null) {

			return false;
		}

		int filingStatus = f.getFilingStatus();
		int numOfAdults = f.getNumAdults();
		boolean isValidFiling = true;

		switch(filingStatus) {

			case 1: 
				if(numOfAdults > 1) {

					isValidFiling = false;
				}
				break;
			case 2:
				if(numOfAdults != 2) {

					isValidFiling = false;
				}
				break;
			case 3:
				if(numOfAdults > 2) {

					isValidFiling = false;
				}
				break;
		}

		if(numOfAdults < 1) {

			isValidFiling = false;
		}

		if((isValidFiling) && (nextPos < families.length)) {

			families[nextPos++] = f;
		}

		return isValidFiling;
	}

	public double taxWithheld() {

		double totalWithheld = 0;

		for(Family f: families) {

			if(f == null) {

				break;
			}

			Person[] people = f.getPeople();

			for(Person p: people) {

				if(p instanceof Adult) {

					totalWithheld += ((Adult) p).taxWithheld();
				}
			}
		}

		return totalWithheld;
	}

	public float taxDue() {

		float taxDue = 0f;
		float currentTax = 0f;

		for(Family f: families) {

			if(f == null) {

				break;
			}
			currentTax = f.calculateTax();
			taxDue += currentTax;
		}

		return taxDue;
	}

	public float taxOwed() {

		float totalTax = 0f;
		float currentTax = 0f;
		float currentRate = 0f;
		float taxCredit = 0f;

		for(Family f: families) {

			if(f == null) {

				break;
			}

			int maxBracket = Taxation.maxIncomeTaxBracket(f);
			float taxableIncome = f.getTaxableIncome();

			currentTax = 0f;

			for(int i = 1; i <= maxBracket; i++) {

				currentRate = Taxation.bracketTaxRate(i, f.getFilingStatus());
				currentTax += (Taxation.bracketIncome(f, i) * currentRate);
			}

			totalTax += currentTax;
		}

		return totalTax;
	}

	public float taxCredits() {

		/*
		if(nextPos != families.length) {

			return 0.0;
		}
		*/
		float taxCredits = 0f;

		for(Family f: families) {

			if(f == null) {

				break;
			}
			taxCredits += f.taxCredit();	
		}

		return taxCredits;
	}

	public int numberOfReturnsFiled() {

		return this.nextPos;
	}

	public int numberOfPersonsFiled() {

		int totalPeople = 0;

		for(int i = 0; i< nextPos; i++) {

			Family f = families[i];
			totalPeople += (f.getNumAdults() + f.getNumChildren());
		}

		return totalPeople;
	}

	public Family getTaxReturn(int index) {

		if((index < 0) || (index > families.length)) {

			return null;
		}

		return families[index];
	}

	public Family[] getFamilies() {

		return families;
	}

}