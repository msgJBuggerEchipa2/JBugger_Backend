package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.RoleRepository;
import com.msgsystems.jbugger.echipa2.backend.repository.UserRepository;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationResult;
import com.msgsystems.jbugger.echipa2.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Test
    public void testFindByUsername() {
        // Create mocks
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        // Create instance of the service to test
        UserService userService = new UserService(userRepository, null);

        // Mock the behavior of the findByUsername method
        User user = new User();
        user.setUsername("username");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        // Test the findByUsername method
        ServiceOperationResult<User> result = userService.findByUsername("username");

        // Verify the result
        assertEquals("username", result.getValue().getUsername());
    }

    @Test
    public void testAddRoleToUser() {
        // Create mocks
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        // Create instance of the service to test
        UserService userService = new UserService(userRepository, null);

        // Mock the behavior of the save method
        User user = new User();
        user.setUsername("username");
        Role role = new Role();
        role.setId(1);
        user.setRoles(Set.of(role));
        when(userRepository.save(user)).thenReturn(user);

        // Test the addRoleToUser method
        ServiceOperationResult<User> result = userService.addRoleToUser(user, role);

        // Verify the result
        assertEquals("username", result.getValue().getUsername());
        assertEquals(1, result.getValue().getRoles().size());
    }
}