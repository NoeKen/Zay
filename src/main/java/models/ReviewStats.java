/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author admin
 */
public class ReviewStats {
    private int averageRating;
    private int totalComments;

    public ReviewStats(int averageRating, int totalComments) {
        this.averageRating = averageRating;
        this.totalComments = totalComments;
    }

    // Getters
    public int getAverageRating() {
        return averageRating;
    }

    public int getTotalComments() {
        return totalComments;
    }
}

