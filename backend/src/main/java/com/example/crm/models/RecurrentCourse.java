package com.example.crm.models;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "recurrent_courses")
public class RecurrentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer teacher; // ID-ul profesorului

    private String classroom;

    private String imageUrl;

    private DayOfWeek dayOfWeek; // Ziua recurentei (LUNI, MARTI, etc.)

    private LocalTime startTime; // Ora de început

    private String status; // active / cancelled

    public RecurrentCourse() {}

    public RecurrentCourse(String name, Integer teacher, String classroom, String imageUrl, DayOfWeek dayOfWeek, LocalTime startTime, String status) {
        this.name = name;
        this.teacher = teacher;
        this.classroom = classroom;
        this.imageUrl = imageUrl;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
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

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }

    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public LocalTime getStartTime() { return startTime; }

    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
