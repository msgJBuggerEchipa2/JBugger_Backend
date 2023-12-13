package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.domain.Bug;
import com.msgsystems.jbugger.echipa2.backend.repository.BugRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

    @Autowired
    private BugRepository bugRepository;


    @GetMapping("/{id}")
    public ResponseEntity<?> getBugById(@PathVariable Long id) {
        Optional<Bug> bug = bugRepository.findById(id);
        return bug.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }


    @PostMapping("/create")
    public ResponseEntity<?> createBug(@RequestBody Bug bug) {
        // Add validations!
        if (bug.getTitle() == null || bug.getDescription() == null ||
                bug.getRevisionVersion() == null || bug.getSeverity() == null) {
            return ResponseEntity.badRequest().body("Missing mandatory fields");
        }

        bugRepository.save(bug);

        return new ResponseEntity<>(bug, HttpStatus.OK);
//        return new ResponseEntity<>("Bug added successfully", HttpStatus.OK);
    }


}
