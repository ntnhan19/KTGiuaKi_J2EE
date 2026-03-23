package com.hutech.lab03.controller;

import com.hutech.lab03.model.Role;
import com.hutech.lab03.model.Student;
import com.hutech.lab03.repository.RoleRepository;
import com.hutech.lab03.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               Model model) {
        if (studentRepository.existsByUsername(username)) {
            model.addAttribute("error", "Username already exists!");
            return "auth/register";
        }
        if (studentRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email already exists!");
            return "auth/register";
        }

        Student student = new Student();
        student.setUsername(username);
        student.setEmail(email);
        student.setPassword(passwordEncoder.encode(password));

        // Default role is STUDENT
        Role userRole = roleRepository.findByName("STUDENT").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("STUDENT");
            return roleRepository.save(newRole);
        });

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        student.setRoles(roles);

        studentRepository.save(student);

        return "redirect:/login?registered";
    }
}
