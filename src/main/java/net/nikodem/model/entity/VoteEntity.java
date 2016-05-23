package net.nikodem.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "Vote")
public class VoteEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private AnswerEntity chosenAnswer;

    @OneToOne
    @NotNull
    private VoterKeyEntity voterKey;

    @Column
    @NotNull
    private String voteKey;

    public VoteEntity(AnswerEntity chosenAnswer, VoterKeyEntity voterKey, String voteKey) {
        this.chosenAnswer = chosenAnswer;
        this.voterKey = voterKey;
        this.voteKey = voteKey;
    }

    public VoteEntity() {
    }

    public AnswerEntity getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(AnswerEntity chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    public VoterKeyEntity getVoterKey() {
        return voterKey;
    }

    public void setVoterKey(VoterKeyEntity voterKey) {
        this.voterKey = voterKey;
    }

    public String getVoteKey() {
        return voteKey;
    }

    public void setVoteKey(String voteKey) {
        this.voteKey = voteKey;
    }
}
