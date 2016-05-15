package net.nikodem.repository;

import net.nikodem.model.entity.*;
import org.springframework.data.jpa.repository.*;

public interface VoterKeyRepository extends JpaRepository<VoterKeyEntity, Long> {

    VoterKeyEntity findByElectionElectionIdAndVoterKey(String electionId, String voterKey);

    @Query("SELECT CASE WHEN (COUNT(*) > 0)  THEN true ELSE false END FROM VoterKey v WHERE v.election.electionId = ?1 AND v.voterKey = ?2")
    boolean existsByElectionIdAndVoterKey(String electionId, String voterKey);
}
