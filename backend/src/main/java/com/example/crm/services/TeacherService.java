package com.example.crm.services;

import com.example.crm.models.Teacher;
import com.example.crm.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

  private final TeacherRepository teacherRepository;

  public TeacherService(TeacherRepository teacherRepository) {
    this.teacherRepository = teacherRepository;
  }

  public List<Teacher> getAllTeachers() {
    return teacherRepository.findAll();
  }

  public Optional<Teacher> getTeacherById(Long id) {
    return teacherRepository.findById(id);
  }

  public Teacher addTeacher(Teacher teacher) {
    return teacherRepository.save(teacher);
  }

  public Teacher updateTeacher(Long id, Teacher teacherDetails) {
    return teacherRepository.findById(id)
            .map(teacher -> {
              teacher.setName(teacherDetails.getName());
              teacher.setEmail(teacherDetails.getEmail());
              teacher.setSubject(teacherDetails.getSubject());
              teacher.setStatus(teacherDetails.getStatus());
              return teacherRepository.save(teacher);
            })
            .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
  }

  public void deleteTeacher(Long id) {
    teacherRepository.deleteById(id);
  }
}
