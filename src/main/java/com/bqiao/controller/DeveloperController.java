package com.bqiao.controller;

import com.bqiao.domain.Developer;
import com.bqiao.service.DeveloperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * Operations for Managing Developers
 */
@CrossOrigin
@RestController
@RequestMapping("developer")
public class DeveloperController {
    private final DeveloperService service;

    public DeveloperController(
            DeveloperService service) {
        this.service = service;
    }

    @GetMapping(path = "")
    public ResponseEntity<List<Developer>> getDevelopers() {
        List<Developer> devs = service.getDevelopers();
        return new ResponseEntity<>(devs, OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Developer> getDeveloper(@PathVariable String id) {
        Developer developer = service.getDeveloper(id);
        return new ResponseEntity<>(developer, OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Developer> deleteDeveloper(@PathVariable String id) {
        service.deleteDeveloper(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable String id, @RequestBody Developer toUpdate) {
        Developer developer = service.updateDeveloper(id, toUpdate);
        return new ResponseEntity<>(developer, OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer toCreate) {
        Developer developer = service.createDeveloper(toCreate);
        return new ResponseEntity<>(developer, CREATED);
    }
}
