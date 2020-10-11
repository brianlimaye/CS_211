
public class Adult extends Person {

	private String employer;

	public Adult(String name, String birthday, String ssn, float income, String employer) {

		super();
		setName(name);
		setBirthday(birthday);
		setSSN(ssn);
		setIncome(income);
		this.employer = employer;
	}

	public String toString() {

		return super.toString() + " " + this.getIncome();
	}

	public float adjustedIncome() {

		float taxableIncome = this.getIncome();
		float ssIncome = taxableIncome;

		if(taxableIncome > Taxation.socialSecurityIncomeLimit) {

			ssIncome = Taxation.socialSecurityIncomeLimit;
		}

		float medicareTax = taxableIncome * (Taxation.medicareRate / 2);
		float ssTax = ssIncome * (Taxation.socialSecurityRate / 2);

		return (taxableIncome - (medicareTax + ssTax));
	}

	public float taxWithheld() {

		float taxableIncome = getIncome();
		float first50K = 0f;
		float next100K = 0f;
		float remaining = 0f;

		if(Float.compare(taxableIncome, 50000f) > 0) {

			first50K = 50000 * 0.1f;
			taxableIncome -= 50000f;
		}
		else {

			first50K = taxableIncome * 0.1f;
			return first50K;
		}

		
		if(Float.compare(taxableIncome, 100000f) > 0) {

			next100K = 100000 * 0.15f;
			taxableIncome -= 100000;
		}
		else {

			next100K = taxableIncome * 0.15f;
			return first50K + next100K;
		}

		remaining = taxableIncome * 0.2f;
		return (first50K + next100K + remaining);
	}

	@Override
	public float deduction(Family f) {
		
		float exemptedIncome = adjustedIncome();
		float exemptionReduction = 0.0f;
		float adultBaseExemption = Taxation.adultBaseExemption;

		if((f.getNumAdults() == 1) && (f.getNumChildren() > 0)) {

			adultBaseExemption *= 2;
		}

		int diff = (int)(Math.floor(exemptedIncome - 100000f) / 1000);

		if(diff > 0) {

			exemptionReduction = 0.005f * diff;

			if(Float.compare(exemptionReduction, 0.30f) > 0) {

				exemptionReduction = 0.30f;
			}
		}
		
		float deduction = adultBaseExemption * (1.0f - exemptionReduction);

		if(deduction > adjustedIncome()) {

			return adjustedIncome();
		}
		return deduction;
	}

	public String getEmployer() {

		return employer;
	}
	
}