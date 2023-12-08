package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.auth.AuthenticationService;
import com.msgsystems.jbugger.echipa2.backend.auth.JwtService;
import com.msgsystems.jbugger.echipa2.backend.auth.LoginUserDto;
import com.msgsystems.jbugger.echipa2.backend.auth.RegisterUserDto;
import com.msgsystems.jbugger.echipa2.backend.model.MockUser;
import com.msgsystems.jbugger.echipa2.backend.model.response.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MockUser> register(@RequestBody RegisterUserDto registerUserDto) {
        MockUser registeredUser = authenticationService.signup(registerUserDto);
        System.out.println("HerE????????????????????????//");
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        System.out.println("LOGGIN????????????");
        System.out.println(loginUserDto);
        MockUser authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(loginUserDto.getUsername(), jwtToken);
                //.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
