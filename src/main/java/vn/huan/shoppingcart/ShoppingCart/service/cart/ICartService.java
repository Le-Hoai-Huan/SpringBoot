package vn.huan.shoppingcart.ShoppingCart.service.cart;

import vn.huan.shoppingcart.ShoppingCart.model.Cart;
import vn.huan.shoppingcart.ShoppingCart.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
