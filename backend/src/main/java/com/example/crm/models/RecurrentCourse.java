package com.example.crm.models;

import jakarta.persistence.*;

@Entity
@Table(name = "recurrent_courses")
public class RecurrentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imageUrl;
    private String courseType;
    private String dayOfWeek;
    private String startHour;
    private String classroom;

    private Long teacherId;       // Referință la user_id
    private String studentIds;    // Ex: "1,2,3"

    public RecurrentCourse() {}

    // Getters și Setters

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCourseType() { return courseType; }
    public void setCourseType(String courseType) { this.courseType = courseType; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }


    public String getStartHour() { return startHour; }
    public void setStartHour(String startHour) { this.startHour = startHour; }

    public String getClassroom() { return classroom; }
    public void setClassroom(String classroom) { this.classroom = classroom; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getStudentIds() { return studentIds; }
    public void setStudentIds(String studentIds) { this.studentIds = studentIds; }
}
