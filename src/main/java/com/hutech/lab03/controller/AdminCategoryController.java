package com.hutech.lab03.controller;

import com.hutech.lab03.model.Category;
import com.hutech.lab03.repository.CategoryRepository;
import com.hutech.lab03.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();

        // FIX: Đếm số course theo category_id bằng query thay vì
        // gọi category.courses.size() (gây LazyInitializationException)
        Map<Long, Long> courseCounts = courseRepository.findAll()
                .stream()
                .filter(c -> c.getCategory() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getCategory().getId(),
                        Collectors.counting()));

        model.addAttribute("categories", categories);
        model.addAttribute("courseCounts", courseCounts);
        return "admin/category-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("category") Category category,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "admin/category-form";
        }
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }
}