package com.phlab.entityrelationship.service;

import com.phlab.entityrelationship.dao.BookDao;
import com.phlab.entityrelationship.dao.CategoryDao;
import com.phlab.entityrelationship.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/*
 * Author: phlab
 * Date: 03/02/21
 */
@Service
public class BookService {

    private final BookDao bookDao;
    private final CategoryDao categoryDao;

    @Autowired
    public BookService(BookDao bookDao, CategoryDao categoryDao) {
        this.bookDao = bookDao;
        this.categoryDao = categoryDao;
    }

    public void addBook(Book book) {

        validateBook(book);
        bookDao.save(book);
    }

    public Iterable<Book> findAll() {
        return bookDao.findAll();
    }

    public Book findById(Long id) {
        return bookDao.findById(id).orElse(null);
    }

    public Book updateBook(Long id, Book book) {

        validateBook(book);

        Book toUpdate = bookDao.getOne(id);
        if (toUpdate.getIdBook() > 0) {
            toUpdate.setTitle(book.getTitle());
            toUpdate.setCategory(book.getCategory());
            return bookDao.save(toUpdate);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found");
        }

    }

    public void validateBook(Book book){
        if (book.getCategory() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is obligatory");
        }

        if (book.getTitle().trim().length() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is obligatory");
        }

        if (categoryDao.findById(book.getCategory().getIdCategory()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }

    }
}
