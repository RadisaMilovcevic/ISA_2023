package rs.isa.medsupply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.isa.medsupply.dto.UserDTO;
import rs.isa.medsupply.help.AppConstants;
import rs.isa.medsupply.model.CompanyAdmin;
import rs.isa.medsupply.model.SystemAdmin;
import rs.isa.medsupply.model.User;
import rs.isa.medsupply.repository.BaseUserRepository;

import static rs.isa.medsupply.help.AppConstants.*;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    BaseUserRepository baseUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        var user = baseUserRepository.findByEmail(email);
        return user;
    }

    public UserDetails register(UserDTO userDTO) {
        if (baseUserRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email \"" + userDTO.getEmail() + "\" already exists!");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        User newUser = new User(userDTO.getEmail(), encryptedPassword, userDTO.getFirstName(), userDTO.getLastName()
                , userDTO.getBirthday(), userDTO.getIdNumber(), userDTO.getPhoneNumber());
        return baseUserRepository.save(newUser);
    }

    public UserDTO addCompanyAdmin(UserDTO userDTO) {
        if (baseUserRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email \"" + userDTO.getEmail() + "\" already exists!");
        }
        CompanyAdmin newCompanyAdmin = (CompanyAdmin) createUser(userDTO, COMPANY_ADMIN_ROLE);
        CompanyAdmin savedCompanyAdmin = baseUserRepository.save(newCompanyAdmin);
        return UserDTO.builder().email(savedCompanyAdmin.getEmail())
                .firstName(savedCompanyAdmin.getFirstName())
                .lastName(savedCompanyAdmin.getLastName())
                .birthday(savedCompanyAdmin.getBirthday())
                .idNumber(savedCompanyAdmin.getIdNumber())
                .phoneNumber(savedCompanyAdmin.getPhoneNumber())
                .build();
    }

    public UserDTO addSystemAdmin(UserDTO userDTO) {
        if (baseUserRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email \"" + userDTO.getEmail() + "\" already exists!");
        }
        SystemAdmin newSystemAdmin = (SystemAdmin) createUser(userDTO, SYSTEM_ADMIN_ROLE);
        SystemAdmin savedSystemAdmin = baseUserRepository.save(newSystemAdmin);
        return UserDTO.builder().email(savedSystemAdmin.getEmail())
                .firstName(savedSystemAdmin.getFirstName())
                .lastName(savedSystemAdmin.getLastName())
                .birthday(savedSystemAdmin.getBirthday())
                .idNumber(savedSystemAdmin.getIdNumber())
                .phoneNumber(savedSystemAdmin.getPhoneNumber())
                .build();
    }

    private Object createUser(UserDTO userDTO, String role) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        Object newUser;
        switch (role) {
            case COMPANY_ADMIN_ROLE:
                newUser = new CompanyAdmin(userDTO.getEmail(), encryptedPassword, userDTO.getFirstName(), userDTO.getLastName()
                        , userDTO.getBirthday(), userDTO.getIdNumber(), userDTO.getPhoneNumber());
                break;
            case SYSTEM_ADMIN_ROLE:
                newUser = new SystemAdmin(userDTO.getEmail(), encryptedPassword, userDTO.getFirstName(), userDTO.getLastName()
                        , userDTO.getBirthday(), userDTO.getIdNumber(), userDTO.getPhoneNumber());
                break;
            default:
                newUser = new User(userDTO.getEmail(), encryptedPassword, userDTO.getFirstName(), userDTO.getLastName()
                        , userDTO.getBirthday(), userDTO.getIdNumber(), userDTO.getPhoneNumber());

        }
        return newUser;
    }

}
