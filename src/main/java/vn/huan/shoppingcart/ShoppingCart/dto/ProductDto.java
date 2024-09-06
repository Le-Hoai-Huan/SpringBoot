package vn.huan.shoppingcart.ShoppingCart.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import vn.huan.shoppingcart.ShoppingCart.model.Category;
import vn.huan.shoppingcart.ShoppingCart.model.Image;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;
}
