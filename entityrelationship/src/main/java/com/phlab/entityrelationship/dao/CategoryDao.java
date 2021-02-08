package com.phlab.entityrelationship.dao;

import com.phlab.entityrelationship.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/*
 * Author: phlab
 * Date: 03/02/21
 */
public interface CategoryDao extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.name = ?1")
    Optional<Category> findByName(String categoryName);

}
