package com.example.crm.controllers;

import com.example.crm.models.OneTimeCourse;
import com.example.crm.services.OneTimeCourseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/one-time-courses")
@CrossOrigin(origins = "*")
public class OneTimeCourseController {

    private final OneTimeCourseService service;

    public OneTimeCourseController(OneTimeCourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<OneTimeCourse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<OneTimeCourse> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public OneTimeCourse save(@RequestBody OneTimeCourse course) {
        return service.save(course);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/between")
    public List<OneTimeCourse> getCoursesBetween(@RequestParam String start, @RequestParam String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
        return service.getCoursesBetween(startDateTime, endDateTime);
    }
    

}
