package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une commande complète faite par un client.
 */
public class Order {

    private int id; // identifiant unique de la commande
    private List<OrderLine> orderLines; // toutes les lignes de cette commande
    private BigDecimal total; // prix total de la commande

    /**
     * Constructeur par défaut : initialise une commande vide.
     */
    public Order() {
        this.orderLines = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    }

    /**
     * Ajoute un produit à la commande.
     * @param orderLine la ligne de commande à ajouter
     */
    public void addOrderLine(OrderLine orderLine) {
        this.orderLines.add(orderLine);
        // mise à jour du total
        this.total = this.total.add(orderLine.getSubtotal());
    }

    /**
     * Retourne toutes les lignes de commande.
     */
    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    /**
     * Retourne le total de la commande.
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Définit l'ID de la commande.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne l'ID de la commande.
     */
    public int getId() {
        return id;
    }
}
