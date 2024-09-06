package vn.huan.shoppingcart.ShoppingCart.service.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.huan.shoppingcart.ShoppingCart.exceptions.ResourceNotFoundException;
import vn.huan.shoppingcart.ShoppingCart.model.Cart;
import vn.huan.shoppingcart.ShoppingCart.model.CartItem;
import vn.huan.shoppingcart.ShoppingCart.model.Product;
import vn.huan.shoppingcart.ShoppingCart.repository.CartItemRepository;
import vn.huan.shoppingcart.ShoppingCart.repository.CartRepository;
import vn.huan.shoppingcart.ShoppingCart.service.product.IProductService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private  final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final  ICartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {


        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem=cart.getItems().stream()
                .filter(item-> item.getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());

        }
        else {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemToCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove= getCartItem(cartId,productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems().stream().filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item->{
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount =cart.getItems()
                .stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);

    }

    @Override
    public  CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()-> new ResourceNotFoundException("product not found"));
    }
}
