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

    public RecurrentCourse update(Long id, RecurrentCourse updatedCourse) {
        return repository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setName(updatedCourse.getName());
                    existingCourse.setDayOfWeek(updatedCourse.getDayOfWeek());
                    existingCourse.setClassroom(updatedCourse.getClassroom());
                    existingCourse.setImageUrl(updatedCourse.getImageUrl());
                    return repository.save(existingCourse);
                })
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }
}
