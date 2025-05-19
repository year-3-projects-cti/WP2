package com.example.crm.controllers;

import com.example.crm.models.RecurrentCourse;
import com.example.crm.services.RecurrentCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<RecurrentCourse> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public RecurrentCourse save(@RequestBody RecurrentCourse course) {
        return service.save(course);
    }

    @PutMapping("/{id}")
    public RecurrentCourse update(@PathVariable Long id, @RequestBody RecurrentCourse updatedCourse) {
        return service.update(id, updatedCourse);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
