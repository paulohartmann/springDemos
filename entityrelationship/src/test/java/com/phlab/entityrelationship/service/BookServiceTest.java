package com.phlab.entityrelationship.service;

import com.phlab.entityrelationship.dao.BookDao;
import com.phlab.entityrelationship.dao.CategoryDao;
import com.phlab.entityrelationship.entity.Book;
import com.phlab.entityrelationship.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/*
 * Author: phlab
 * Date: 04/02/21
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookDao bookDao;
    @Mock
    private CategoryDao categoryDao;
    @Captor
    ArgumentCaptor<Book> bookArgumentCaptor;

    private BookService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new BookService(bookDao, categoryDao);
    }

    @Test
    void shouldSaveBook() {
        //Given
        Category category = new Category(1l, "Category");

        Long id = 1l;
        Book book = new Book("Teste");
        book.setIdBook(id);
        book.setCategory(category);


        //When
        given(categoryDao.findById(book.getCategory().getIdCategory())).willReturn(Optional.of(category));

        underTest.addBook(book);

        //Then
        then(bookDao).should().save(bookArgumentCaptor.capture());
        Book bookArgmentValue = bookArgumentCaptor.getValue();

        assertThat(bookArgmentValue.getIdBook()).isEqualTo(id);
        assertThat(bookArgmentValue.getTitle()).isEqualTo("Teste");

    }

    @Test
    void shouldNotValidateWhenCategoryNotExist() {
        //Given
        Category category = new Category(1l, "Category");
        Long id = 1l;
        Book book = new Book("Test");
        book.setIdBook(id);
        book.setCategory(category);

        //given
        given(categoryDao.findById(book.getCategory().getIdCategory())).willReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> underTest.validateBook(book))
                .hasMessageContaining("Category not found")
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void shouldNotvalidateWhenCategoryNull() {
        //Given
        Long id = 1l;
        Book book = new Book("Test");
        book.setIdBook(id);
        book.setCategory(null);

        //Then
        assertThatThrownBy(() -> underTest.validateBook(book))
                .hasMessageContaining("Category is obligatory")
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void shouldNotValidateBookWhenNomeBlank() {
        //Given
        Category category = new Category(1l, "Category");

        Long id = 1l;
        Book book = new Book(" ");
        book.setIdBook(id);
        book.setCategory(category);

        //Then
        assertThatThrownBy(() -> underTest.validateBook(book))
                .hasMessageContaining("Title is obligatory")
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    void shouldUpdateBook() {
        //Given
        Category category = new Category(1l, "Category");

        Long id = 1l;
        Book book = new Book("Teste");
        book.setIdBook(id);
        book.setCategory(category);


        //When
        given(categoryDao.findById(book.getCategory().getIdCategory())).willReturn(Optional.of(category));
        given(bookDao.getOne(id)).willReturn(book);

        book.setTitle("Teste mudou");
        underTest.updateBook(id, book);

        //Then
        then(bookDao).should().save(bookArgumentCaptor.capture());
        Book bookArgmentValue = bookArgumentCaptor.getValue();

        assertThat(bookArgmentValue.getIdBook()).isEqualTo(id);
        assertThat(bookArgmentValue.getTitle()).isEqualTo("Teste mudou");

    }

    @Test
    void shouldNotUpdateWhenBookIdNotExist() {
        //Given
        Category category = new Category(1l, "Category");
        Long id = 1l;

        Book book = new Book("Test");
        book.setIdBook(0l);
        book.setCategory(category);

        //When
        given(categoryDao.findById(category.getIdCategory())).willReturn(Optional.of(category));
        given(bookDao.getOne(id)).willReturn(book);

        //Then
        assertThatThrownBy(() -> underTest.updateBook(id, book))
                .hasMessageContaining("Book not found")
                .isInstanceOf(ResponseStatusException.class);
    }
}
