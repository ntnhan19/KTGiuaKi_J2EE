package com.hutech.lab03.controller;

import com.hutech.lab03.model.Course;
import com.hutech.lab03.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CourseService courseService;

    @GetMapping({"/", "/home"})
    public String home(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            Model model) {
        
        // Pagination with 5 items per page
        PageRequest pageable = PageRequest.of(page, 5);
        Page<Course> coursePage;

        if (keyword != null && !keyword.isEmpty()) {
            coursePage = courseService.searchCourses(keyword, pageable);
        } else {
            coursePage = courseService.getAllCourses(pageable);
        }

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "home";
    }
}
