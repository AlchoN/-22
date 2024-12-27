package com.example.person.controller;

import com.example.person.model.User;
import com.example.person.repository.PersonRepository;
import com.example.person.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/user")
    public List<User> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getPersonById(@PathVariable Long id) {
        Optional<User> person = personRepository.findById(id);
        return person.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public User addPerson(@RequestBody User person) {
        return personRepository.save(person);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updatePerson(@PathVariable Long id, @RequestBody User person) {
        Optional<User> existingPerson = personRepository.findById(id);
        if (existingPerson.isPresent()) {
            person.setId(id);
            return ResponseEntity.ok(personRepository.save(person));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}/weather")
    public ResponseEntity<?> getWeatherForPerson(@PathVariable Long id) {
        Optional<User> person = personRepository.findById(id);
        if (person.isPresent()) {
            return weatherService.getWeather(person.get().getLocation());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
