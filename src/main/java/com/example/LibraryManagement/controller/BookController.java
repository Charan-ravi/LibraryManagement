package com.example.LibraryManagement.controller;

import com.example.LibraryManagement.model.Book;
import com.example.LibraryManagement.service.BookService;
import com.example.LibraryManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;
    private UserService userService;

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
        Book addedBook = bookService.addBook(book);
        return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        try {
            Book book = bookService.getBookById(id);
            return new ResponseEntity<>(book,HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks ( @RequestParam String query){
        List<Book> books = bookService.searchBook(query);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
    @PostMapping("/{id}/checkout")
    public ResponseEntity<Book> checkoutBook(@PathVariable Long id){
        try {
            Book updatedBook = bookService.checkoutBook(id);
            return new ResponseEntity<>(updatedBook,HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{id}/return")
    public ResponseEntity<Book> returnBook(@PathVariable Long id){
        try {
            Book updatedBook = bookService.returnBook(id);
            return new ResponseEntity<>(updatedBook,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long bookId, @RequestParam Long userId) {
        String result = bookService.borrowBook(bookId, userId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{userId}/borrowed-books")
    public ResponseEntity<List<Book>> getBorrowedBooks(@PathVariable Long userId) {
        List<Book> books = userService.getBorrowedBooksByUserId(userId);
        return ResponseEntity.ok(books);
    }


}
