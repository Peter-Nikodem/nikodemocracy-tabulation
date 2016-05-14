package net.nikodem.model.json;

import java.util.Objects;

public class VoteSubmission {
    private String electionId;
    private String chosenAnswer;
    private String voterKey;
    private String voteKey;

    public VoteSubmission(String electionId, String chosenAnswer, String voterKey, String voteKey) {
        this.electionId = electionId;
        this.chosenAnswer = chosenAnswer;
        this.voterKey = voterKey;
        this.voteKey = voteKey;
    }

    public VoteSubmission() {
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(String chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    public String getVoterKey() {
        return voterKey;
    }

    public void setVoterKey(String voterKey) {
        this.voterKey = voterKey;
    }

    public String getVoteKey() {
        return voteKey;
    }

    public void setVoteKey(String voteKey) {
        this.voteKey = voteKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteSubmission that = (VoteSubmission) o;
        return Objects.equals(electionId, that.electionId) &&
                Objects.equals(chosenAnswer, that.chosenAnswer) &&
                Objects.equals(voterKey, that.voterKey) &&
                Objects.equals(voteKey, that.voteKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(electionId, chosenAnswer, voterKey, voteKey);
    }
}
