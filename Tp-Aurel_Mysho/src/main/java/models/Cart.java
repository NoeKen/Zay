/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 * Représente le panier d'un utilisateur, contenant une liste de CartItem.
 */
public class Cart {

    private List<CartItem> items;
    private BigDecimal totalCartPrice;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalCartPrice = BigDecimal.ZERO;
    }

    // --- Méthodes pour gérer le panier ---

    /**
     * Ajoute un article au panier. S'il existe déjà, met à jour la quantité.
     * @param newItem L'article à ajouter.
     */
//    public void addItem(CartItem newItem) {
//        boolean itemExists = false;
//        for (CartItem existingItem : items) {
//            // Vérifie si le produit et la taille sont identiques
//            if (existingItem.getProduct().getId() == newItem.getProduct().getId()
//                    && existingItem.getSize().equals(newItem.getSize())) {
//                existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
//                itemExists = true;
//                break;
//            }
//        }
//        if (!itemExists) {
//            items.add(newItem);
//        }
//        // Recalculer le prix total du panier
//        calculateTotalPrice();
//    }
    
    // Ajoute d'autres méthodes pour retirer un article, vider le panier, etc.
    
    // --- Getters et Setters ---

    public List<CartItem> getItems() {
        return items;
    }
    
    public BigDecimal getTotalCartPrice() {
        return totalCartPrice;
    }
    
//    private void calculateTotalPrice() {
//        this.totalCartPrice = items.stream()
//            .map(CartItem::getTotalPrice)
//            .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
}