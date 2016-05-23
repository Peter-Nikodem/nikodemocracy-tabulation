package net.nikodem.repository;

import net.nikodem.model.entity.ElectionEntity;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ElectionRepository extends JpaRepository<ElectionEntity, Long> {

    Optional<ElectionEntity> findByElectionId(String electionId);
}
