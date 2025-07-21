package interfaces;


import models.Product;
import java.util.List;

/**
 * Interface définissant les opérations disponibles pour gérer les produits.
 * Permet d'avoir une implémentation DAO ou JPA interchangeable.
 * 
 * @author Aurel Noe Kenfack
 */
public interface ProductService {
    public Product findById(int id);
    public List<Product> findAll();
    public boolean addProduct(Product product);
    public boolean updateProduct(Product product);
    public boolean deleteProduct(int id);
    public int countAllProducts();
    public List<Product> getProducts(int offset, int limit);
}

