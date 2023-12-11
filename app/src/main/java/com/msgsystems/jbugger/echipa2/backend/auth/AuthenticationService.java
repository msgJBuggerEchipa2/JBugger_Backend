package com.msgsystems.jbugger.echipa2.backend.auth;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setEmail("my@email.com");

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        System.out.println("Authint = ");
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        System.out.println("Auth result = "+auth.toString());

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }

    public void assertPermission(Authentication auth, String permissionType) throws NotPermittedException {
        var user = userRepository.findByUsername(auth.getName());
        if(user.isEmpty())
            throw new NullPointerException("assertPermission(): User not found");
        if(!user.get().hasPermission(new Permission(permissionType)))
            throw new NotPermittedException("User doesn't have the required permission to execute this action");
    }


}