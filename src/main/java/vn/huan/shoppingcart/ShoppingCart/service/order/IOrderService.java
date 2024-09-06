package vn.huan.shoppingcart.ShoppingCart.service.order;

import vn.huan.shoppingcart.ShoppingCart.dto.OrderDto;
import vn.huan.shoppingcart.ShoppingCart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
