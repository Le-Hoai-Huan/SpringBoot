package vn.huan.shoppingcart.ShoppingCart.data;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.huan.shoppingcart.ShoppingCart.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role , Long> {
    Optional<Role> findByName(String role);
}

