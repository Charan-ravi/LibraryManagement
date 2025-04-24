package com.example.LibraryManagement.repository;

import com.example.LibraryManagement.model.Book;
import com.example.LibraryManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository  extends JpaRepository<Book,Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    Book findByIsbn(String isbn);
    List<Book> findByBorrowedBy(User user);
}
