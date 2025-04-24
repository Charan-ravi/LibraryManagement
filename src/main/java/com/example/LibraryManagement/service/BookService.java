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
    public List<Book> searchBook(String query){
        List<Book> byTitle = bookRepository.findByTitleContainingIgnoreCase(query);
        List<Book> byAuthor = bookRepository.findByAuthorContainingIgnoreCase(query);
        byTitle.addAll(byAuthor);
        return byTitle.stream().distinct().toList();
    }
    public Book checkoutBook(Long id){
        Book book = getBookById(id);
        if(book.getAvailable() > 0 ){
            book.setAvailable(book.getAvailable() - 1);
            return bookRepository.save(book);
        } else {
            throw new IllegalStateException("No copies of book with id "+ id + " are available");
        }
    }
    public Book returnBook(Long id){
        Book book = getBookById(id);
        if (book.getAvailable() < book.getAvailable()){
            book.setAvailable(book.getAvailable() + 1);
            return bookRepository.save(book);
        } else {
            throw new IllegalStateException("All copies of book with id "+ id + " are already available");
        }
    }

    public String borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getBorrowedBy() != null) {
            return "Book already borrowed by another user.";
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        book.setBorrowedBy(user);
        bookRepository.save(book);

        return "Book borrowed successfully.";
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
