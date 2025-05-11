package com.example.crm.services;

import com.example.crm.models.OneTimeCourse;
import com.example.crm.repositories.OneTimeCourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OneTimeCourseService {

    private final OneTimeCourseRepository oneTimeCourseRepository;

    public OneTimeCourseService(OneTimeCourseRepository oneTimeCourseRepository) {
        this.oneTimeCourseRepository = oneTimeCourseRepository;
    }

    public List<OneTimeCourse> findAll() {
        return oneTimeCourseRepository.findAll();
    }

    public List<OneTimeCourse> findBetweenDates(LocalDateTime start, LocalDateTime end) {
        return oneTimeCourseRepository.findByStartDateTimeBetween(start, end);
    }

    public OneTimeCourse save(OneTimeCourse course) {
        return oneTimeCourseRepository.save(course);
    }

    public void delete(Long id) {
        oneTimeCourseRepository.deleteById(id);
    }
}
