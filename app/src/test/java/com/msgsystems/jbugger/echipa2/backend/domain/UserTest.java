package com.msgsystems.jbugger.echipa2.backend.domain;

import com.msgsystems.jbugger.echipa2.backend.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class UserTest {

    @Test
    void testUser() {
        User user = new User("John", "Doe", "123-456-7890", "<EMAIL>");
        Assertions.assertEquals("John", user.getFirstName());
        Assertions.assertEquals("Doe", user.getLastName());
        Assertions.assertEquals("123-456-7890", user.getMobileNumber());
        Assertions.assertEquals("<EMAIL>", user.getEmail());
        Assertions.assertEquals(UserStatus.ACTIVE, user.getStatus());
        Assertions.assertNotNull(user.getRoles());
        Assertions.assertTrue(user.getRoles().isEmpty());
    }

    @Test
    void testSetters() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setMobileNumber("123-456-7890");
        user.setEmail("<EMAIL>");
        Assertions.assertEquals("John", user.getFirstName());
        Assertions.assertEquals("Doe", user.getLastName());
        Assertions.assertEquals("123-456-7890", user.getMobileNumber());
        Assertions.assertEquals("<EMAIL>", user.getEmail());
    }

    @Test
    void testAddRole() {
        Role role = new Role("ADMIN");
        User user = new User();
        user.addRole(role);
        Assertions.assertTrue(user.getRoles().contains(role));
    }

    @Test
    void testHasRole() {
        Role role = new Role("ADMIN");
        User user = new User();
        user.addRole(role);
        Assertions.assertTrue(user.hasRole(role));
    }

    @Test
    void testHasPermission() {
        Permission permission = new Permission("READ");
        Role role = new Role("ADMIN");
        role.addPermission(permission);
        User user = new User();
        user.addRole(role);
        Assertions.assertTrue(user.hasPermission(permission));
    }

    @Test
    void testDeactivateUser() {
        User user = new User();
        user.deactivateUser();
        Assertions.assertEquals(UserStatus.INACTIVE, user.getStatus());
    }

}
// Compare this snippet from JBugger_Backend-main/app/src/test/java/com/msgsystems/jbugger/echipa2/backend/domain/UserTest.java: