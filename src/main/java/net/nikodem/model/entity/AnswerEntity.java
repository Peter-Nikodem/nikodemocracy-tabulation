package net.nikodem.model.entity;

import javax.persistence.*;

@Entity(name = "Answer")
public class AnswerEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String answerText;

    @ManyToOne
    private ElectionEntity election;

    public AnswerEntity(String answerText,ElectionEntity election) {
        this.answerText = answerText;
        this.election = election;
    }

    public AnswerEntity() {
    }

    public ElectionEntity getElection() {
        return election;
    }

    public void setElection(ElectionEntity election) {
        this.election = election;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
