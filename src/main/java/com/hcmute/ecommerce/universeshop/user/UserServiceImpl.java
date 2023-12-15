package com.hcmute.ecommerce.universeshop.user;

import com.hcmute.ecommerce.universeshop.base.email.EmailService;
import com.hcmute.ecommerce.universeshop.base.email.OTPService;
import com.hcmute.ecommerce.universeshop.base.exception.*;
import com.hcmute.ecommerce.universeshop.base.utils.EmailUtils;
import com.hcmute.ecommerce.universeshop.role.RoleEntity;
import com.hcmute.ecommerce.universeshop.role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.hcmute.ecommerce.universeshop.base.utils.EmailUtils.isEmailValid;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ErrorMessage errorMessage;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OTPService opsService;
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ErrorMessage errorMessage) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.errorMessage = errorMessage;
    }

    @Override
    public UserEntity registerNewUser(UserEntity user) {
        if(userRepository.getUserByUsername(user.getUserName()).isPresent()) {
            throw new InputValidationException(errorMessage.getMessage(Constants.USERNAME_EXISTED));
        }
        if(Boolean.FALSE.equals(isEmailValid(user.getUserName()))) {
            throw new InputValidationException(errorMessage.getMessage(Constants.USER_EMAIL_INVALID));
        }
        // Find the role by role name
        Optional<RoleEntity> optionalRole = roleRepository.findById("USER");

        // Check if the role is present before getting it
        if (optionalRole.isPresent()) {
            RoleEntity role = optionalRole.get();
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            user.setActivated(Boolean.FALSE);

            user.setUserPassword(getEncodedPassword(user.getUserPassword()));
            //Generate the code
            String OtpCode = opsService.generate(8);
            user.setVerificationCode(OtpCode);
            user.setIsDeleted(Boolean.FALSE);
            sendWelcomeEmail(user.getUserName(), "One-Time Password (OTP) - Expire in 5 minutes!", createEmailVariables(user, OtpCode));
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
        RoleEntity sellerRole = new RoleEntity();
        sellerRole.setRoleName("SELLER");
        sellerRole.setRoleDescription("SELLER ROLE");
        roleRepository.save(sellerRole);

        RoleEntity userRole = new RoleEntity();
        userRole.setRoleName("USER");
        userRole.setRoleDescription("USER ROLE DEFAULT");
        roleRepository.save(userRole);

        UserEntity adminUser = new UserEntity();
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserName("admin@gmail.com");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        Set<RoleEntity> adminRoles = new HashSet<>() ;
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
        adminUser.setActivated(Boolean.TRUE);
        adminUser.setIsDeleted(Boolean.FALSE);
        userRepository.save(adminUser);

        UserEntity seller = new UserEntity();
        seller.setUserFirstName("David");
        seller.setUserLastName("Tran");
        seller.setUserName("seller@gmail.com");
        seller.setAddress("Ha Noi");
        seller.setContactNumber("038 294 3017");
        seller.setUserPassword(getEncodedPassword("seller@pass"));
        seller.setActivated(Boolean.TRUE);
        Set<RoleEntity> sellerRoles = new HashSet<>() ;
        sellerRoles.add(sellerRole);
        seller.setRoles(sellerRoles);
        seller.setIsDeleted(Boolean.FALSE);
        userRepository.save(seller);

        UserEntity user = new UserEntity();
        user.setUserFirstName("tri");
        user.setUserLastName("nguyen");
        user.setUserName("tri@gmail.com");
        user.setAddress("Truong dai hoc SPKT - So 1 VVN - HCM");
        user.setContactNumber("038 294 3017");
        user.setUserPassword(getEncodedPassword("user@pass"));
        user.setActivated(Boolean.TRUE);
        Set<RoleEntity> userRoles = new HashSet<>() ;
        userRoles.add(userRole);
        user.setIsDeleted(Boolean.FALSE);
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

    @Override
    public void verifyAndActivateUser(String email, String verificationCode) {
        verificationCode = verificationCode.trim();
        // Retrieve the user by email
        UserEntity user = userRepository.findById(email).get();
        if (user != null && user.getActivated() != null && !user.getActivated()) {
            if (verificationCode.equals(user.getVerificationCode())) {
                // Activate the user
                user.setActivated(true);
                user.setIsDeleted(Boolean.FALSE);
                userRepository.save(user);
            } else {
                throw new InputValidationException(errorMessage.getMessage(errorMessage.getMessage(Constants.INVALID_OTP_CODE)));
            }
        } else {
            throw new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND));
        }
    }

    @Override
    public void deleteUser(String userName) {
        UserEntity user = userRepository.findById(userName).orElseThrow(
                () -> new InputValidationException(errorMessage.getMessage(Constants.USER_NOT_FOUND))
        );
        user.setIsDeleted(Boolean.TRUE);
        user.setActivated(Boolean.FALSE);
        userRepository.save(user);
    }

    @Override
    public void reviveUser(String userName) {
        UserEntity user = userRepository.findById(userName).orElseThrow(
                () -> new InputValidationException(errorMessage.getMessage(Constants.USER_NOT_FOUND))
        );
        user.setIsDeleted(Boolean.FALSE);
        user.setActivated(Boolean.TRUE);
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getUsersDelete() {
        return userRepository.getDeletedUser();
    }

    public Boolean isExistingEmail(String email) {
        return userRepository.existsById(email);
    }
    private Map<String, Object> createEmailVariables(UserEntity user, String OTPCode) {
        // Add any necessary variables for the email content
        Map<String, Object> variables = new HashMap<>();
        variables.put("otpCode", OTPCode);
        variables.put("fullName", "Buddies");
        return variables;
    }
    private void sendWelcomeEmail(String email, String subject, Map<String, Object> variables) {
        try {
            emailService.sendEmail(email, variables, subject);
        } catch (Exception e) {
            log.error("Error sending welcome email to " + email, e);
            throw new SystemException(errorMessage.getMessage(Constants.SEND_EMAIL_ERROR));
        }
    }
}
