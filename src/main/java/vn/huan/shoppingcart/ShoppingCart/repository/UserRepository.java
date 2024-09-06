package vn.huan.shoppingcart.ShoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.huan.shoppingcart.ShoppingCart.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
