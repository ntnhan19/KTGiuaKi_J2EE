package com.hutech.lab03.controller;

import com.hutech.lab03.model.Course;
import com.hutech.lab03.repository.CategoryRepository;
import com.hutech.lab03.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
@RequiredArgsConstructor
public class AdminCourseController {

    private final CourseService courseService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCoursesList());
        return "admin/course-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/course-form";
    }

    @PostMapping("/create")
    public String createCourse(@Valid @ModelAttribute("course") Course course,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/course-form";
        }
        courseService.saveCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id: " + id));
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/course-form";
    }

    @PostMapping("/edit/{id}")
    public String updateCourse(@PathVariable Long id,
            @Valid @ModelAttribute("course") Course course,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            course.setId(id);
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/course-form";
        }
        course.setId(id);
        courseService.saveCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses";
    }
}