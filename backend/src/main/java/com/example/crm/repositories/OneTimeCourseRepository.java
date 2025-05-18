package com.example.crm.repositories;

import com.example.crm.models.OneTimeCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OneTimeCourseRepository extends JpaRepository<OneTimeCourse, Long> {
    @Query("SELECT c FROM OneTimeCourse c WHERE c.startDateTime BETWEEN :start AND :end")
    List<OneTimeCourse> findBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
