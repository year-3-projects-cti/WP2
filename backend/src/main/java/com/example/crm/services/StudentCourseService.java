package com.example.crm.services;

import com.example.crm.models.StudentCourse;
import com.example.crm.repositories.OneTimeCourseRepository;
import com.example.crm.repositories.RecurrentCourseRepository;
import com.example.crm.repositories.StudentCourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final OneTimeCourseRepository oneTimeCourseRepository;
    private final RecurrentCourseRepository recurrentCourseRepository;

    public StudentCourseService(StudentCourseRepository studentCourseRepository, OneTimeCourseRepository oneTimeCourseRepository, RecurrentCourseRepository recurrentCourseRepository) {
        this.studentCourseRepository = studentCourseRepository;
        this.oneTimeCourseRepository = oneTimeCourseRepository;
        this.recurrentCourseRepository = recurrentCourseRepository;
    }

    public void save(StudentCourse studentCourse) {
        studentCourseRepository.save(studentCourse);
    }

    public List<Object> findCoursesByStudent(Long studentId) {
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudentId(studentId);
        List<Object> courses = new ArrayList<>();

        for (StudentCourse sc : studentCourses) {
            if ("one-time".equalsIgnoreCase(sc.getCourseType())) {
                oneTimeCourseRepository.findById(sc.getCourseId()).ifPresent(courses::add);
            } else if ("recurrent".equalsIgnoreCase(sc.getCourseType())) {
                recurrentCourseRepository.findById(sc.getCourseId()).ifPresent(courses::add);
            }
        }

        return courses;
    }
}
