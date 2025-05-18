package com.example.crm.services;

import com.example.crm.models.RecurrentCourse;
import com.example.crm.repositories.RecurrentCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecurrentCourseService {

    private final RecurrentCourseRepository repository;

    public RecurrentCourseService(RecurrentCourseRepository repository) {
        this.repository = repository;
    }

    public List<RecurrentCourse> getAll() {
        return repository.findAll();
    }

    public Optional<RecurrentCourse> getById(Long id) {
        return repository.findById(id);
    }

    public RecurrentCourse save(RecurrentCourse course) {
        return repository.save(course);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<RecurrentCourse> getCoursesBetween(String start, String end) {
        return repository.findAll();
    }
}
