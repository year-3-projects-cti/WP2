package com.example.crm.models;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String subject;
  private String status;

  public Teacher() {}

  public Teacher(String name, String email, String subject, String status) {
    this.name = name;
    this.email = email;
    this.subject = subject;
    this.status = status;
  }

  public Long getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getSubject() { return subject; }
  public void setSubject(String subject) { this.subject = subject; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}
