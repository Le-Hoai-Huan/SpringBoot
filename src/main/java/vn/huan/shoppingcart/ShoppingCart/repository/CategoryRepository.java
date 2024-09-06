package vn.huan.shoppingcart.ShoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.huan.shoppingcart.ShoppingCart.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);
}
