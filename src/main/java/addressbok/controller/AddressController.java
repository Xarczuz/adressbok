package addressbok.controller;

import addressbok.model.Person;
import addressbok.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("address")
public class AddressController {
    private final PersonService personService;

    public AddressController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("")
    public ResponseEntity<List<Person>> getAllPerson() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.getAllPerson());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.getPerson(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(person));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.updatePerson(person));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable("id") int id) {
        try {
            personService.deletePerson(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
