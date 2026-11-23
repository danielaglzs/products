package mx.edu.utez.products.service;

import mx.edu.utez.products.exception.ProductOutOfStock;
import mx.edu.utez.products.exception.ResourceNotFoundException;
import mx.edu.utez.products.model.Category;
import mx.edu.utez.products.model.Product;
import mx.edu.utez.products.repository.CategoryRepository;
import mx.edu.utez.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repo;

    private final CategoryRepository repoCategory;

    public ProductService(ProductRepository repo, CategoryRepository repoCategory){
        this.repo=repo;
        this.repoCategory = repoCategory;
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Product findByIdService(Long id) {

        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontro el producto con id: " + id)
        );
    }

    public Product saveService(Product newProduct, Long id) {
        Category category = repoCategory.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontro esa categoria con id: " + id)
        );
        newProduct.setCategory(category);

        return repo.save(newProduct);
    }

    public Product updateService(Long id, Product productUpdated, Long idCategory) {
        Product product = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontro el producto con id: " + id)
        );

        Category category = repoCategory.findById(idCategory).orElseThrow(
                () -> new ResourceNotFoundException("No se encontro la categoria con id: " + idCategory)
        );

        product.setName(productUpdated.getName());
        product.setStock(productUpdated.getStock());
        product.setPrice(product.getPrice());
        product.setCategory(category);

        return repo.save(product);

    }

    public boolean deleteService(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }

        repo.deleteById(id);
        return true;
    }

    public Product applyDiscount(Long id, Double percentage) {

        Product product = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontro el producto con id: " + id)
        );

        if (product.getStock() == 0) {
            throw new ProductOutOfStock("No hay Stock");
        }

        double percen = percentage / 100;
        double newPrice = product.getPrice() - (percen * product.getPrice());

        product.setPrice(newPrice);

        return repo.save(product);

    }

}
