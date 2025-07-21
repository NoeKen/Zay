package models;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entité représentant un produit dans la boutique.
 */

@Entity // Marque cette classe comme une entité JPA
@Table(name = "products") // Lie cette classe à la table SQL "products"
public class Product {

    @Id // Déclare la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Utilise l'auto-incrément de la base
    private int id;

    @Column(nullable = false, length = 100)
    private String name; // Nom du produit

    @Column(length = 255)
    private String description; // Description du produit

    @Column(nullable = false)
    private BigDecimal price; // Prix en dollars

    @Column(length = 255)
    private String image; // URL ou nom du fichier image

    @Column(nullable = false)
    private Integer stock; // Quantité disponible en stock

    @Column(nullable = false)
    private boolean featured; // Produit mis en avant ou non

    // Mapping simple par ID de catégorie (relation manuelle en DAO)
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

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
}
