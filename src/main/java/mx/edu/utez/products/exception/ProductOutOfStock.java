package mx.edu.utez.products.exception;

public class ProductOutOfStock extends RuntimeException {
    public ProductOutOfStock(String message) {
        super(message);
    }
}
