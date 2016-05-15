package net.nikodem.repository;

import net.nikodem.model.entity.ElectionEntity;
import org.springframework.data.jpa.repository.*;

public interface ElectionRepository extends JpaRepository<ElectionEntity,Long> {
    ElectionEntity findByElectionId(String electionId);

    @Query("SELECT CASE WHEN (COUNT(*) > 0)  THEN true ELSE false END FROM Election e WHERE e.electionId = ?1 AND e.isFinished = true")
    boolean isElectionFinished(String electionId);
}
