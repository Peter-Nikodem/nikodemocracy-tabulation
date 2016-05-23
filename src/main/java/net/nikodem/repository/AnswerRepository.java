package net.nikodem.repository;

import net.nikodem.model.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    AnswerEntity findByElectionElectionIdAndAnswerText(String electionId, String answerText);

    @Query("SELECT CASE WHEN (COUNT(*) > 0)  THEN true ELSE false END FROM Answer a WHERE a.election.electionId = ?1 AND a.answerText = ?2")
    boolean existsByElectionIdAndAnswerText(String electionId, String chosenAnswer);

    @Query("SELECT a.answerText FROM Answer a WHERE a.election = ?1")
    List<String> findAnswerTextsByElection(ElectionEntity election);
}


