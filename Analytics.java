public class Analytics {

	private float povertyThreshold;
	private TaxYear taxYear;

	public Analytics(TaxYear ty) {

		this.povertyThreshold = 26200;
		this.taxYear = ty;
	}

	public void setPovertyThreshold(float newThreshold) {

		if(newThreshold < 0) {

			return;
		}

		this.povertyThreshold = newThreshold;
	}

	public double averageFamilyIncome() {

		Family[] families = taxYear.getFamilies();
		double incomes = 0;
		int numOfIncomes = 0;

		for(Family f: families) {

			if(f == null) {

				break;
			}

			incomes += f.getTaxableIncome();
			++numOfIncomes;
		}

		if(numOfIncomes == 0.0) {

			return 0.0;
		}

		return (incomes / numOfIncomes);
	}

	public double averagePersonIncome() {

		Family[] families = taxYear.getFamilies();
		double incomes = 0;
		int numOfIncomes = 0;
		
		for(Family f: families) {

			if(f == null) {

				break;
			}

			Person[] people = f.getPeople();

			for(Person p: people) {

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

		if(numOfIncomes == 0) {

			return 0.0;
		}

		return (incomes / numOfIncomes);
	}

	public double maxFamilyIncome() {

		Family[] families = taxYear.getFamilies();
		double currentIncome = 0;
		double maxIncome = 0;

		for(Family f: families) {

			if(f == null) {

				break;
			}

			currentIncome = f.getTaxableIncome();
			
			if(Double.compare(currentIncome, maxIncome) > 0) {

				maxIncome = currentIncome;
			}
		}

		return maxIncome;
	}

	public double maxPersonIncome() {

		Family[] families = taxYear.getFamilies();
		double currentIncome = 0;
		double maxIncome = 0;
		
		for(Family f: families) {

			if(f == null) {

				break;
			}

			Person[] people = f.getPeople();

			for(Person p: people) {

				if(p instanceof Adult) {

					currentIncome = ((Adult) p).adjustedIncome();
					currentIncome -= ((Adult) p).deduction(f);
				}

				if(p instanceof Child) {

					currentIncome = ((Child) p).getIncome();
					currentIncome -= ((Child) p).deduction(f);	
				}

				if(Double.compare(currentIncome, maxIncome) > 0) {

					maxIncome = currentIncome;
				}
				currentIncome = 0;
			}

			
		}

		return maxIncome;
	}

	public int familiesBelowPovertyLimit() {

		Family[] families = taxYear.getFamilies();
		int povFamilyCount = 0;

		for(Family f: families) {

			if(f == null) {

				break;
			}

			double currentIncome = f.getTaxableIncome();
			
			if(Double.compare(currentIncome, povertyThreshold) < 0) {

				++povFamilyCount;
			}
		}

		return povFamilyCount;
	}

	public int familyRank(Family f) {

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

			if(Double.compare(fam.getTaxableIncome(), currentIncome) > 0) {

				++currentRank;
			}
		}

		return currentRank;
	}
}