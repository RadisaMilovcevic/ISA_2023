package rs.isa.medsupply.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static rs.isa.medsupply.help.AppConstants.USER_ROLE;

@Entity
@DiscriminatorColumn(name = "user")
@NoArgsConstructor
@Data
public class User extends BaseUser {

    public User(String email, String password, String firstName, String lastName, LocalDate birthday, String idNumber, String phoneNumber) {
        super(email, password, firstName, lastName, birthday, idNumber, phoneNumber);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(USER_ROLE));
    }

}
