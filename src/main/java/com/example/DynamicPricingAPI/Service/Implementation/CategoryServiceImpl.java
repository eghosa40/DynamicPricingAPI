package com.example.DynamicPricingAPI.Service.Implementation;

import com.example.DynamicPricingAPI.Repository.CategoryRepository;
import com.example.DynamicPricingAPI.Service.CategoryService;
import com.example.DynamicPricingAPI.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String name, String description) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Category already exists: " + name);
        }

        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

}
