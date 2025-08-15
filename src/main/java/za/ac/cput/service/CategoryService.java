package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Category;
import za.ac.cput.repository.CategoryRepository;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    @Autowired
    private CategoryService(CategoryRepository repository){
        this.categoryRepository = repository;  }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category read(Long categoryId) {
        return this.categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long categoryId) {
        this.categoryRepository.deleteById(categoryId);
//         if (categoryRepository.existsById(categoryId)) {
//             categoryRepository.deleteById(categoryId);
//             return true;
//         }
//        return false;
    }

    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }
}
