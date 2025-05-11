package com.example.crm.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "one_time_courses")
public class OneTimeCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer teacher; // ID-ul profesorului

    private String classroom;

    private String imageUrl;

    private LocalDateTime startDateTime; // Data și ora de început

    private String status; // active / cancelled

    public OneTimeCourse() {}

    public OneTimeCourse(String name, Integer teacher, String classroom, String imageUrl, LocalDateTime startDateTime, String status) {
        this.name = name;
        this.teacher = teacher;
        this.classroom = classroom;
        this.imageUrl = imageUrl;
        this.startDateTime = startDateTime;
        this.status = status;
    }

    // Getters și Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getTeacher() { return teacher; }

    public void setTeacher(Integer teacher) { this.teacher = teacher; }

    public String getClassroom() { return classroom; }

    public void setClassroom(String classroom) { this.classroom = classroom; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getStartDateTime() { return startDateTime; }

    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
