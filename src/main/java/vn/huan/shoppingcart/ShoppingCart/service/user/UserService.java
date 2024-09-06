package vn.huan.shoppingcart.ShoppingCart.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.huan.shoppingcart.ShoppingCart.dto.UserDto;
import vn.huan.shoppingcart.ShoppingCart.exceptions.AlreadyExistsException;
import vn.huan.shoppingcart.ShoppingCart.exceptions.ResourceNotFoundException;
import vn.huan.shoppingcart.ShoppingCart.model.User;
import vn.huan.shoppingcart.ShoppingCart.repository.UserRepository;
import vn.huan.shoppingcart.ShoppingCart.request.CreateUserRequest;
import vn.huan.shoppingcart.ShoppingCart.request.UserUpdateRequest;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userReponsitory;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(long userId) {
        return userReponsitory.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found !"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userReponsitory.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userReponsitory.save(user);
                }).orElseThrow(()-> new AlreadyExistsException("Oops" +request.getEmail()+"already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userReponsitory.findById(userId)
                .map(existingUser->{
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userReponsitory.save(existingUser);
                }).orElseThrow(()-> new AlreadyExistsException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userReponsitory.findById(userId)
                .ifPresentOrElse(userReponsitory::delete,()->
        {
            throw  new ResourceNotFoundException("User not found!");

        });
    }

    @Override
    public UserDto convertUserToDto(User user){
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public User getAuthemticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return userReponsitory.findByEmail(email);
    }
}
