import java.time.LocalDate;

public class VoterRegistry {

    private Voter[] voters;

    public VoterRegistry() {
        this(100);
    }

    public VoterRegistry(final int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be > 0!");
        }
        this.voters = new Voter[capacity];
    }

    public static void main(String[] args) {
        VoterRegistry vr = new VoterRegistry(10);
        vr.addVoter(new Voter("112", "Brian", "Limaye", LocalDate.of(2002, 3, 16), "Male", new Address("734 Bonnie", 20176, "Leesburg", "Virginia"), 1l));
        vr.addVoter(new Voter("453", "John", "Heldon", LocalDate.of(1995, 4, 30), "Male", new Address("567 Hillbrook Terrace", 20176, "Anchorage", "Alaska"), 5l));

        long startTime = System.currentTimeMillis();

        vr.deleteVoter(new Voter("112", "Brian", "Limaye", LocalDate.of(2002, 3, 16), "Male", new Address("734 Bonnie", 20176, "Leesburg", "Virginia"), 1l));

        final Voter[] voterData = vr.getVoterData();
        for (int i = 0; i < voterData.length; i++) {
            System.out.println(voterData[i]);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("deleteVoter() executed in about: " + (endTime - startTime) + " milliseconds!");

        vr.addVoter(new Voter("112", "Brian", "Limaye", LocalDate.of(2002, 3, 16), "Male", new Address("734 Bonnie", 20176, "Leesburg", "Virginia"), 1l));

        startTime = System.currentTimeMillis();

        vr.deleteVoter2(new Voter("112", "Brian", "Limaye", LocalDate.of(2002, 3, 16), "Male", new Address("734 Bonnie", 20176, "Leesburg", "Virginia"), 1l));
        System.out.println(vr.getVoterData());

        endTime = System.currentTimeMillis();

        System.out.println("deleteVoter2() executed in about: " + (endTime - startTime) + " milliseconds!");
    }

    public int getCapacity() {
        return voters.length;
    }

    public Voter[] getVoterData() {
        return this.voters;
    }

    public boolean isEmpty() {
        int nullCount = 0;
        if (voters == null) {
            return false;
        }
        for (Voter v : voters) {
            if (v == null) {
                ++nullCount;
            }
        }
        return nullCount == voters.length;
    }

    public boolean isFull() {
        int voterCount = 0;
        if (voters == null) {
            return false;
        }
        for (Voter v : voters) {
            if (v != null) {
                ++voterCount;
            }
        }
        return voterCount == voters.length;
    }

    private void fillVoters(Voter[] newVoters) {
        for (int i = 0; i < voters.length; i++) {
            newVoters[i] = voters[i];
        }
    }

    public void addVoter(Voter newVoter) {
        if ((voters == null) || (newVoter.getBirthDate().plusYears(18).isAfter(LocalDate.now()))) {
            return;
        }
        if (isFull()) {
            doubleSize();
        }
        for (int i = 0; i < voters.length; i++) {
            if ((voters[i] != null) && (newVoter.equals(voters[i]))) {
                return;
            }
            if (voters[i] == null) {
                voters[i] = newVoter;
                Voter.incrementVoters();
                break;
            }
        }
    }

    public Voter deleteVoter(Voter voter) {
        boolean reachedDeleted = false;
        Voter deletedVoter = null;

        if ((voters == null) || (voter == null) || (isEmpty())) {
            return null;
        }
        for (int i = 0; i < voters.length; i++) {
            if (reachedDeleted) {
                Voter temp = voters[i - 1];
                voters[i - 1] = voters[i];
                voters[i] = temp;
                continue;
            }
            if (voters[i].equals(voter)) {
                deletedVoter = voters[i];
                voters[i] = null;
                reachedDeleted = true;
                Voter.decrementVoters();
            }
        }
        return deletedVoter;
    }

    public Voter deleteVoter2(Voter voter) {
        int removedIndex = 0;
        boolean isRemoved = false;
        Voter deletedVoter = null;
        Voter[] newVoters;

        if ((voters == null) || (voter == null) || (isEmpty())) {
            return null;
        }

        for (int i = 0; i < voters.length; i++) {
            if (voters[i].equals(voter)) {
                deletedVoter = voters[i];
                removedIndex = i;
                voters[i] = null;
                Voter.decrementVoters();
                isRemoved = true;
                break;
            }
        }

        newVoters = new Voter[voters.length - 1];

        if (isRemoved) {
            if (removedIndex > 0) {
                System.arraycopy(voters, 0, newVoters, 0, removedIndex);
            }
            if (removedIndex < voters.length - 1) {
                System.arraycopy(voters, removedIndex + 1, newVoters, removedIndex, voters.length - removedIndex - 1);
            }
            this.voters = newVoters;
        }
        return deletedVoter;

    }

    public void doubleSize() {
        if (isFull()) {

            Voter[] newVoters = new Voter[voters.length * 2];
            fillVoters(voters);
            this.voters = newVoters;
        }
    }

    public int getNumberOfRegisteredVoters() {
        int numOfVoters = 0;

        for (Voter v : voters) {

            if (v != null) {

                ++numOfVoters;
            }
        }
        return numOfVoters;
    }
}