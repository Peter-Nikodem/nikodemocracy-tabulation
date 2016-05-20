package net.nikodem.service;

import net.nikodem.model.exception.*;
import net.nikodem.model.json.VoteSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteReceivingService {

    private VoteSubmissionValidator voteSubmissionValidator;

    private VoteSaver voteSaver;

    public void receiveVote(VoteSubmission voteSubmission) throws NikodemocracyRequestException {
        validate(voteSubmission);
        storeVoteAndUpdatePermission(voteSubmission);
    }

    private void validate(VoteSubmission voteSubmission) throws NikodemocracyRequestException {
        voteSubmissionValidator.validate(voteSubmission);
    }

    private void storeVoteAndUpdatePermission(VoteSubmission voteSubmission) {
        voteSaver.save(voteSubmission);
    }

    @Autowired
    public void setVoteSubmissionValidator(VoteSubmissionValidator voteSubmissionValidator) {
        this.voteSubmissionValidator = voteSubmissionValidator;
    }

    @Autowired
    public void setVoteSaver(VoteSaver voteSaver) {
        this.voteSaver = voteSaver;
    }
}
