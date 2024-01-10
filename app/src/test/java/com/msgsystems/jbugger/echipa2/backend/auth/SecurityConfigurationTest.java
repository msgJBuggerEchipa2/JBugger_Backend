package com.msgsystems.jbugger.echipa2.backend.auth;

import com.msgsystems.jbugger.echipa2.backend.auth.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SecurityConfigurationTest {

    @Test
    public void testBeansCreation() {
        // Create mocks
        AuthenticationProvider authenticationProvider = mock(AuthenticationProvider.class);
        JwtAuthenticationFilter jwtAuthenticationFilter = mock(JwtAuthenticationFilter.class);

        // Create instance of the configuration to test
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(jwtAuthenticationFilter, authenticationProvider);

        // Verify the beans creation
        SecurityFilterChain securityFilterChain = null;
        try {
            securityFilterChain = securityConfiguration.securityFilterChain(mock(HttpSecurity.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CorsConfigurationSource corsConfigurationSource = securityConfiguration.corsConfigurationSource();

        assertNotNull(securityFilterChain);
        assertNotNull(corsConfigurationSource);
    }
}
