package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.controllers.TestController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestControllerTest {

    @Test
    public void testHelloUser() {
        // Create instance of the controller to test
        TestController testController = new TestController();

        // Test the helloUser method
        String response = testController.helloUser();

        // Verify the result
        assertEquals("Hello User", response);
    }

    @Test
    public void testHelloAdmin() {
        // Create instance of the controller to test
        TestController testController = new TestController();

        // Test the helloAdmin method
        String response = testController.helloAdmin();

        // Verify the result
        assertEquals("Hello Admin", response);
    }
}