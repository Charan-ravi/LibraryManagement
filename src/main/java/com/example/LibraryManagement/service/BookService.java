package com.example.LibraryManagement.service;

import com.example.LibraryManagement.model.Book;
import com.example.LibraryManagement.model.User;
import com.example.LibraryManagement.repository.BookRepository;
import com.example.LibraryManagement.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    private UserRepository userRepository;

    public Book addBook(@Valid Book book){
        if(bookRepository.findByIsbn(book.getIsbn())!=null){
            throw new IllegalArgumentException("Book with ISBN "+ book.getIsbn() + " already exists");
        }
        book.setAvailable(book.getQuantity());
        return bookRepository.save(book);
    }
    public Book getBookById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Book not found with id : "+id));
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
