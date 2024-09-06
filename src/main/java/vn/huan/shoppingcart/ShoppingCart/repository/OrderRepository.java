package vn.huan.shoppingcart.ShoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.huan.shoppingcart.ShoppingCart.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
