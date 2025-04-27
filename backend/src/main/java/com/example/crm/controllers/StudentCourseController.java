package com.example.crm.controllers;

import com.example.crm.models.StudentCourse;
import com.example.crm.services.StudentCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-courses")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @PostMapping
    public void addStudentCourse(@RequestBody StudentCourse studentCourse) {
        studentCourseService.save(studentCourse);
    }

    @GetMapping("/by-student/{studentId}")
    public List<Object> getCoursesByStudent(@PathVariable Long studentId) {
        return studentCourseService.findCoursesByStudent(studentId);
    }
}
