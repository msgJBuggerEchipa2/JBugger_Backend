package com.msgsystems.jbugger.echipa2.backend.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class JwtAuthentificationFilterTest {

    @Test
    public void testDoFilterInternal() throws Exception {
        // Create mocks
        JwtService jwtServiceMock = Mockito.mock(JwtService.class);
        UserDetailsService userDetailsServiceMock = Mockito.mock(UserDetailsService.class);
        HandlerExceptionResolver handlerExceptionResolverMock = Mockito.mock(HandlerExceptionResolver.class);
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse responseMock = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChainMock = Mockito.mock(FilterChain.class);

        // Create instance of the filter to test
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtServiceMock, userDetailsServiceMock, handlerExceptionResolverMock);

        // Define the behavior of the mocks
        when(requestMock.getHeader("Authorization")).thenReturn("Bearer jwtToken");
        when(jwtServiceMock.extractUsername("jwtToken")).thenReturn("testUser");
        UserDetails userDetails = User.withUsername("testUser").password("password").authorities(new ArrayList<>()).build();
        when(userDetailsServiceMock.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtServiceMock.isTokenValid("jwtToken", userDetails)).thenReturn(true);

        // Use the filter to test the method
        jwtFilter.doFilterInternal(requestMock, responseMock, filterChainMock);

        // Verify the interactions
        verify(filterChainMock, times(1)).doFilter(requestMock, responseMock);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternalWithException() throws Exception {
        // Create mocks
        JwtService jwtServiceMock = Mockito.mock(JwtService.class);
        UserDetailsService userDetailsServiceMock = Mockito.mock(UserDetailsService.class);
        HandlerExceptionResolver handlerExceptionResolverMock = Mockito.mock(HandlerExceptionResolver.class);
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse responseMock = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChainMock = Mockito.mock(FilterChain.class);

        // Create instance of the filter to test
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtServiceMock, userDetailsServiceMock, handlerExceptionResolverMock);

        // Define the behavior of the mocks
        when(requestMock.getHeader("Authorization")).thenReturn("Bearer jwtToken");
        when(jwtServiceMock.extractUsername("jwtToken")).thenThrow(new RuntimeException("Test exception"));

        // Use the filter to test the method
        jwtFilter.doFilterInternal(requestMock, responseMock, filterChainMock);

        // Verify the interactions
        verify(handlerExceptionResolverMock, times(1)).resolveException(requestMock, responseMock, null, any(RuntimeException.class));
    }
}
