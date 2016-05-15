package net.nikodem.repository;

import net.nikodem.model.entity.VoteEntity;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteEntity,Long> {
    Optional<VoteEntity> findByVoterKeyAndVoterKeyElectionElectionId(String voterKey, String electionId);

    @Query("SELECT CASE WHEN (COUNT(*) > 0)  THEN true ELSE false END FROM Vote v WHERE v.voterKey.election.electionId = ?1 AND v.voterKey.voterKey = ?2")
    boolean existsByElectionElectionIdAndVoterKeyVoterKey(String electionId, String voterKey);
}
