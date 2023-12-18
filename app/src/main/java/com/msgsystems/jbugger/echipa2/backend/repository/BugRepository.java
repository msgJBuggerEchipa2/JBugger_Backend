package com.msgsystems.jbugger.echipa2.backend.repository;

import com.msgsystems.jbugger.echipa2.backend.domain.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "bugs", path = "bugs")
public interface BugRepository extends JpaRepository<Bug, Long> {
    Optional<Bug> findById(Long id);

}
