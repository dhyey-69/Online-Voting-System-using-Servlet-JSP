public class Result {
    private Candidate candidate;
    private int totalVotes;

    // Constructor
    public Result(Candidate candidate, int totalVotes) {
        this.candidate = candidate;
        this.totalVotes = totalVotes;
    }

    // Getter for candidate
    public Candidate getCandidate() {
        return candidate;
    }

    // Setter for candidate
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    // Getter for totalVotes
    public int getTotalVotes() {
        return totalVotes;
    }

    // Setter for totalVotes
    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}
