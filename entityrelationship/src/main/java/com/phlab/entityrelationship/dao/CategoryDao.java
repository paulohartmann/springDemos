package com.phlab.entityrelationship.dao;

import com.phlab.entityrelationship.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/*
 * Author: phlab
 * Date: 03/02/21
 */
public interface CategoryDao extends JpaRepository<Category, Long> {

}
