package org.example.model.dao;

import org.example.model.entity.Category;

import java.util.List;

public interface CategoryDAO extends GenericDAO<Category>{
    List<Category> findAllWithoutNurse();
    List<Category> findAll();
}
