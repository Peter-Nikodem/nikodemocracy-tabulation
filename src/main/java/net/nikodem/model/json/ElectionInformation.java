package net.nikodem.model.json;

import java.util.*;

public class ElectionInformation {
    private String electionId;
    private String question;
    private List<String> answers;
    private List<String> voterKeys;

    public ElectionInformation() {
    }

    public ElectionInformation(String electionId, String question, List<String> answers, List<String> voterKeys) {
        this.electionId = electionId;
        this.question = question;
        this.answers = answers;
        this.voterKeys = voterKeys;
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getVoterKeys() {
        return voterKeys;
    }

    public void setVoterKeys(List<String> voterKeys) {
        this.voterKeys = voterKeys;
    }
}
