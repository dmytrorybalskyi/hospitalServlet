package org.example.service;

import org.example.model.dao.CategoryDAO;
import org.example.model.dao.DAOFactory;
import org.example.model.entity.Category;

import java.util.List;

public class CategoryService {
    private DAOFactory daoFactory = DAOFactory.getInstance();

    public List<Category> finAll(){
        CategoryDAO categoryDAO = daoFactory.createCategoryDAO();
        return categoryDAO.findAll();
    }

    public List<Category> finAllWithoutNurse(){
        CategoryDAO categoryDAO = daoFactory.createCategoryDAO();
        return categoryDAO.findAllWithoutNurse();
    }
}
