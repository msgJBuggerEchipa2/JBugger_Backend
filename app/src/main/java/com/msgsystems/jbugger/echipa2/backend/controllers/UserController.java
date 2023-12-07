package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


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
        Pattern pattern_email = Pattern.compile("^[a-zA-Z0-9._%+-]+@msggroup\\.com$");


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

    @PostMapping("/users/add")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        try{
            if(validateUser(user)) {
                //Generate username
                String generatedUsername = generateUsername(user);
                user.setUsername(generatedUsername);
                // Save the user to the database
                repository.save(user);
                // Send welcome notification
                sendWelcomeNotification(user);
                return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
            } else {
                // Return a more detailed error message
                return new ResponseEntity<>("Invalid user data", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private void sendWelcomeNotification(User user) {
        try {
            String userEmail = user.getEmail();
            logger.info("Sending welcome notification to: {}", userEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/users/edit/{id}")
    public ResponseEntity<String> editUser(@PathVariable int id, @Valid @RequestBody User updatedUser) {
        try {
            Optional<User> optionalUser = repository.findById((long)id);
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();

                // Check if the username is being modified, which is not allowed
                if (!existingUser.getUsername().equals(updatedUser.getUsername())) {
                    return new ResponseEntity<>("Username cannot be modified", HttpStatus.BAD_REQUEST);
                }

                // Set the properties of the existing user based on the updated user
                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                existingUser.setMobileNumber(updatedUser.getMobileNumber());
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setPassword(updatedUser.getPassword());
                existingUser.setStatus(updatedUser.getStatus());

                // Perform validations
                if (validateUser(existingUser)) {
                    // Save the updated user to the database
                    repository.save(existingUser);

                    // Send USER_UPDATED notification
                    sendUserUpdatedNotification(existingUser);

                    return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Invalid user data", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(optionalUser.toString(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendUserUpdatedNotification(User user) {
        try {
            String userEmail = user.getEmail();
            logger.info("Sending USER_UPDATED notification to: {}", userEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/printUsers")
    public ResponseEntity<List<User>> printUsers() {
        List<User> users = repository.findAll();
        users.forEach(user -> {
            // Log or print the user data
            System.out.println(user.toString());
        });
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            Optional<User> optionalUser = repository.findById((long)id);

            if (optionalUser.isPresent()) {
                User userToDelete = optionalUser.get();

                //TODO check if user has unfinished tasks
                if (hasUnfinishedTasks(userToDelete)) {
                    return new ResponseEntity<>("User has unfinished tasks and cannot be deleted", HttpStatus.BAD_REQUEST);
                }

                // set the status to INACTIVE
                userToDelete.deactivateUser();


                repository.save(userToDelete);

                // send notification USER_DELETED
                sendUserDeletedNotification(userToDelete);

                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean hasUnfinishedTasks(User user) {
        //TODO
        return false;
    }

    private void sendUserDeletedNotification(User user) {
        try {
            String userEmail = user.getEmail();
            logger.info("Sending USER_DELETED notification to: {}", userEmail);
            // TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
