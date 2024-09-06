package vn.huan.shoppingcart.ShoppingCart.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.huan.shoppingcart.ShoppingCart.exceptions.ResourceNotFoundException;
import vn.huan.shoppingcart.ShoppingCart.model.Cart;
import vn.huan.shoppingcart.ShoppingCart.response.ApiResponse;
import vn.huan.shoppingcart.ShoppingCart.service.cart.ICartService;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;


    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Succes",cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear cart Success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Total Price",totalPrice));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
