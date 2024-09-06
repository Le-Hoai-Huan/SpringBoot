package vn.huan.shoppingcart.ShoppingCart.dto;

import lombok.Data;
import vn.huan.shoppingcart.ShoppingCart.model.CartItem;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
        private Long cartId;
        private Set<CartItemDto> items;
        private BigDecimal totalAmount;
}
