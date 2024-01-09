package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.repository.PermissionRepository;
import com.msgsystems.jbugger.echipa2.backend.service.PermissionService;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PermissionServiceTest {

    @Test
    public void testFindAll() {
        // Create mock
        PermissionRepository permissionRepository = Mockito.mock(PermissionRepository.class);

        // Create instance of the service to test
        PermissionService permissionService = new PermissionService(permissionRepository);

        // Mock the behavior of the findAll method
        Permission permission = new Permission();
        permission.setType("type");
        when(permissionRepository.findAll()).thenReturn(Arrays.asList(permission));

        // Test the findAll method
        ServiceOperationResult<List<Permission>> result = permissionService.findAll();

        // Verify the result
        assertEquals(1, result.getResult().size());
        assertEquals("type", result.getResult().get(0).getType());
    }

    @Test
    public void testFindByType() {
        // Create mock
        PermissionRepository permissionRepository = Mockito.mock(PermissionRepository.class);

        // Create instance of the service to test
        PermissionService permissionService = new PermissionService(permissionRepository);

        // Mock the behavior of the findByType method
        Permission permission = new Permission();
        permission.setType("type");
        when(permissionRepository.findByType("type")).thenReturn(Optional.of(permission));

        // Test the findByType method
        ServiceOperationResult<Permission> result = permissionService.findByType("type");

        // Verify the result
        assertEquals("type", result.getResult().getType());
    }
}