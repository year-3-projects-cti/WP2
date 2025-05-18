package com.example.crm.services;

import com.example.crm.models.OneTimeCourse;
import com.example.crm.repositories.OneTimeCourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OneTimeCourseService {

    private final OneTimeCourseRepository repository;

    public OneTimeCourseService(OneTimeCourseRepository repository) {
        this.repository = repository;
    }

    public List<OneTimeCourse> getAll() {
        return repository.findAll();
    }

    public Optional<OneTimeCourse> getById(Long id) {
        return repository.findById(id);
    }

    public OneTimeCourse save(OneTimeCourse course) {
        return repository.save(course);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<OneTimeCourse> getCoursesBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findBetween(start, end);
    }
}
