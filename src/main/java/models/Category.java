package models;

import jakarta.persistence.*; 

/**
 * Modèle représentant une catégorie.
 * Utilisable à la fois avec DAO (via JDBC) et JPA (via EntityManager).
 */

@Entity // Indique que cette classe est une entité persistante JPA
@Table(name = "categories") // Lie cette entité à la table 'categories'
public class Category {

    @Id // Définit la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentée 
    private int id;

    @Column(nullable = false, length = 100) // Champ non null avec longueur max de 100 caractères
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "image_url") // Précise le nom exact de la colonne en DB
    private String imageUrl;

    // Constructeur vide obligatoire pour JPA
    public Category() {}

    // Constructeur avec ID (utile pour DAO ou pour updates)
    public Category(int id, String name, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Constructeur sans ID (utilisé lors des insertions)
    public Category(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // ----------------- Getters & Setters -------------------

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
