package com.example.crm.controllers;

import com.example.crm.models.Teacher;
import com.example.crm.services.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {

  private final TeacherService teacherService;

  public TeacherController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @GetMapping
  public List<Teacher> getAllTeachers() {
    return teacherService.getAllTeachers();
  }

  @PostMapping
  public Teacher addTeacher(@RequestBody Teacher teacher) {
    return teacherService.addTeacher(teacher);
  }

  @PutMapping("/{id}")
  public Teacher updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
    return teacherService.updateTeacher(id, teacherDetails);
  }

  @DeleteMapping("/{id}")
  public void deleteTeacher(@PathVariable Long id) {
    teacherService.deleteTeacher(id);
  }
}
