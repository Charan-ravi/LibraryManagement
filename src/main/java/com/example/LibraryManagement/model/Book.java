package com.example.LibraryManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is required")
    @Size(max = 255,message = "Title cannot exceed 255 characters")
    private String title;
    @NotBlank(message = "Author is required")
    @Size(max = 255,message = "Author cannot exceed 255 characters")
    private String author;
    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 20,message = "ISBN must be between 10 and 20 characters")
    @Column(unique = true)
    private String isbn;
    private Integer publicationYear;
    @Min(value = 0,message = "Quantity cannot be negative")
    private Integer quantity;
    @Min(value = 0,message = "Available cannot be negative")
    private Integer available;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User borrowedBy;

    public boolean isBorrowed() {
        return borrowedBy != null;
    }

}
