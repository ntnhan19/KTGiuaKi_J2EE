package com.hutech.lab03.controller;

import com.hutech.lab03.model.Book;
import com.hutech.lab03.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping
    public String add(@Valid @ModelAttribute Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "add-book"; // Quay lại form nếu có lỗi
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return bookService.findById(id)
                .map(b -> {
                    model.addAttribute("book", b);
                    return "edit-book";
                })
                .orElse("redirect:/books");
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-book";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/books";
    }
}