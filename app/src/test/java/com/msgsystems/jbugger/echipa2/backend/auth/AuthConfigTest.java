package com.msgsystems.jbugger.echipa2.backend.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.msgsystems.jbugger.echipa2.backend.auth.AuthConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthConfigTest {

    @Test
    public void testAuthConfig() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AuthConfig.class);

        AuthConfig authConfig = context.getBean(AuthConfig.class);

        UserDetailsService userDetailsService = authConfig.userDetailsService();
        Assertions.assertNotNull(userDetailsService);

        BCryptPasswordEncoder passwordEncoder = authConfig.passwordEncoder();
        Assertions.assertNotNull(passwordEncoder);

        AuthenticationManager authenticationManager = authConfig.authenticationManager(null);
        Assertions.assertNotNull(authenticationManager);

        AuthenticationProvider authenticationProvider = authConfig.authenticationProvider();
        Assertions.assertNotNull(authenticationProvider);
    }
}