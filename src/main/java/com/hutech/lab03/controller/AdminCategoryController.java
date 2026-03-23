package com.hutech.lab03.controller;

import com.hutech.lab03.model.Category;
import com.hutech.lab03.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
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
