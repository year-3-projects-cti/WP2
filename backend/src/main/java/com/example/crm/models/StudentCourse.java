package com.example.crm.models;

import jakarta.persistence.*;

@Entity
@Table(name = "student_courses")
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private Long courseId;

    private String courseType; // "one-time" sau "recurrent"

    public StudentCourse() {}

    public StudentCourse(Student student, Long courseId, String courseType) {
        this.student = student;
        this.courseId = courseId;
        this.courseType = courseType;
    }

    public Long getId() { return id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseType() { return courseType; }
    public void setCourseType(String courseType) { this.courseType = courseType; }
}
