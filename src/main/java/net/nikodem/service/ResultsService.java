package net.nikodem.service;

import net.nikodem.model.entity.*;
import net.nikodem.model.dto.*;
import net.nikodem.model.exception.*;
import net.nikodem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class ResultsService {

    private ElectionRepository electionRepository;

    private AnswerRepository answerRepository;

    private VoteRepository voteRepository;

    @Transactional(readOnly = true)
    public ElectionResults getElectionResults(String electionId) throws ElectionDoesNotExistException {
        ElectionEntity election = findElectionOrThrowException(electionId);
        List<String> answers = findAnswers(election);
        List<VoteEntity> votes = findAllVotes(election);
        ResultPlace[] countedAndSortedVotes = countAndSortVotes(answers, votes);
        VoteProof[] enumaratedVoterKeysAndTheirAnswers = enumerateVoteKeysAndAnswers(votes);
        return new ElectionResults(election.getElectionId(), election.getQuestion(), countedAndSortedVotes,
                enumaratedVoterKeysAndTheirAnswers);
    }

    private ElectionEntity findElectionOrThrowException(String electionId) {
        return electionRepository.findByElectionId(electionId)
                .orElseThrow(() -> new ElectionDoesNotExistException(electionId));
    }

    private List<String> findAnswers(ElectionEntity election){
        return answerRepository.findAnswerTextsByElection(election);
    }

    private List<VoteEntity> findAllVotes(ElectionEntity election) {
        return voteRepository.findByVoterKeyElection(election);
    }

    private VoteProof[] enumerateVoteKeysAndAnswers(List<VoteEntity> votes) {
        votes.sort((x, y) -> x.getVoteKey()
                .compareTo(y.getVoteKey()));
        return votes.stream()
                .map(this::getVoteProof)
                .toArray(VoteProof[]::new);
    }

    private VoteProof getVoteProof(VoteEntity vote) {
        return new VoteProof(vote.getVoteKey(), vote.getChosenAnswer()
                .getAnswerText());
    }

    private ResultPlace[] countAndSortVotes(List<String> answers, List<VoteEntity> votes) {
        Map<String, Integer> votesForAnswers = new HashMap<>();
        for (String answer : answers) {
            votesForAnswers.put(answer, 0);
        }
        for (VoteEntity vote : votes) {
            String answerText = vote.getChosenAnswer()
                    .getAnswerText();
            Integer currentNumberOfVotes = votesForAnswers.get(answerText);
            votesForAnswers.put(answerText, ++currentNumberOfVotes);
        }
        return votesForAnswers.entrySet()
                .stream()
                .sorted(countedVotesComparator())
                .map(e -> new ResultPlace(e.getKey(), e.getValue()))
                .toArray(ResultPlace[]::new);
    }

    private Comparator<Map.Entry<String, Integer>> countedVotesComparator() {
        return (x, y) -> y.getValue()
                .compareTo(x.getValue());
    }

    @Autowired
    public void setElectionRepository(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Autowired
    public void setVoteRepository(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }
}
