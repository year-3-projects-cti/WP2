package com.example.crm.repositories;

import com.example.crm.models.OneTimeCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OneTimeCourseRepository extends JpaRepository<OneTimeCourse, Long> {
    List<OneTimeCourse> findByStartDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
