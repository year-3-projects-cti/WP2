package com.example.crm.repositories;

import com.example.crm.models.RecurrentCourse;
import scala.collection.immutable.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurrentCourseRepository extends JpaRepository<RecurrentCourse, Long> {
    List<RecurrentCourse> findByDayOfWeekBetween(int startDay, int endDay);
}
