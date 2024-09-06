package vn.huan.shoppingcart.ShoppingCart.service.image;

import org.springframework.web.multipart.MultipartFile;
import vn.huan.shoppingcart.ShoppingCart.dto.ImageDto;
import vn.huan.shoppingcart.ShoppingCart.model.Image;
import vn.huan.shoppingcart.ShoppingCart.model.Product;

import java.util.List;

public interface IimageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
