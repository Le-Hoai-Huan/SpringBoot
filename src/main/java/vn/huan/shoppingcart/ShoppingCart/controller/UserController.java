package vn.huan.shoppingcart.ShoppingCart.controller;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.huan.shoppingcart.ShoppingCart.dto.UserDto;
import vn.huan.shoppingcart.ShoppingCart.exceptions.AlreadyExistsException;
import vn.huan.shoppingcart.ShoppingCart.exceptions.ResourceNotFoundException;
import vn.huan.shoppingcart.ShoppingCart.model.User;
import vn.huan.shoppingcart.ShoppingCart.request.CreateUserRequest;
import vn.huan.shoppingcart.ShoppingCart.request.UserUpdateRequest;
import vn.huan.shoppingcart.ShoppingCart.response.ApiResponse;
import vn.huan.shoppingcart.ShoppingCart.service.user.IUserService;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;


    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserByid(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("success",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public  ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request ){
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User succes",userDto ));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,@PathVariable Long userId){
        try {
            User user = userService.updateUser(request,userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
