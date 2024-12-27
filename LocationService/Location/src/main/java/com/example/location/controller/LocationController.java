package com.example.location.controller;


import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @GetMapping(params = "name")
    public Location getLocationByName(@RequestParam String name) {
        return locationRepository.findByName(name).orElse(null);
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }

    @PutMapping(params = "name")
    public Location updateLocation(@RequestParam String name, @RequestBody Location updatedLocation) {
        Optional<Location> locationOptional = locationRepository.findByName(name);
        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();
            location.setLatitude(updatedLocation.getLatitude());
            location.setLongitude(updatedLocation.getLongitude());
            location.setName(updatedLocation.getName());
            return locationRepository.save(location);
        } else {
            return null;
        }
    }


    @DeleteMapping(params = "name")
    public void deleteLocation(@RequestParam String name) {
        locationRepository.deleteByName(name);
    }

    @GetMapping("/weather")
    public Weather getWeatherForLocation(@RequestParam String name) {
        Optional<Location> locationOptional = locationRepository.findByName(name);
        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "6c260e98b561e4b0412baf1487de4fed";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Weather> response = restTemplate.getForEntity(apiUrl, Weather.class);
            return response.getBody();

        } else {
            return null;
        }
    }
}