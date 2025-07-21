/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

/**
 *
 * @author Aurel Noe Kenfack
 */
// interfaces/CategoryService.java

import java.util.List;
import models.Category;

public interface CategoryService {
    public Category findById(int id);
    public List<Category> findAll();
    public boolean addCategory(Category category);
    public boolean updateCategory(Category category);
    public boolean deleteCategory(int id);
    public int countAllCategories();
}

