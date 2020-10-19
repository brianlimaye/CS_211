import java.util.ArrayList;

public class BankATM {

	private ArrayList<Account> accounts;
	private int capacity;


	public BankATM() {

		this(100);
	}

	public BankATM(int numberAccounts) {

		this.accounts = new ArrayList<Account>();

		if(numberAccounts > 0) {

			this.capacity = numberAccounts;
		}
		else {

			this.capacity = 1;
		}
	}

	public BankATM(ArrayList <Account> ac) {

		if(ac != null) {

			this.accounts = ac;
		}
	}

	public ArrayList <Account> getAccount() {

		return accounts;
	}

	public void setAccount(ArrayList <Account> acc) {

		if(acc == null) {

			return;
		}

		this.accounts = acc;
	}

	public void add(Account acc) {

		if((acc != null) && (accounts.size() < capacity)) {

			accounts.add(acc);
		}
	}

	public boolean remove(Account acc) {

		if(acc == null) {

			return false;
		}

		return accounts.remove(acc);
	}

	public void calcInterest() {

		for(int i = 0; i< accounts.size(); i++) {

			Account curr = accounts.get(i);

			if(accounts.get(i) instanceof SAccount) {

				SAccount sa = (SAccount) curr;
				sa.setBalance(sa.getBalance() * (1 + SAccount.getInterestRate()));
			}

			if(accounts.get(i) instanceof CAccount) {

				CAccount ca = (CAccount) curr;
				ca.setBalance(ca.getBalance() * (1 + CAccount.getInterestRate()));
			}
		}
	}
}