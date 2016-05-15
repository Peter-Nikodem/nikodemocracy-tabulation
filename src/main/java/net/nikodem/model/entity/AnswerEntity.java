package net.nikodem.model.entity;

import javax.persistence.*;

@Entity(name = "Answer")
public class AnswerEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String answerText;

    @Column
    private int answerOrder;

    @ManyToOne
    private ElectionEntity election;

    public AnswerEntity(ElectionEntity election, String answerText, int answerOrder) {
        this.election = election;
        this.answerText = answerText;
        this.answerOrder = answerOrder;
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

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }
}
