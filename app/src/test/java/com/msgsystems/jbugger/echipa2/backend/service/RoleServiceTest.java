package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.repository.PermissionRepository;
import com.msgsystems.jbugger.echipa2.backend.repository.RoleRepository;
import com.msgsystems.jbugger.echipa2.backend.service.RoleService;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @Test
    public void testFindAllRoles() {
        // Create mock
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

        // Create instance of the service to test
        RoleService roleService = new RoleService();

        // Mock the behavior of the findAll method
        Role role = new Role();
        role.setType("type");
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role));

        // Test the findAllRoles method
        ServiceOperationResult<List<Role>> result = roleService.findAllRoles();

        // Verify the result
        assertEquals(1, result.getResult().size());
        assertEquals("type", result.getResult().get(0).getType());
    }

    @Test
    public void testFindByType() {
        // Create mock
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

        // Create instance of the service to test
        RoleService roleService = new RoleService(roleRepository, null);

        // Mock the behavior of the findByType method
        Role role = new Role();
        role.setType("type");
        when(roleRepository.findByType("type")).thenReturn(Optional.of(role));

        // Test the findByType method
        ServiceOperationResult<Role> result = roleService.findByType("type");

        // Verify the result
        assertEquals("type", result.getResult().getType());
    }

    @Test
    public void testAddPermissionToRole() {
        // Create mock
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

        // Create instance of the service to test
        RoleService roleService = new RoleService(roleRepository, null);

        // Mock the behavior of the addPermissionToRole method
        Role role = new Role();
        role.setPermissions(new HashSet<>());
        Permission permission = new Permission();
        permission.setId(1);

        // Test the addPermissionToRole method
        ServiceOperationResult<Role> result = roleService.addPermissionToRole(role, permission);

        // Verify the result
        assertTrue(result.getResult().getPermissions().contains(permission));
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void testRemovePermissionFromRole() {
        // Create mock
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

        // Create instance of the service to test
        RoleService roleService = new RoleService(roleRepository, null);

        // Mock the behavior of the removePermissionFromRole method
        Role role = new Role();
        Permission permission = new Permission();
        permission.setId(1);
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));

        // Test the removePermissionFromRole method
        ServiceOperationResult<Role> result = roleService.removePermissionFromRole(role, permission);

        // Verify the result
        assertFalse(result.getResult().getPermissions().contains(permission));
        verify(roleRepository, times(1)).save(role);
    }
}