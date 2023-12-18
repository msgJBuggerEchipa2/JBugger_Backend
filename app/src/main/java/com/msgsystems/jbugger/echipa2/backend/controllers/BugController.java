package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.msgsystems.jbugger.echipa2.backend.domain.Bug;
import com.msgsystems.jbugger.echipa2.backend.domain.BugSeverity;
import com.msgsystems.jbugger.echipa2.backend.domain.BugStatus;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.BugRepository;
import com.msgsystems.jbugger.echipa2.backend.utils.Graph;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BugController {

    @Autowired
    BugRepository bugRepository;

    Graph<String> bugStatesGraph;

    private static final Logger logger = Logger.getLogger("BugController");

    public BugController(){
        // State graph creation
        bugStatesGraph = new Graph<>();

        bugStatesGraph.addNode("NEW");
        bugStatesGraph.addNode("FIXED");
        bugStatesGraph.addNode("CLOSED");
        bugStatesGraph.addNode("IN_PROGRESS");
        bugStatesGraph.addNode("REJECTED");
        bugStatesGraph.addNode("INFO_NEEDED");

        bugStatesGraph.addEdge(BugStatus.NEW.toString(), BugStatus.OPEN.toString());

         bugStatesGraph.addEdge(BugStatus.FIXED.toString(), BugStatus.OPEN.toString());
        bugStatesGraph.addEdge(BugStatus.FIXED.toString(), BugStatus.CLOSED.toString());

        bugStatesGraph.addEdge(BugStatus.OPEN.toString(), BugStatus.IN_PROGRESS.toString());
        bugStatesGraph.addEdge(BugStatus.OPEN.toString(), BugStatus.REJECTED.toString());

        bugStatesGraph.addEdge(BugStatus.IN_PROGRESS.toString(), BugStatus.FIXED.toString());
        bugStatesGraph.addEdge(BugStatus.IN_PROGRESS.toString(), BugStatus.REJECTED.toString());
        bugStatesGraph.addEdge(BugStatus.IN_PROGRESS.toString(), BugStatus.INFO_NEEDED.toString());

        bugStatesGraph.addEdge(BugStatus.INFO_NEEDED.toString(), BugStatus.IN_PROGRESS.toString());

        bugStatesGraph.addEdge(BugStatus.REJECTED.toString(), BugStatus.CLOSED.toString());

        bugStatesGraph.printGraph();


    }

    boolean validateBug(Bug bug){

        Pattern pattern_version = Pattern.compile("^[a-zA-Z0-9]+.[a-zA-Z0-9]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern_version.matcher(bug.getVersion());

        if(bug.getDescription().length() < 250) { // description validation
            logger.info("Invalid description");
            return false;
        }

        if(!matcher.find()) { // version validation
            logger.info("Invalid version");
            return false;
        }

        try {
            BugSeverity.valueOf(bug.getSeverity());
        } catch (IllegalArgumentException e) {
            logger.info("Invalid severity");
            return false;
        }

        try {
            BugStatus.valueOf(bug.getStatus());
        } catch (IllegalArgumentException e) {
            logger.info("Invalid status");
            return false;
        }

        return true;

    }

    boolean canUpdateBugStatus(String oldStatus, String newStatus){
        if(bugStatesGraph.isEdge(oldStatus.toUpperCase(), newStatus.toUpperCase())) {
            return true;
        }
        return false;
    }

    @PostMapping("/bugs/add")
    public ResponseEntity<String> addBug(@Valid @RequestBody Bug bug) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // TODO -> add assigned user and attachments
        if(authentication != null) {
            try {
                if (validateBug(bug)) {
                    // Set bug creator
                    bug.setCreatedBy((User)authentication.getPrincipal());
                    // Save bug to repo
                    bugRepository.save(bug);
                    //sendWelcomeNotification(user);
                    return new ResponseEntity<>("Bug added successfully", HttpStatus.CREATED);
                } else {
                    // Return a more detailed error message
                    return new ResponseEntity<>("Invalid bug data", HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding bug", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>("Not authenticated.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/bugs/updateStatus/{id}")
    public ResponseEntity<String> updateBugStatus(@Valid @RequestBody String newStatus_json ,
                                                  @PathVariable int id) {

        Optional<Bug> optionalBug = bugRepository.findById((long)id);
        try{
            if(optionalBug.isPresent()) {
                Bug bugToChangeStatus = optionalBug.get();
                ObjectMapper mapper = new JsonMapper();
                JsonNode json = mapper.readTree(newStatus_json);

                String newStatus_string = json.get("newStatus").asText();

                if(canUpdateBugStatus(bugToChangeStatus.getStatus(), newStatus_string)) {

                    bugToChangeStatus.setStatus(newStatus_string);
                    bugRepository.save(bugToChangeStatus);
                    return new ResponseEntity<>("Bug updated successfully", HttpStatus.CREATED);
                }
                else {
                    return new ResponseEntity<>("Transition doesn't exist.", HttpStatus.BAD_REQUEST);
                }

            } else {
                return new ResponseEntity<>("Invalid bug status", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error changing bug status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bugs/editBug/{id}")
    public ResponseEntity<String> editBug(@Valid @RequestBody Bug updatedBug ,
                                                  @PathVariable int id) {

        try{
            Optional<Bug> optionalBug = bugRepository.findById((long)id);
            if(optionalBug.isPresent()) {
                Bug editedBug = optionalBug.get();
                if(validateBug(updatedBug) && canUpdateBugStatus(editedBug.getStatus(), updatedBug.getStatus())) {
                    editedBug.setTitle(updatedBug.getTitle());
                    editedBug.setDescription(editedBug.getDescription());
                    editedBug.setStatus(updatedBug.getStatus());
                    editedBug.setVersion(updatedBug.getVersion());
                    editedBug.setFixedVersion(updatedBug.getFixedVersion());
                    editedBug.setTargetDate(updatedBug.getTargetDate());
                    editedBug.setSeverity(updatedBug.getSeverity());
                    bugRepository.save(editedBug);
                    return new ResponseEntity<>("Bug updated successfully", HttpStatus.CREATED);
                }
                else {
                    return new ResponseEntity<>("Invalid bug data.", HttpStatus.BAD_REQUEST);
                }

            }
            else {
                return new ResponseEntity<>("Bug doesn't exist", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Bug doesn't exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bugs/closeBug/{id}")
    public ResponseEntity<String> closeBug(@PathVariable int id) {

        try{
            Optional<Bug> optionalBug = bugRepository.findById((long)id);
            if(optionalBug.isPresent()) {
                Bug editedBug = optionalBug.get();
               if(canUpdateBugStatus(editedBug.getStatus(), "CLOSED")) {
                   editedBug.setStatus("CLOSED");
                   bugRepository.save(editedBug);
                   return new ResponseEntity<>("Bug closed.", HttpStatus.CREATED);
               }
               else {
                   return new ResponseEntity<>("Bug cannot be closed.", HttpStatus.BAD_REQUEST);
               }
            }
            else {
                return new ResponseEntity<>("Bug doesn't exist", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Bug doesn't exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/bugs/printBugs")
    public ResponseEntity<List<Bug>> printBugs() {
        List<Bug> bugs = bugRepository.findAll();
        bugs.forEach(bug -> {
            // Log or print the user data
            System.out.println(bug.toString());
        });
        return new ResponseEntity<>(bugs, HttpStatus.OK);
    }


}
