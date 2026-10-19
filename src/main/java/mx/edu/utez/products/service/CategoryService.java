package mx.edu.utez.products.service;

import mx.edu.utez.products.dto.CategoryRequest;
import mx.edu.utez.products.dto.CategoryResponse;
import mx.edu.utez.products.exception.ResourceNotFoundException;
import mx.edu.utez.products.model.Category;
import mx.edu.utez.products.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repoCat;

    public CategoryService(CategoryRepository repoCat) {
        this.repoCat = repoCat;
    }

    public List<CategoryResponse> findAll() {
        return repoCat.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse findById(Long idCat) {
        Category category = repoCat.findById(idCat)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "No se encontro la categoria con ID: " + idCat
                        )
                );

        return toResponse(category);
    }

    public CategoryResponse save(CategoryRequest request) {
        Category category = new Category();

        category.setName(request.name());

        Category saved = repoCat.save(category);

        return toResponse(saved);
    }

    public CategoryResponse update(Long idCat, CategoryRequest request) {
        Category category = repoCat.findById(idCat)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "No se encontro la categoria con ID: " + idCat
                        )
                );

        category.setName(request.name());

        Category updated = repoCat.save(category);

        return toResponse(updated);
    }

    public void delete(Long idCat) {
        Category category = repoCat.findById(idCat)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró la categoría con ID: " + idCat
                ));

        repoCat.delete(category);
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }


}
