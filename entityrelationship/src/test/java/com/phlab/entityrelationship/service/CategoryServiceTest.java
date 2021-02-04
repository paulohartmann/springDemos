package com.phlab.entityrelationship.service;

import com.phlab.entityrelationship.dao.CategoryDao;
import com.phlab.entityrelationship.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/*
 * Author: phlab
 * Date: 04/02/21
 */
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryDao categoryDao;
    @Captor
    ArgumentCaptor<Category> categoryArgumentCaptor;

    private CategoryService underTest;

    @BeforeEach
    void setUp(){
        this.underTest = new CategoryService(categoryDao);
    }

    @Test
    void shouldUpdateCategory(){
        //Given
        Category category = new Category(1l, "New name");
        Long id = 1l;
        Category toUpdate = new Category(1l, "Test");
        //When
        given(categoryDao.getOne(id)).willReturn(toUpdate);
        underTest.updateCategory(id, category);

        //Then
        then(categoryDao).should().save(categoryArgumentCaptor.capture());
        Category categoryCaptorValue = categoryArgumentCaptor.getValue();

        assertThat(categoryCaptorValue.getIdCategory()).isEqualTo(category.getIdCategory());
        assertThat(categoryCaptorValue.getName()).isEqualTo(category.getName());

    }

    @Test
    void shouldNotUpdateCategoryWhenNameBlank(){
        //Given
        Category category = new Category(1l, " ");
        Long id = 1l;

        //Then
        assertThatThrownBy(() -> underTest.updateCategory(id, category))
                .hasMessageContaining("Category Name is obligatory")
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    void shouldNotUpdateCategoryWhenCategoryIdNotExist(){
        //Given
        Category category = new Category(0l, "Test");
        Long id = 1l;

        //When
        given(categoryDao.getOne(id)).willReturn(category);

        //Then
        assertThatThrownBy(() -> underTest.updateCategory(id, category))
                .hasMessageContaining("Category not found")
                .isInstanceOf(ResponseStatusException.class);

    }
}
