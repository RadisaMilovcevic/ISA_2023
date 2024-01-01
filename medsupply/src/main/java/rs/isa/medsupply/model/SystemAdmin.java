package rs.isa.medsupply.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorColumn(name = "system_admin")
public class SystemAdmin extends BaseUser{

    public SystemAdmin(String email, String password, String firstName, String lastName, LocalDate birthday, String idNumber, String phoneNumber) {
        super(email, password, firstName, lastName, birthday, idNumber, phoneNumber);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_SYSTEM_ADMIN"));
    }

}
