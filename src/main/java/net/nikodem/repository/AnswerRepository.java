package net.nikodem.repository;

import net.nikodem.model.entity.AnswerEntity;
import org.springframework.data.jpa.repository.*;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    AnswerEntity findByElectionElectionIdAndAnswerText(String electionId, String answerText);

    @Query("SELECT CASE WHEN (COUNT(*) > 0)  THEN true ELSE false END FROM Answer a WHERE a.election.electionId = ?1 AND a.answerText = ?2")
    boolean existsByElectionIdAndAnswerText(String electionId, String chosenAnswer);
}


