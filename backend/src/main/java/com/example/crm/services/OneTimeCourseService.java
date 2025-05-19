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
        return repository.findAll();
    }

    public OneTimeCourse update(Long id, OneTimeCourse updatedCourse) {
        return repository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setName(updatedCourse.getName());
                    existingCourse.setStartDateTime(updatedCourse.getStartDateTime());
                    existingCourse.setClassroom(updatedCourse.getClassroom());
                    existingCourse.setImageUrl(updatedCourse.getImageUrl());
                    return repository.save(existingCourse);
                })
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }
}
