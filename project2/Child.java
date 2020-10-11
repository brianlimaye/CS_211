
public class Child extends Person {
	
	private String school;
	private float tuition;

	public Child(String name, String birthday, String ssn, float income, String school, float tuition) {

		super();
		setName(name);
		setBirthday(birthday);
		setSSN(ssn);
		setIncome(income);
		this.school = school;
		this.tuition = tuition;
	}

	public String toString() {

		if(school == null) {

			return null;
		}

		return super.toString() + " " + school;
	}

	public float getTuition() {

		return tuition;
	}

	@Override
	public float deduction(Family f) {

		float exemptionReduction = 0f;
		float childBaseExemption = Taxation.childBaseExemption;
		int numOfChildren = f.getNumChildren();
		Person[] people = f.getPeople();
		float exemptedIncome = this.getIncome();

		for(int i = 3; i <= numOfChildren; i++) {

			if(Float.compare(exemptionReduction, 0.5f) > 0) {

				break;
			}

			exemptionReduction += 0.05f;
		}

		if(Float.compare((childBaseExemption * (1.0f - exemptionReduction)), exemptedIncome) > 0) {

			return exemptedIncome;
		}
		
		return childBaseExemption * (1.0f - exemptionReduction);
	}

}