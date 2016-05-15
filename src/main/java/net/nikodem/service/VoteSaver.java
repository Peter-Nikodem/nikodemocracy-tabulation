package net.nikodem.service;

import net.nikodem.model.entity.AnswerEntity;
import net.nikodem.model.entity.VoteEntity;
import net.nikodem.model.entity.VoterKeyEntity;
import net.nikodem.model.json.VoteSubmission;
import net.nikodem.repository.AnswerRepository;
import net.nikodem.repository.VoteRepository;
import net.nikodem.repository.VoterKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoteSaver {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private VoterKeyRepository voterKeyRepository;
    @Autowired
    private VoteRepository voteRepository;

    public void save(VoteSubmission voteSubmission) {
        voteRepository.save(new VoteEntity(
                findAnswer(voteSubmission),
                findVoterKey(voteSubmission),
                getVoteKey(voteSubmission)));
    }

    private AnswerEntity findAnswer(VoteSubmission voteSubmission) {
        String electionId = voteSubmission.getElectionId();
        String chosenAnswer = voteSubmission.getChosenAnswer();
        return answerRepository.findByElectionElectionIdAndAnswerText(electionId, chosenAnswer);
    }

    private VoterKeyEntity findVoterKey(VoteSubmission voteSubmission) {
        String electionId = voteSubmission.getElectionId();
        String voterKey = voteSubmission.getVoterKey();
        return voterKeyRepository.findByElectionElectionIdAndVoterKey(electionId, voterKey);
    }

    private String getVoteKey(VoteSubmission voteSubmission) {
        return voteSubmission.getVoteKey();
    }
}
