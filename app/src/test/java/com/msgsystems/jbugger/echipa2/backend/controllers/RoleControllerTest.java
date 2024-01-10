package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.auth.AuthenticationService;
import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.service.PermissionService;
import com.msgsystems.jbugger.echipa2.backend.service.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleControllerTest {

    @Test
    public void testFindAllRoles() {
        // Create mocks
        RoleService roleService = mock(RoleService.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        PermissionService permissionService = mock(PermissionService.class);

        // Create instance of the controller to test
        RoleController roleController = new RoleController();

        // Mock the behavior of the findAllRoles method
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = Arrays.asList(role1, role2);
        when(roleService.findAllRoles()).thenReturn(ResponseEntity.ok(roles));

        // Test the findAllRoles method
        ResponseEntity<List<Role>> response = roleController.findAllRoles("token");

        // Verify the result
        assertEquals(roles, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    // Similar tests can be written for addPermissionToRole and
    // removePermissionFromRole methods
}