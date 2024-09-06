package vn.huan.shoppingcart.ShoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.huan.shoppingcart.ShoppingCart.model.Cart;

public interface CartRepository extends JpaRepository<Cart , Long> {
    Cart findByUserId(Long userId);
}
