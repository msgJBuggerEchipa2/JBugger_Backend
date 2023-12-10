package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.auth.AuthenticationService;
import com.msgsystems.jbugger.echipa2.backend.auth.JwtService;
import com.msgsystems.jbugger.echipa2.backend.auth.LoginUserDto;
import com.msgsystems.jbugger.echipa2.backend.auth.RegisterUserDto;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.model.response.LoginResponse;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Usage
 * POST: /api/login body = { "username":"admin", "password":"admin" }
 * response = {
 *     "user": "admin",
 *     "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMjE5ODg4NCwiZXhwIjoxNzAyMjAyNDg0fQ.Dr7qGl6_bTBYaLgeinLzZ4GV3T4-iQ6yKsLjCuQd5NE"
 * }
 * GET: /api/anything
 * se pune la headers "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMjE5ODM0OSwiZXhwIjoxNzAyMjAxOTQ5fQ.nA2MywbdiUh3NrZHwhKqpAMbd_9W4zj76vpWk1D-VY0"
 * N-am mers automat cu JWT Bearer din Postman si nush ce sa-i mai fac :))
 */

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
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        System.out.println("HerE????????????????????????//");
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        System.out.println("LOGGIN????????????");
        System.out.println(loginUserDto);
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(loginUserDto.getUsername(), jwtToken);
                //.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @ExceptionHandler(value = { SignatureException.class })
    protected ResponseEntity<Object> handleException(SignatureException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
