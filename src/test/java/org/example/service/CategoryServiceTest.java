package org.example.service;

import org.example.model.entity.Category;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class CategoryServiceTest {
    CategoryService categoryService = new CategoryService();

    @Test
    public void FindAll(){
        Category expected = new Category(2,"dentist");
        int expectedSize = 5;
        List<Category> categoryLint = categoryService.finAll();
        Category actual = categoryLint.get(categoryLint.indexOf(expected));
        assertEquals(expectedSize,categoryLint.size());
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getName(),actual.getName());
    }

    @Test
    public void FindAllWithoutNurse(){
        Category nurse = new Category(4,"nurse");
        int expectedSize = 4;
        int expected = -1;
        List<Category> categoryList = categoryService.finAllWithoutNurse();
        assertEquals(expectedSize,categoryList.size());
        int actual = categoryList.indexOf(nurse);
        assertEquals(expected,actual);
    }


}
