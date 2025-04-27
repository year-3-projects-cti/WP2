package com.example.crm.controllers;

import com.example.crm.models.OneTimeCourse;
import com.example.crm.models.RecurrentCourse;
import com.example.crm.services.OneTimeCourseService;
import com.example.crm.services.RecurrentCourseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final OneTimeCourseService oneTimeCourseService;
    private final RecurrentCourseService recurrentCourseService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public CourseController(OneTimeCourseService oneTimeCourseService, RecurrentCourseService recurrentCourseService) {
        this.oneTimeCourseService = oneTimeCourseService;
        this.recurrentCourseService = recurrentCourseService;
    }

    // GET - toate cursurile (one-time + recurente)
    @GetMapping
    public Map<String, Object> getAllCourses() {
        List<OneTimeCourse> oneTimeCourses = oneTimeCourseService.findAll();
        List<RecurrentCourse> recurrentCourses = recurrentCourseService.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("oneTimeCourses", oneTimeCourses);
        response.put("recurrentCourses", recurrentCourses);
        return response;
    }

    // GET - cursurile dintr-o perioadă (one-time + recurente)
    @GetMapping("/between")
    public Map<String, Object> getCoursesBetween(@RequestParam String start, @RequestParam String end) {
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        List<OneTimeCourse> oneTimeCourses = oneTimeCourseService.findBetweenDates(startDate, endDate);
        List<RecurrentCourse> recurrentCourses = recurrentCourseService.findAll(); // La recurente trebuie să decidem în frontend ce zile pică în acel interval.

        Map<String, Object> response = new HashMap<>();
        response.put("oneTimeCourses", oneTimeCourses);
        response.put("recurrentCourses", recurrentCourses);
        return response;
    }

    // POST - adaugă un OneTimeCourse
    @PostMapping("/one-time")
    public OneTimeCourse createOneTimeCourse(@RequestBody OneTimeCourse course) {
        return oneTimeCourseService.save(course);
    }

    // POST - adaugă un RecurrentCourse
    @PostMapping("/recurrent")
    public RecurrentCourse createRecurrentCourse(@RequestBody RecurrentCourse course) {
        return recurrentCourseService.save(course);
    }

    // DELETE - șterge un OneTimeCourse
    @DeleteMapping("/one-time/{id}")
    public void deleteOneTimeCourse(@PathVariable Long id) {
        oneTimeCourseService.delete(id);
    }

    // DELETE - șterge un RecurrentCourse
    @DeleteMapping("/recurrent/{id}")
    public void deleteRecurrentCourse(@PathVariable Long id) {
        recurrentCourseService.delete(id);
    }
}
