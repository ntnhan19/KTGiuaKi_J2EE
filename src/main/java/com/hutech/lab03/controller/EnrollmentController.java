package com.hutech.lab03.controller;

import com.hutech.lab03.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll/{courseId}")
    public String enrollCourse(@PathVariable Long courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        boolean success = enrollmentService.enrollStudent(username, courseId);
        
        if (success) {
            return "redirect:/home?enrollSuccess";
        } else {
            return "redirect:/home?enrollError";
        }
    }

    @GetMapping("/my-courses")
    public String myCourses(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        model.addAttribute("enrollments", enrollmentService.getEnrolledCourses(username));
        return "student/my-courses";
    }
}
