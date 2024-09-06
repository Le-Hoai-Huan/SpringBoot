package vn.huan.shoppingcart.ShoppingCart.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.huan.shoppingcart.ShoppingCart.model.User;
import vn.huan.shoppingcart.ShoppingCart.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(()-> new  UsernameNotFoundException("User not found!"));
        return ShopUserDetails.buildUserDetails(user);
    }
}
