package mx.edu.utez.products.service;

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

    public List<Product> findAll(){
        return repo.findAll();
    }

    public Optional<Product> finById(Long id) {
        return repo.findById(id);
    }


    public Product save(Product product, Long id) {
        Category category = repoCategory.findById(id).orElseThrow();
        product.setCategory(category);
        return repo.save(product);
    }

    // Datos de entrada
    public Optional<Product> update(Long id, Product newProduct) {
        Optional<Product> optional = repo.findById(id);

        Product productBd = optional.get();

        productBd.setName(newProduct.getName());
        productBd.setPrice(newProduct.getPrice());
        productBd.setStock(newProduct.getStock());

        return Optional.of(repo.save(productBd));
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }

        repo.deleteById(id);
        return true;
    }
}
