package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.controllers.PermissionController;
import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PermissionControllerTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PermissionRepository repository;

    @Test
    public void testFindAll() {
        // Create instance of the controller to test
        PermissionController permissionController = new PermissionController();
        permissionController.repository = this.repository;

        // Persist some permissions
        Permission permission1 = new Permission();
        Permission permission2 = new Permission();
        entityManager.persist(permission1);
        entityManager.persist(permission2);
        entityManager.flush();

        // Test the findAll method
        ResponseEntity<List<Permission>> response = permissionController.findAll();

        // Verify the result
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().contains(permission1));
        assertTrue(response.getBody().contains(permission2));
    }
}