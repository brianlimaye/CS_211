import java.time.LocalDate;

public class Voter {

    private static int numberOfVoters;

    private String voterID;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private Address address;
    private long socialSecurityNumber;

    public Voter() {
        this(null, null, null, null, null, null, 0l);
    }

    public Voter(final String voterId, final String firstName, final String lastName, final LocalDate birthDate, final String gender, final Address address, final long socialSecurity) {
        this.voterID = voterId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = birthDate;
        this.gender = gender;
        this.address = address;
        this.socialSecurityNumber = socialSecurity;

        numberOfVoters++;
    }

    public static void incrementVoters() {
        ++numberOfVoters;
    }

    public static void decrementVoters() {
    	if (numberOfVoters == 0)
		{
			return;
		}
        --numberOfVoters;
    }

    public static void reset() {
        numberOfVoters = 0;
    }

    public static int getNumberOfVoters() {
        return numberOfVoters;
    }

    public static void main(String[] args) {

        final Voter voter1 = new Voter("112", "Brian", "Limaye", LocalDate.of(2002, 3, 16), "Male", new Address("734 Bonnie", 20176, "Leesburg", "Virginia"), 1l);
        final Voter voter2 = new Voter("112", "Brian", "Limaye", LocalDate.of(2002, 3, 16), "Male", new Address("734 Bonnie", 20176, "Leesburg", "Virginia"), 1l);

        System.out.println(voter1.equals(voter2));
    }

    public String getVoterId() {
        return voterID;
    }

    public void setVoterId(final String voterId) {
        this.voterID = voterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return dateOfBirth;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.dateOfBirth = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public long getSocialSecurity() {

        return socialSecurityNumber;
    }

    public void setSocialSecurity(final long socialSecurity) {
        this.socialSecurityNumber = socialSecurity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(voterID);
        builder.append(",");
        builder.append(" ");
        builder.append(firstName);
        builder.append(",");
        builder.append(" ");
        builder.append(lastName);
        builder.append(",");
        builder.append(" ");
        builder.append(dateOfBirth.toString());
        builder.append(",");
        builder.append(" ");
        builder.append(gender);
        builder.append(",");
        builder.append(" ");
        builder.append(address);
        builder.append(",");
        builder.append(" ");
        builder.append(socialSecurityNumber);
        return builder.toString();
    }

    public boolean equals(final Voter voter) {
        if (voter == null) {
            return false;
        }
        return (voter.toString().equalsIgnoreCase(toString()));
    }

    public int hashCode()
    {
        return toString().hashCode();
    }
}