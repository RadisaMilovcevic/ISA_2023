package rs.isa.medsupply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.isa.medsupply.dto.UserDTO;
import rs.isa.medsupply.model.User;
import rs.isa.medsupply.repository.BaseUserRepository;

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
            System.out.println("Username already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        User newUser = new User(userDTO.getEmail(), encryptedPassword, userDTO.getFirstName(), userDTO.getLastName()
                , userDTO.getBirthday(), userDTO.getIdNumber(), userDTO.getPhoneNumber());
        return baseUserRepository.save(newUser);
    }

}
