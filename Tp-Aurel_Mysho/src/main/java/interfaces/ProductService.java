package interfaces;

import java.math.BigDecimal;
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

    /**
     * Recherche des produits selon plusieurs critères optionnels.
     *
     * @param categoryId ID de la catégorie (ou -1 si non filtré)
     * @param minPrice prix minimum (ou null si non filtré)
     * @param maxPrice prix maximum (ou null si non filtré)
     * @param featured true = seulement produits vedettes, false = tous
     * @return liste de produits correspondant aux critères
     */
    List<Product> findByCriteria(int categoryId, BigDecimal minPrice, BigDecimal maxPrice, boolean featured);

    /**
     * Récupère tous les produits mis en avant (featured).
     *
     * @return Liste des produits vedettes
     */
    List<Product> findFeaturedProducts();

    public List<Product> getProductsByCategory(int categoryId, int start, int recordsPerPage);

    public int countProductsByCategory(int categoryId);
}
