package net.nikodem.service;

import net.nikodem.model.exception.*;
import net.nikodem.model.dto.*;
import net.nikodem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import static net.nikodem.util.ValidationPreconditions.*;

@Component
public class VoteSubmissionValidator {

    private AnswerRepository answerRepository;
    private VoterKeyRepository voterKeyRepository;
    private VoteRepository voteRepository;

    public void validate(VoteSubmission voteSubmission) {
        if (isNullOrEmpty(voteSubmission.getElectionId())) {
            throw new EmptyElectionIdException();
        }
        if (isNullOrEmpty(voteSubmission.getChosenAnswer())) {
            throw new EmptyAnswerException();
        }
        if (isNullOrEmpty(voteSubmission.getVoterKey())) {
            throw new EmptyVoterKeyException();
        }
        if (voterKeyDoestNotExistForTheElection(voteSubmission.getElectionId(), voteSubmission.getVoterKey())) {
            throw new UnauthorizedVoteSubmissionException();
        }
        if (chosenAnswerDoesNotExistForTheElection(voteSubmission.getElectionId(), voteSubmission.getChosenAnswer())) {
            throw new AnswerDoesNotExistException(voteSubmission.getChosenAnswer());
        }
        if (voterKeyHasBeenUsed(voteSubmission.getElectionId(), voteSubmission.getVoterKey())) {
            throw new VoterKeyHasBeenUsedException();
        }
    }

    private boolean voterKeyDoestNotExistForTheElection(String electionId, String voterKey) {
        return !voterKeyRepository.existsByElectionIdAndVoterKey(electionId, voterKey);
    }

    private boolean chosenAnswerDoesNotExistForTheElection(String electionId, String chosenAnswer) {
        return !answerRepository.existsByElectionIdAndAnswerText(electionId, chosenAnswer);
    }

    private boolean voterKeyHasBeenUsed(String electionId, String voterKey) {
        return voteRepository.existsByElectionElectionIdAndVoterKeyVoterKey(electionId, voterKey);
    }

    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Autowired
    public void setVoterKeyRepository(VoterKeyRepository voterKeyRepository) {
        this.voterKeyRepository = voterKeyRepository;
    }

    @Autowired
    public void setVoteRepository(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }
}
