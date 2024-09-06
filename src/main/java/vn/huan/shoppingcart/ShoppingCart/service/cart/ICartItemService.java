package vn.huan.shoppingcart.ShoppingCart.service.cart;

import vn.huan.shoppingcart.ShoppingCart.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemToCart(Long cartId, Long productId);
    void updateItemToCart(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
