package vn.huan.shoppingcart.ShoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.huan.shoppingcart.ShoppingCart.model.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
     public List<Image> findByProductId(Long id);
}
