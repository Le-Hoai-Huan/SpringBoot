package vn.huan.shoppingcart.ShoppingCart.request;

import lombok.Data;
import vn.huan.shoppingcart.ShoppingCart.model.Category;

import java.math.BigDecimal;
@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
