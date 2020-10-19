public class SAccount implements Account {
	
	protected double balance;
	protected int acctNumber;
	private static double interestRate = 0.01;

	public SAccount() {

		this(0, 0.0);
	}

	public SAccount(int acctNo, double bal) {

		this.acctNumber = acctNo;

		if(Double.compare(bal, 0.0) >= 0) {

			this.balance = bal;
		}
		else {

			this.balance = 0.0;
		}
	}

	public void withdraw(double amount) {

		if((Double.compare(amount, 0.0) <= 0)) {

			return;
		}

		if((Double.compare(amount, this.balance) <= 0) && (Double.compare(amount, 0.0) >= 0)) {

			this.balance -= amount;
		}
		else {

			this.balance -= this.balance;
		}
	}

	public void deposit(double amount) {

		if(Double.compare(amount, 0.0) >= 0) {

			this.balance += amount;
		}
	}

	public int getAccountNumber() {

		return acctNumber;
	}

	public void setAccountNumber(int acctNo) {

		if(acctNo > 0) {

			this.acctNumber = acctNo;
		}
	}

	public double getBalance() {

		return this.balance;
	}

	public void setBalance(double bal) {

		if(Double.compare(bal, 0.0) > 0) {

			this.balance = bal;
		}
		else {

			this.balance = 0.0;
		}
	}

	public static double getInterestRate() {

		return interestRate;
	}

	public static void setInterestRate(double newInterest) {

		if(Double.compare(newInterest, 0.0) > 0) {

			interestRate = newInterest;
		}
	}
}