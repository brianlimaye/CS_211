public class Address {

    private String streetNumber;
    private int zipCode;
    private String city;
    private String state;

    public Address() {
        this(null, 0, null, null);
    }

    public Address(final String streetNumber, final int zipCode, final String city, final String state) {

        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }

    public String getStreetNumber() {

        return streetNumber;
    }

    public void setStreetNumber(final String streetNumber) {

        this.streetNumber = streetNumber;
    }

    public int getZipCode() {

        return zipCode;
    }

    public void setZipCode(final int zipCode) {

        this.zipCode = zipCode;
    }

    public String getCity() {

        return city;
    }

    public void setCity(final String city) {

        this.city = city;
    }

    public String getState() {

        return state;
    }

    public void setState(final String state) {

        this.state = state;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder("Address: ");
        builder.append(streetNumber);
        builder.append(",");
        builder.append(" ");
        builder.append(zipCode);
        builder.append(",");
        builder.append(" ");
        builder.append(city);
        builder.append(",");
        builder.append(" ");
        builder.append(state);
        return builder.toString();
    }

    public boolean equals(final Address otherAddress) {
        if (otherAddress == null) {
            return false;
        }
        return this.toString().equalsIgnoreCase(otherAddress.toString());
    }

    public int hashCode()
    {
        return toString().hashCode();
    }
}