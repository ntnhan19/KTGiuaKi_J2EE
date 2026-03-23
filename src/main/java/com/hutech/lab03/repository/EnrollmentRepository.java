package com.hutech.lab03.repository;

import com.hutech.lab03.model.Enrollment;
import com.hutech.lab03.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // FIX: JOIN FETCH để load course + category cùng lúc, tránh
    // LazyInitializationException
    @Query("SELECT e FROM Enrollment e JOIN FETCH e.course c LEFT JOIN FETCH c.category WHERE e.student = :student")
    List<Enrollment> findByStudentWithCourse(@Param("student") Student student);

    boolean existsByStudentAndCourseId(Student student, Long courseId);
}