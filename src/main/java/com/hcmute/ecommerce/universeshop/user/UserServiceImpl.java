package com.hcmute.ecommerce.universeshop.user;

import com.hcmute.ecommerce.universeshop.role.RoleEntity;
import com.hcmute.ecommerce.universeshop.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserEntity registerNewUser(UserEntity user) {
        // Find the role by role name
        Optional<RoleEntity> optionalRole = roleRepository.findById("USER");

        // Check if the role is present before getting it
        if (optionalRole.isPresent()) {
            RoleEntity role = optionalRole.get();
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);

            user.setUserPassword(getEncodedPassword(user.getUserPassword()));
            return userRepository.save(user);
        } else {
            // Handle the case when the role is not found
            throw new RuntimeException("Role with name 'User' not found");
        }
    }

    @Override
    public void initRolesAndUser() {
        RoleEntity adminRole = new RoleEntity();
        adminRole.setRoleName("ADMIN");
        adminRole.setRoleDescription("ADMIN ROLE");
        roleRepository.save(adminRole);

        RoleEntity userRole = new RoleEntity();
        userRole.setRoleName("USER");
        userRole.setRoleDescription("USER ROLE DEFAULT");
        roleRepository.save(userRole);

        UserEntity adminUser = new UserEntity();
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        Set<RoleEntity> adminRoles = new HashSet<>() ;
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
        userRepository.save(adminUser);

        UserEntity user = new UserEntity();
        user.setUserFirstName("tri");
        user.setUserLastName("sharma");
        user.setUserName("user123");
        user.setUserPassword(getEncodedPassword("user@pass"));
        Set<RoleEntity> userRoles = new HashSet<>() ;
        userRoles.add(userRole);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getUserEntities() {
        return userRepository.findAll();
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public UserEntity getUserByUserName(String userName) {
        return null;
    }
}
