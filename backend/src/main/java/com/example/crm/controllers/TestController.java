package com.example.crm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Marchează această clasă ca fiind un Controller
@RequestMapping("/api")  // Prefixează toate request-urile cu /api
public class TestController {

    @GetMapping("/test")  // Când cineva accesează /api/test, se execută metoda test()
    public String test() {
        return "Backend is working!";
    }
}
