package com.msgsystems.jbugger.echipa2.backend.repository;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "permission", path = "permission")
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findById(String id);
    Optional<Permission> findByType(String type);
}
