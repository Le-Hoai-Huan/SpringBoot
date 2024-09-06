package vn.huan.shoppingcart.ShoppingCart.service.product;

import vn.huan.shoppingcart.ShoppingCart.dto.ProductDto;
import vn.huan.shoppingcart.ShoppingCart.model.Product;
import vn.huan.shoppingcart.ShoppingCart.request.AddProductRequest;
import vn.huan.shoppingcart.ShoppingCart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void  deleteProductByid(Long id);
    Product  updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCatergory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCatergoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand, String name);


    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
