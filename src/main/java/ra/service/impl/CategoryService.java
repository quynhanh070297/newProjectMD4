package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ra.model.entity.Category;
import ra.repository.CategoryRepository;


@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCatalog(Category category, Long id) {
        if (id != null) {
            category.setCategoryId(id);
        }
        return categoryRepository.save(category);
    }

    public Category getCategory(Long id) throws Exception
    {
        return categoryRepository.findById(id).orElseThrow(() -> new Exception("Category not exits"));
    }

    public Page<Category> getCatalogsList(int page) {
        int size = 3;
        Sort sort = Sort.by(Sort.Direction.ASC, "categoryName");
        Pageable pageable = PageRequest.of(page, size, sort);
        return categoryRepository.findAll(pageable);
    }

    public Boolean deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new Exception("Category not exits"));
        categoryRepository.delete(category);
        return true;
    }
}
