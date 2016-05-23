package net.nikodem.model.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity(name = "Election")
public class ElectionEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    @NotNull
    private String electionId;

    @Column
    @NotNull
    private String question;

    public ElectionEntity() {
    }

    public ElectionEntity(String electionId, String question) {
        this.electionId = electionId;
        this.question = question;
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
}
