package com.hutech.lab03.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên học phần không được để trống")
    @Column(nullable = false)
    private String name;

    private String image;

    @Min(value = 1, message = "Số tín chỉ phải lớn hơn 0")
    @Column(nullable = false)
    private int credits;

    @NotBlank(message = "Giảng viên không được để trống")
    @Column(nullable = false)
    private String lecturer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments = new HashSet<>();
}
