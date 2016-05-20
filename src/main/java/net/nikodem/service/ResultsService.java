package net.nikodem.service;

import net.nikodem.model.entity.*;
import net.nikodem.model.json.*;
import net.nikodem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class ResultsService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private VoteRepository voteRepository;

    public ElectionResults getElectionResults(String electionId) {
        ElectionEntity election = findElection(electionId);
        List<String> answers = findAnswers(election);
        List<VoteEntity> votes = findAllVotes(election);
        ResultPlace[] countedAndSortedVotes = countAndSortVotes(answers, votes);
        VoteProof[] enumaratedVoterKeysAndTheirAnswers = enumerateVoteKeysAndAnswers(votes);
        return new ElectionResults(election.getElectionId(), election.getQuestion(), countedAndSortedVotes,
                enumaratedVoterKeysAndTheirAnswers);
    }

    private ElectionEntity findElection(String electionId) {
        return electionRepository.findByElectionId(electionId);
    }

    private List<String> findAnswers(ElectionEntity election) {
        return answerRepository.findByElection(election)
                .stream()
                .map(AnswerEntity::getAnswerText)
                .collect(Collectors.toList());
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
                .sorted((x, y) -> x.getValue()
                        .compareTo(y.getValue()) * -1)
                .map(e -> new ResultPlace(e.getKey(), e.getValue()))
                .toArray(ResultPlace[]::new);
    }


}
