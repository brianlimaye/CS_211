public class Address {

	private String streetNumber;
	private int zipCode;
	private String city;
	private String state;

	public Address() {

		this.streetNumber = null;
		this.zipCode = 0;
		this.city = null;
		this.state = null;
	}

	public Address(String streetNumber, int zipCode, String city, String state) {

		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
		this.state = state;
	}

	public boolean equals(Address a) {

		return this.toString().equalsIgnoreCase(a.toString());
	}

	public String getStreetNumber() {

		return streetNumber;
	}

	public int getZipCode() {

		return zipCode;
	}

	public String getCity() {

		return city;
	}

	public String getState() {

		return state;
	}

	public void setStreetNumber(String streetNumber) {

		this.streetNumber = streetNumber;
	}

	public void setZipCode(int zipCode) {

		this.zipCode = zipCode;
	}

	public void setCity(String city) {

		this.city = city;
	}

	public void setState(String state) {

		this.state = state;
	}

	public String toString() {

		return ("Address: " + streetNumber + ", " + zipCode + ", " + city + ", " + state);
	}

	public static void main(String[] args) {


	}
}