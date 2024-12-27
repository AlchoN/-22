package com.example.person.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;
    private static final String API = "6c260e98b561e4b0412baf1487de4fed";

    public ResponseEntity<?> getWeather(String location) {
        return restTemplate.getForEntity(API, String.class, location);
    }
}
