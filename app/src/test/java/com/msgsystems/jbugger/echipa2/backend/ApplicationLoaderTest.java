package com.msgsystems.jbugger.echipa2.backend;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.repository.PermissionRepository;
import com.msgsystems.jbugger.echipa2.backend.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.ApplicationArguments;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ApplicationLoaderTest {

    @Test
    public void testRun() {
        // Create mocks
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        PermissionRepository permissionRepository = Mockito.mock(PermissionRepository.class);
        ApplicationArguments args = Mockito.mock(ApplicationArguments.class);

        // Create instance of the class to test
        ApplicationLoader loader = new ApplicationLoader(roleRepository, permissionRepository);

        // Mock the behavior of the findByType methods
        when(roleRepository.findByType(anyString())).thenReturn(Optional.empty());
        when(permissionRepository.findByType(anyString())).thenReturn(Optional.empty());

        // Call the run method
        loader.run(args);

        // Verify that the save methods were called the expected number of times
        verify(roleRepository, times(5)).save(any(Role.class));
        verify(permissionRepository, times(4)).save(any(Permission.class));
    }

    @Test
    public void testRunWhenRoleExists() {
        // Create mocks
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        PermissionRepository permissionRepository = Mockito.mock(PermissionRepository.class);
        ApplicationArguments args = Mockito.mock(ApplicationArguments.class);

        // Create instance of the class to test
        ApplicationLoader loader = new ApplicationLoader(roleRepository, permissionRepository);

        // Mock the behavior of the findByType method to return a role
        when(roleRepository.findByType(anyString())).thenReturn(Optional.of(new Role()));

        // Call the run method
        loader.run(args);

        // Verify that the save method was not called
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    public void testRunWhenPermissionExists() {
        // Create mocks
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        PermissionRepository permissionRepository = Mockito.mock(PermissionRepository.class);
        ApplicationArguments args = Mockito.mock(ApplicationArguments.class);

        // Create instance of the class to test
        ApplicationLoader loader = new ApplicationLoader(roleRepository, permissionRepository);

        // Mock the behavior of the findByType method to return a permission
        when(permissionRepository.findByType(anyString())).thenReturn(Optional.of(new Permission()));

        // Call the run method
        loader.run(args);

        // Verify that the save method was called the expected number of times
        verify(permissionRepository, times(4)).save(any(Permission.class));
    }
}