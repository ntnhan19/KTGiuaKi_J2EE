package com.hutech.lab03.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;

    @NotBlank(message = "Tên sách không được để trống")
    @Size(max = 200, message = "Tên sách không quá 200 ký tự")
    private String title;

    @NotBlank(message = "Tác giả không được để trống")
    private String author;
}