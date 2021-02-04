package com.phlab.entityrelationship.dao;

import com.phlab.entityrelationship.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * Author: phlab
 * Date: 03/02/21
 */
public interface BookDao extends JpaRepository<Book, Long> {

    //List<Book> findAllByIdCategory(Long id);
}
