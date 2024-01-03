package rs.isa.medsupply.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.isa.medsupply.dto.JwtTokenDTO;
import rs.isa.medsupply.dto.LoginDTO;
import rs.isa.medsupply.dto.UserDTO;
import rs.isa.medsupply.model.User;
import rs.isa.medsupply.service.AuthService;
import rs.isa.medsupply.service.TokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/add-company-admin")
    public ResponseEntity<?> addCompanyAdmin(@RequestBody UserDTO userDTO) {
        UserDTO companyAdmin = authService.addCompanyAdmin(userDTO);
        return ResponseEntity.ok(companyAdmin);
    }

    @PostMapping("/add-system-admin")
    public ResponseEntity<?> addSystemAdmin(@RequestBody UserDTO userDTO) {
        UserDTO systemAdmin = authService.addSystemAdmin(userDTO);
        return ResponseEntity.ok(systemAdmin);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            var authUser = authenticationManager.authenticate(usernamePassword);
            var accessToken = tokenService.generateAccessToken((User) authUser.getPrincipal());
            return ResponseEntity.ok(new JwtTokenDTO(accessToken));
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
