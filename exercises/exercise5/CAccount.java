public class CAccount implements Account {

	protected double balance;
	protected int acctNumber;
	private static double interestRate = 0.05;

	public CAccount() {

		this(0, 0.0);
	}

	public CAccount(int acctNo, double bal) {

		this.acctNumber = acctNo;

		if(Double.compare(bal, 0.0) >= 0) {

			this.balance = bal;
		}
		else {

			this.balance = 0.0;
		}
	}

	public void withdraw(double amount) {

		if((Double.compare(amount, 0.0) <= 0) || (Double.compare(this.balance, 0.0) == 0)) {

			return;
		}

		double limit = (this.balance * 1.1) / 1.01;
		double charge = Math.min(limit, amount) * 0.01;

		if(Double.compare(amount, this.balance) > 0) {

			if(Double.compare(amount, limit) > 0) {

				this.balance -= limit; 
				this.balance -=  charge;
				return;
			}
		}
		
		this.balance -= amount;
		this.balance -=  charge;	
	}

	public void deposit(double amount) {

		if(Double.compare(amount, 0.0) >= 0) {

			double charge = amount * 0.01;
			this.balance += (amount - charge);
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