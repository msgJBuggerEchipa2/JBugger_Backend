package com.msgsystems.jbugger.echipa2.backend.auth;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    private final HashMap<String, Integer> failedLoginAttempts = new HashMap<>();

    public User authenticate(LoginUserDto input) {
        System.out.println("Authint = ");
        var username = input.getUsername();
        var password = input.getPassword();

        var user = userRepository.findByUsername(username).orElseThrow();
        if(!user.isEnabled())
            throw new DeactivatedUserException();

        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            System.out.println("Auth result = "+auth.toString());
        }
        catch (AuthenticationException e) {
            failedLoginAttempts.put(
                    username, failedLoginAttempts.getOrDefault(username, 0) + 1
            );
            if(failedLoginAttempts.get(username)>=5){
                user.deactivateUser();
                userRepository.save(user);
                throw new DeactivatedUserException();
            }
            throw e;
        }
        return user;
    }

    /**
     * @param auth authentication
     * @param permissionType when permission type is null, we just want to check
     *                       user's integrity. Otherwise, the permission is checked
     *                       considering the user's current roles
     */
    public User assertPermission(Authentication auth, String permissionType) {
        var user = userRepository.findByUsername(auth.getName());
        if(user.isEmpty())
            throw new NullPointerException("assertPermission(): User not found");
        if(permissionType==null)
            return user.get();
        if(!user.get().hasPermission(new Permission(permissionType)))
            throw new NotPermittedException("User doesn't have the required permission to execute this action");
        return user.get();
    }


}