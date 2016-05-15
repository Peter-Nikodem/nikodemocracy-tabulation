package net.nikodem.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name ="VoterKey")
public class VoterKeyEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    @NotNull
    private String voterKey;

    @ManyToOne
    @NotNull
    private ElectionEntity election;

    public VoterKeyEntity(String voterKey, ElectionEntity election) {
        this.voterKey = voterKey;
        this.election = election;
    }

    public VoterKeyEntity() {
    }

    public String getVoterKey() {
        return voterKey;
    }

    public void setVoterKey(String voterKey) {
        this.voterKey = voterKey;
    }

    public ElectionEntity getElection() {
        return election;
    }

    public void setElection(ElectionEntity election) {
        this.election = election;
    }
}
