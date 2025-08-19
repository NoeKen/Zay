package models;

import java.math.BigDecimal;

/**
 * Représente une ligne de commande (un produit + quantité).
 */
public class OrderLine {

    private Product product; // le produit choisi
    private int quantity; // la quantité commandée

    /**
     * Constructeur pour créer une ligne de commande.
     */
    public OrderLine(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Retourne le produit.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Retourne la quantité commandée.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Retourne le prix total pour cette ligne (prix du produit * quantité).
     */
    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
