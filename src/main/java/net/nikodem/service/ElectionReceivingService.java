package net.nikodem.service;

import net.nikodem.model.entity.*;
import net.nikodem.model.json.*;
import net.nikodem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

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

    public void receiveElection(ElectionInformation information) {
        ElectionEntity electionEntity = saveElectionEntity(information.getElectionId(), information.getQuestion());
        saveAnswers(information.getAnswers(), electionEntity);
        saveVoterKeys(information.getVoterKeys(), electionEntity);
    }

    private ElectionEntity saveElectionEntity(String electionId, String question) {
        return electionRepository.save(new ElectionEntity(electionId, question));
    }

    private void saveVoterKeys(List<String> voterKeys, ElectionEntity electionEntity) {
        List<VoterKeyEntity> voterKeyEntities = voterKeys.stream()
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
