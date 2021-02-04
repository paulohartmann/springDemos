package com.phlab.entityrelationship.controller;

import com.phlab.entityrelationship.entity.Book;
import com.phlab.entityrelationship.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * Author: phlab
 * Date: 03/02/21
 */
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public void addBook(@RequestBody Book book){
        bookService.addBook(book);
    }

    @GetMapping
    public Iterable<Book> findAll(){
        return bookService.findAll();
    }

    @GetMapping(path ="{id}")
    public Book findById(@PathVariable("id") Long id){
        return bookService.findById(id);
    }

    @PutMapping(path = "{id}")
    public Book updateBook(@PathVariable("id") Long id, @RequestBody Book book){
        return bookService.updateBook(id, book);
    }
}
