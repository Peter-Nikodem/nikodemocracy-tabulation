package net.nikodem.service;

import net.nikodem.model.entity.*;
import net.nikodem.model.dto.*;
import net.nikodem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

@Service
public class ElectionReceivingService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private VoterKeyRepository voterKeyRepository;

    /**
     * Election information comes encrypted and signed with a shared symmetric secret from a single trusted source.
     * Hence, no validation is needed.
     */
    @Transactional
    public void receiveElection(ElectionInformation e) {
        ElectionEntity electionEntity = saveElectionEntity(e.getElectionId(), e.getQuestion());
        saveAnswers(e.getAnswers(), electionEntity);
        saveVoterKeys(e.getVoterKeys(), electionEntity);
    }

    private ElectionEntity saveElectionEntity(String electionId, String question) {
        return electionRepository.save(new ElectionEntity(electionId, question));
    }

    private void saveVoterKeys(List<String> eligibleVoterKeys, ElectionEntity electionEntity) {
        List<VoterKeyEntity> voterKeyEntities = eligibleVoterKeys.stream()
                .map(key -> new VoterKeyEntity(key, electionEntity))
                .collect(Collectors.toList());
        voterKeyRepository.save(voterKeyEntities);
    }

    private void saveAnswers(List<String> possibleAnswers, ElectionEntity electionEntity) {
        List<AnswerEntity> answerEntities = possibleAnswers.stream()
                .map(answer -> new AnswerEntity(answer, electionEntity))
                .collect(Collectors.toList());
        answerRepository.save(answerEntities);
    }
}
