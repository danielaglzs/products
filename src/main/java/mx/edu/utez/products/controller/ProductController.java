package mx.edu.utez.products.controller;

import jakarta.validation.Valid;
import mx.edu.utez.products.dto.DiscountRequest;
import mx.edu.utez.products.dto.ProductRequest;
import mx.edu.utez.products.dto.ProductResponse;
import mx.edu.utez.products.exception.ResourceNotFoundException;
import mx.edu.utez.products.model.Product;
import mx.edu.utez.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        for(Product product : service.findAll()) {
            list.add(toResponse(product));
        }

        return ResponseEntity.ok(list);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById (@PathVariable Long id) {

        Product product = service.finById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado " + id)
        );

        return ResponseEntity.ok(toResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product) {
        // TODO: process POST request
        Product newProduct = service.save(toEntity(product), product.categoryId());

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest productUpdated) {
        // TODO: process POST request
        Product product = service.update(
                id,
                toEntity(productUpdated),
                productUpdated.categoryId()

        );

        return ResponseEntity.ok(toResponse(product));
    }

    @PatchMapping("/{id}/discount")
    public ResponseEntity<ProductResponse> updatePrice(@PathVariable Long id, @Valid @RequestBody DiscountRequest priceUpdated) {
        Product product = service.applyDiscount(
                id,
                priceUpdated.percentaje()

        );

        return ResponseEntity.ok(toResponse(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
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
