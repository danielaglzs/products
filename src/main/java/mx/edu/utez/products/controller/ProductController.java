package mx.edu.utez.products.controller;

import jakarta.validation.Valid;
import mx.edu.utez.products.dto.DiscountRequest;
import mx.edu.utez.products.dto.ProductRequest;
import mx.edu.utez.products.dto.ProductResponse;
import mx.edu.utez.products.model.Product;
import mx.edu.utez.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;

    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> list = new ArrayList<>();

        for (Product product: service.findAll()) {
            list.add(toResponse(product));
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        Product product = service.findByIdService(id);

        return ResponseEntity.ok(toResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest dto) {
        Product product = service.saveService(
                toEntity(dto),
                dto.categoryId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest dto) {
        Product product = service.updateService(
                id,
                toEntity(dto),
                dto.categoryId()
        );

        return ResponseEntity.ok(toResponse(product));
    }

    @PatchMapping("/{id}/discount")
    public ResponseEntity<ProductResponse> discount(@PathVariable Long id, @Valid @RequestBody DiscountRequest dto) {
        Product product = service.applyDiscount(
                id,
                dto.percentage()
        );

        return ResponseEntity.ok(toResponse(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long id) {
        if (service.deleteService(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock(), "", 0L);
    }

    private Product toEntity(ProductRequest product) {
        Product productEntity = new Product();
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setStock(product.stock());

        return productEntity;
    }
}
