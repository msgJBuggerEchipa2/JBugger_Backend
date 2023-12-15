package com.msgsystems.jbugger.echipa2.backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserStatusTest {

    @Test
    void activeStatusShouldBeActive() {
        UserStatus status = UserStatus.ACTIVE;
        assertEquals("ACTIVE", status.name());
    }

    @Test
    void inactiveStatusShouldBeInactive() {
        UserStatus status = UserStatus.INACTIVE;
        assertEquals("INACTIVE", status.name());
    }

    @Test
    void activeStatusShouldNotBeInactive() {
        UserStatus status = UserStatus.ACTIVE;
        assertNotEquals("INACTIVE", status.name());
    }

    @Test
    void inactiveStatusShouldNotBeActive() {
        UserStatus status = UserStatus.INACTIVE;
        assertNotEquals("ACTIVE", status.name());
    }

    @Test
    void activeStatusShouldHaveCode0() {
        UserStatus status = UserStatus.ACTIVE;
        assertEquals(0, status.ordinal());
    }

    @Test
    void inactiveStatusShouldHaveCode1() {
        UserStatus status = UserStatus.INACTIVE;
        assertEquals(1, status.ordinal());
    }

    @Test
    void activeStatusShouldHaveCodeLessThanInactive() {
        UserStatus activeStatus = UserStatus.ACTIVE;
        UserStatus inactiveStatus = UserStatus.INACTIVE;
        assertTrue(activeStatus.ordinal() < inactiveStatus.ordinal());
    }

}