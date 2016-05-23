package net.nikodem.model.dto;

import java.util.*;

public class ElectionResults extends AbstractNikodemocracyResponse {

    private String electionId;

    private String question;

    private ResultPlace[] sortedResults;

    private VoteProof[] voteProofs;

    public ElectionResults(String electionId, String question, ResultPlace[] sortedResults, VoteProof[] voteProofs) {
        this.electionId = electionId;
        this.question = question;
        this.sortedResults = sortedResults;
        this.voteProofs = voteProofs;
    }

    public ElectionResults() {
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ResultPlace[] getSortedResults() {
        return sortedResults;
    }

    public void setSortedResults(ResultPlace[] sortedResults) {
        this.sortedResults = sortedResults;
    }

    public VoteProof[] getVoteProofs() {
        return voteProofs;
    }

    public void setVoteProofs(VoteProof[] voteProofs) {
        this.voteProofs = voteProofs;
    }

    @Override
    public String toString() {
        return "ElectionResults{" +
                "electionId='" + electionId + '\'' +
                ", question='" + question + '\'' +
                ", sortedResults=" + Arrays.toString(sortedResults) +
                ", voteProofs=" + Arrays.toString(voteProofs) +
                '}';
    }
}
