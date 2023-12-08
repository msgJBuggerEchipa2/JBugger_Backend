package com.msgsystems.jbugger.echipa2.backend.repository;

import com.msgsystems.jbugger.echipa2.backend.model.MockUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "mockuser", path = "mockuser")
public interface MockUserRepository extends JpaRepository<MockUser, Long> {
    Optional<MockUser> findByUsername(String username);
}
