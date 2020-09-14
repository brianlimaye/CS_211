import java.time.LocalDate;

public class Voter {

    private static int numOfVoters;
    private String voterID;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private Address address;
    private long socialSecurityNum;

    public Voter() {

        this.voterID = null;
        this.firstName = null;
        this.lastName = null;
        this.dateOfBirth = null;
        this.gender = null;
        this.address = null;
        this.socialSecurityNum = 0;
    }

    public Voter(String voterId, String firstName, String lastName, LocalDate birthDate, String gender, Address address, long socialSecurity) {

        this.voterID = voterId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = birthDate;
        this.gender = gender;
        this.address = address;
        this.socialSecurityNum = socialSecurity;
        numOfVoters++;
    }

    public String getVoterId() {

        return voterID;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public LocalDate getBirthDate() {

        return dateOfBirth;
    }

    public String getGender() {

        return gender;
    }

    public long getSocialSecurity() {

        return socialSecurityNum;
    }

    public Address getAddress() {

        return address;
    }

    public void setVoterId(String voterId) {

        this.voterID = voterId;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {

        this.dateOfBirth = birthDate;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    public static void incrementVoters() {

        ++numOfVoters;
    }

    public static void decrementVoters() {

        --numOfVoters;
    }

    public void setSocialSecurity(long socialSecurity) {

        this.socialSecurityNum = socialSecurity;
    }

    public void setAddress(Address address) {

        this.address = address;
    }

    public static void reset() {

        numOfVoters = 0;
    }

    public static int getNumberOfVoters() {

        return numOfVoters;
    }

    public boolean equals (Voter voter) {

        return this.firstName.equalsIgnoreCase(voter.getFirstName()) && this.lastName.equalsIgnoreCase(voter.getLastName()) && this.dateOfBirth.equals(voter.getBirthDate()) && this.gender.equalsIgnoreCase(voter.getGender()) && this.socialSecurityNum == voter.getSocialSecurity() && this.address.equals(voter.getAddress());
    }

    public String toString() {

        return (voterID + ", " + firstName + ", " + lastName + ", " + dateOfBirth + ", " + gender + ", " + address + ", " + socialSecurityNum);
    }

    public static void main(String[] args) {

        Voter voter1 = new Voter("112", "Brian", "Limaye", LocalDate.of(2002, 3, 16), "Male", new Address("734 Bonnie", 20176, "Leesburg", "Virginia"), 1l);
        Voter voter2 = new Voter("112", "Brian", "Limaye", LocalDate.of(2002, 3, 16), "Male", new Address("734 Bonnie", 20176, "Leesburg", "Virginia"), 1l);

        System.out.println(voter1.equals(voter2));

    }
}