package vn.huan.shoppingcart.ShoppingCart.controller;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.huan.shoppingcart.ShoppingCart.exceptions.ResourceNotFoundException;
import vn.huan.shoppingcart.ShoppingCart.model.Cart;
import vn.huan.shoppingcart.ShoppingCart.model.User;
import vn.huan.shoppingcart.ShoppingCart.response.ApiResponse;
import vn.huan.shoppingcart.ShoppingCart.service.cart.ICartItemService;
import vn.huan.shoppingcart.ShoppingCart.service.cart.ICartService;
import vn.huan.shoppingcart.ShoppingCart.service.user.IUserService;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity){


        try {
                User user = userService.getAuthemticatedUser();
               Cart cart = cartService.initializeNewCart(user);
            cartItemService.addItemToCart(cart.getId(),productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Add item Success",null));
        } catch (ResourceNotFoundException e) {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        } catch (JwtException e){
            return  ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public  ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){

        try {
            cartItemService.removeItemToCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("Remove item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity){

        try {
            cartItemService.updateItemToCart(cartId,itemId,quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item Success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
