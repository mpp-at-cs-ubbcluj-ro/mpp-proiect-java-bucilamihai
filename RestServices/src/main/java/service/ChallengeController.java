package service;

import domain.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.interfaces.ChallengeRepository;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/contest/challenge")
public class ChallengeController {

    private static final String template = "Hello, %s!";

    @Autowired
    private ChallengeRepository challengeRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Challenge> getAll() {
        System.out.println("Get all challenges ...");
        return (List<Challenge>) challengeRepository.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        System.out.println("Get by id " + id);
        Challenge challenge = challengeRepository.findById(id);
        if(challenge == null)
            return new ResponseEntity<String>("Challenge not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Challenge>(challenge, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Challenge create(@RequestBody Challenge challenge) {
        System.out.println("Creating challenge ...");
        challengeRepository.add(challenge);
        return challengeRepository.findByName(challenge.getName());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Challenge update(@PathVariable Long id, @RequestBody Challenge challenge) {
        System.out.println("Updating challenge ...");
        if(!Objects.equals(id, challenge.getId()))
            throw new ServiceException("Update challenge exception!");
        challengeRepository.update(challenge, challenge.getId());
        return challenge;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        System.out.println("Deleting challenge ..." + id);
        try {
            challengeRepository.delete(challengeRepository.findById(id));
            return new ResponseEntity<Challenge>(HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println("Delete challenge exception!");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String challengeError(Exception e) {
        return e.getMessage();
    }
}
