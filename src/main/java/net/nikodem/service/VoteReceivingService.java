package net.nikodem.service;

import net.nikodem.model.exception.*;
import net.nikodem.model.json.VoteSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteReceivingService {

    private VoteSubmissionValidator voteSubmissionValidator;

    private VoteSaver voteSaver;

    private ElectionCompletionChecker electionCompletionChecker;

    public void receiveVote(VoteSubmission voteSubmission) throws NikodemocracyException {
        validate(voteSubmission);
        storeVoteAndUpdatePermission(voteSubmission);
        asynchronouslyCheckIfVotingLimitHasBeenMet(voteSubmission);
    }

    private void validate(VoteSubmission voteSubmission) throws NikodemocracyException {
        voteSubmissionValidator.validate(voteSubmission);
    }

    private void storeVoteAndUpdatePermission(VoteSubmission voteSubmission) {
        voteSaver.save(voteSubmission);
    }

    private void asynchronouslyCheckIfVotingLimitHasBeenMet(VoteSubmission voteSubmission) {
        new Thread(checkElectionCompleteness(voteSubmission)).start();
    }

    private Runnable checkElectionCompleteness(VoteSubmission voteSubmission) {
        String electionId = voteSubmission.getElectionId();
        return () -> electionCompletionChecker.checkIfFinished(electionId);
    }

    @Autowired
    public void setVoteSubmissionValidator(VoteSubmissionValidator voteSubmissionValidator) {
        this.voteSubmissionValidator = voteSubmissionValidator;
    }

    @Autowired
    public void setVoteSaver(VoteSaver voteSaver) {
        this.voteSaver = voteSaver;
    }

    @Autowired
    public void setElectionCompletionChecker(ElectionCompletionChecker electionCompletionChecker) {
        this.electionCompletionChecker = electionCompletionChecker;
    }
}
