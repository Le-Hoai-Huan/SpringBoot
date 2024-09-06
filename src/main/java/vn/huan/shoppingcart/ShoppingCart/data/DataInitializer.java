package vn.huan.shoppingcart.ShoppingCart.data;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.huan.shoppingcart.ShoppingCart.model.Role;
import vn.huan.shoppingcart.ShoppingCart.model.User;
import vn.huan.shoppingcart.ShoppingCart.repository.UserRepository;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Set<String> defaultRoles= Set.of("ROLE_ADMIN","ROLE_USER");
           createDefaultUserIfNotExits();
            createDefaultRoleIfNotExits(defaultRoles);
          createDefaultAdminIfNotExits();
    }

    private void createDefaultUserIfNotExits(){
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i =1;i<5;i++){
            String defaultEmail = "user"+i+"@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user= new User();
            user.setFirstName("The User");
            user.setLastName("User"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user" + i + " created successfully.");
        }

    }


    private void createDefaultAdminIfNotExits(){
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i =1;i<=2;i++){
            String defaultEmail = "admin"+i+"@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user= new User();
            user.setFirstName("Admin");
            user.setLastName("Admin"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default Admin user" + i + " created successfully.");
        }

    }
    private void createDefaultRoleIfNotExits(Set<String> roles){
        roles.stream()
                .filter(role->roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository ::save);
    }
}
