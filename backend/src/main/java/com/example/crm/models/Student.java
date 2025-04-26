package com.example.crm.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @JsonProperty("studentClass") // adăugat
    private String studentClass;

    private String status;

    public Student() {}

    public Student(String name, String email, String studentClass, String status) {
        this.name = name;
        this.email = email;
        this.studentClass = studentClass;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}