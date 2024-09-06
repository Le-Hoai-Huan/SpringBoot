package vn.huan.shoppingcart.ShoppingCart.exceptions;

public class ProductNotFoundException extends  RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
