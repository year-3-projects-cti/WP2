package com.example.crm.controllers;

import com.example.crm.models.RecurrentCourse;
import com.example.crm.services.RecurrentCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurrent-courses")
@CrossOrigin(origins = "*")
public class RecurrentCourseController {

    private final RecurrentCourseService service;

    public RecurrentCourseController(RecurrentCourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<RecurrentCourse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RecurrentCourse getById(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @PostMapping
    public RecurrentCourse save(@RequestBody RecurrentCourse course) {
        return service.save(course);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/between")
    public List<RecurrentCourse> getCoursesBetween(@RequestParam String start, @RequestParam String end) {
        return service.getCoursesBetween(start, end);
    }
}
