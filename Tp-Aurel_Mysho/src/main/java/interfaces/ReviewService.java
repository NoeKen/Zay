/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.util.List;
import models.Review;
import models.ReviewStats;

/**
 *
 * @author admin
 */
public interface ReviewService {
    public ReviewStats getReviewStatsByProductId(int productId);
    public List<Review> getReviewsByProductId(int productID);
}
