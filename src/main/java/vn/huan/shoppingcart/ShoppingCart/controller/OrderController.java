package vn.huan.shoppingcart.ShoppingCart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.huan.shoppingcart.ShoppingCart.dto.OrderDto;
import vn.huan.shoppingcart.ShoppingCart.exceptions.ResourceNotFoundException;
import vn.huan.shoppingcart.ShoppingCart.model.Order;
import vn.huan.shoppingcart.ShoppingCart.response.ApiResponse;
import vn.huan.shoppingcart.ShoppingCart.service.order.IOrderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {


    private final IOrderService orderService;
    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return  ResponseEntity.ok(new ApiResponse("Item Order Success", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured",e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto order = orderService.getOrder(orderId);
            return  ResponseEntity.ok(new ApiResponse("Item order success" , order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!",e.getMessage()));
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return  ResponseEntity.ok(new ApiResponse("Item order success" , order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!",e.getMessage()));
        }
    }




}
