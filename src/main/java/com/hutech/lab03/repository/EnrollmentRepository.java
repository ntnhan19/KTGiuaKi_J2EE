package com.hutech.lab03.repository;

import com.hutech.lab03.model.Enrollment;
import com.hutech.lab03.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Student student);
    boolean existsByStudentAndCourseId(Student student, Long courseId);
}
