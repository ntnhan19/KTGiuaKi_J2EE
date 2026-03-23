package com.hutech.lab03.service;

import com.hutech.lab03.model.Course;
import com.hutech.lab03.model.Enrollment;
import com.hutech.lab03.model.Student;
import com.hutech.lab03.repository.CourseRepository;
import com.hutech.lab03.repository.EnrollmentRepository;
import com.hutech.lab03.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public boolean enrollStudent(String username, Long courseId) {
        Student student = studentRepository.findByUsername(username).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (student != null && course != null) {
            // Check if already enrolled
            if (enrollmentRepository.existsByStudentAndCourseId(student, courseId)) {
                return false; // Already enrolled
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrollDate(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
            return true;
        }
        return false;
    }

    public List<Enrollment> getEnrolledCourses(String username) {
        Student student = studentRepository.findByUsername(username).orElseThrow();
        return enrollmentRepository.findByStudent(student);
    }
}
