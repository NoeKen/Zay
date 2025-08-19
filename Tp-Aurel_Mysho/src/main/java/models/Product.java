package models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Entité représentant un produit dans la boutique.
 */

public class Product {

    private int id;

    private String name; // Nom du produit

    private String description; // Description du produit

    private BigDecimal price; // Prix en dollars

    private String image; // URL ou nom du fichier image

    private Integer stock; // Quantité disponible en stock

    private boolean featured; // Produit mis en avant ou non
    
    private String sizes; // Stockée brute en BDD: "S,M,L,XL"

    // Mapping simple par ID de catégorie (relation manuelle en DAO)
    private Integer categoryId;
    
    private String specifications;
    private int avgRating;      // moyenne arrondie des ratings
    private int nbrComments;    // nombre de commentaires
    private List<Review> reviews = new ArrayList<>();

    
    

    // ----------------- Constructeurs -----------------

    public Product() {
        // Constructeur sans argument requis par JPA
    }

    public Product(int id, String name, String description, BigDecimal price, String image, int stock, boolean featured, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.stock = stock;
        this.featured = featured;
        this.categoryId = categoryId;
        
    }

    // ----------------- Getters & Setters -----------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getSizesRaw() {
        return sizes;
    }
    public void setSizesRaw(String sizes) {
        this.sizes = sizes;
    }

    // Retourne la liste de tailles
    public List<String> getSizesList() {
        if (sizes == null || sizes.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(sizes.split(","));
    }

    // Définit la liste de tailles
    public void setSizesList(List<String> sizesList) {
        this.sizes = String.join(",", sizesList);
    }
    
    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getAvgRating() {
        return avgRating;
    }
    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public int getNbrComments() {
        return nbrComments;
    }
    public void setNbrComments(int nbrComments) {
        this.nbrComments = nbrComments;
    }
    
    
    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", image=" + image + ", stock=" + stock + ", featured=" + featured + ", categoryId=" + categoryId + '}';
    }
    
    
}
