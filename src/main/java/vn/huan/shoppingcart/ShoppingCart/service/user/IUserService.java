package vn.huan.shoppingcart.ShoppingCart.service.user;

import vn.huan.shoppingcart.ShoppingCart.dto.UserDto;
import vn.huan.shoppingcart.ShoppingCart.model.User;
import vn.huan.shoppingcart.ShoppingCart.request.CreateUserRequest;
import vn.huan.shoppingcart.ShoppingCart.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthemticatedUser();
}
