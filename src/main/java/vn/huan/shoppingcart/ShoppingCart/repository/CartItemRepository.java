package vn.huan.shoppingcart.ShoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.huan.shoppingcart.ShoppingCart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem ,Long> {
    void deleteAllByCartId(Long id);
}
