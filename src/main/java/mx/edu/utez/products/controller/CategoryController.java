package mx.edu.utez.products.controller;

import jakarta.validation.Valid;
import mx.edu.utez.products.dto.CategoryRequest;
import mx.edu.utez.products.dto.CategoryResponse;
import mx.edu.utez.products.model.Category;
import mx.edu.utez.products.repository.CategoryRepository;
import mx.edu.utez.products.service.CategoryService;
import mx.edu.utez.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService serviceCat;

    public CategoryController(CategoryService serviceCat) {
        this.serviceCat = serviceCat;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(serviceCat.findAll());
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long idCategory) {
        return ResponseEntity.ok(serviceCat.findById(idCategory));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest category) {

        CategoryResponse response = serviceCat.save(category);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{idCategory}")
    public  ResponseEntity<CategoryResponse> update(@PathVariable Long idCategory, @Valid @RequestBody CategoryRequest category) {
        return ResponseEntity.ok(serviceCat.update(idCategory, category));
    }

    @DeleteMapping("/{idCategory}")
    public ResponseEntity<Void> delete (@PathVariable Long idCategory) {
        serviceCat.delete(idCategory);

        return ResponseEntity.noContent().build();
    }


}
