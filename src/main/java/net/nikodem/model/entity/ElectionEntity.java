package net.nikodem.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Election")
public class ElectionEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String electionId;

    @Column
    private String question;

    @Column
    private boolean isFinished;

    public ElectionEntity(){

    }

    public ElectionEntity(String electionId, String question) {
        this.electionId = electionId;
        this.question = question;
        this.isFinished = false;
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
