package net.nikodem.model.dto;

public class VoteProof {

    private String voteKey;
    private String answer;

    public VoteProof(String voteKey, String answer) {
        this.voteKey = voteKey;
        this.answer = answer;
    }

    public VoteProof() {
    }

    public String getVoteKey() {
        return voteKey;
    }

    public void setVoteKey(String voteKey) {
        this.voteKey = voteKey;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "VoteProof{" +
                "voteKey='" + voteKey + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
