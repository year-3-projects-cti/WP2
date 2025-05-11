package com.example.crm.services;

import com.example.crm.models.RecurrentCourse;
import com.example.crm.repositories.RecurrentCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecurrentCourseService {

    private final RecurrentCourseRepository recurrentCourseRepository;

    public RecurrentCourseService(RecurrentCourseRepository recurrentCourseRepository) {
        this.recurrentCourseRepository = recurrentCourseRepository;
    }

    public List<RecurrentCourse> findAll() {
        return recurrentCourseRepository.findAll();
    }

    public RecurrentCourse save(RecurrentCourse course) {
        return recurrentCourseRepository.save(course);
    }

    public void delete(Long id) {
        recurrentCourseRepository.deleteById(id);
    }
}
