package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository repository;

    public String generateUsername(User user){
        // Generate username for a given user.
        StringBuilder result = new StringBuilder();

        String lastName = user.getLastName();
        String firstName = user.getFirstName();

        // Get first 5 letters from last name.
        // If the last name doesn't have enough letters then we put them all.
        int nrLastNameLetters = 5;
        if(lastName.length() < 5){
            nrLastNameLetters = lastName.length();
        }

        // Add first 5 characters of lastName or all lastName characters if it's length is smaller than 5.
        for(int i = 0; i < nrLastNameLetters; i++) {
            result.append(lastName.charAt(i));
        }

        int fNameIndex = 0;
        int nr = 0;

        // Add first character from first name.
        result.append(firstName.charAt(fNameIndex));
        fNameIndex ++;

        // If user is not unique, then we append numbers at the end.
        while(!isUnique(result.toString())){
            if(fNameIndex < firstName.length()) {
                result.append(firstName.charAt(fNameIndex));
                fNameIndex++;
            }
            else {
                if(result.length() > firstName.length() + nrLastNameLetters) {
                    result.replace(firstName.length() + nrLastNameLetters, result.length(), String.valueOf(nr));
                }
                else {
                    result.append(nr);
                }
                nr++;
            }
        }

        return result.toString();
    }
    public boolean isUnique(String username){
        // Check if username is unique by calling the repository method.
        Optional<User> foundUser = repository.findByUsername(username);
        return foundUser.isEmpty();
    }
    @PostMapping("/users/create")
    public void createUser(@Valid @RequestBody User user){
        // Validate given user and then generate username.
        if(validateUser(user)) {
            String generatedUsername = generateUsername(user);
            user.setUsername(generatedUsername);
            repository.save(user);
        }
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        try {
            var users = repository.findAll();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean validateUser(User user){
        // Method used to validate a user using regex.
        // Create pattern for each field to be validated by the matcher.

        Pattern pattern_name = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
        Pattern pattern_mobile = Pattern.compile("^(\\+40|\\+49)[0-9]{8}$", Pattern.CASE_INSENSITIVE);
        Pattern pattern_email = Pattern.compile("^[a-zA-Z0-9_]+@msggroup.com$");

        Matcher matcher = pattern_name.matcher(user.getFirstName());

        if(!matcher.find())
            return false;

        matcher = pattern_name.matcher(user.getLastName());

        if(!matcher.find())
            return false;


        matcher = pattern_email.matcher(user.getEmail());

        if(!matcher.find())
            return false;

        matcher = pattern_mobile.matcher(user.getMobileNumber());

        if(!matcher.find())
            return false;

        return true;

    }
}
