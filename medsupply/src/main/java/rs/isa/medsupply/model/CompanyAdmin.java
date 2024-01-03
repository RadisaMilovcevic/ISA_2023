package rs.isa.medsupply.model;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static rs.isa.medsupply.help.AppConstants.COMPANY_ADMIN_ROLE;

@Entity
@DiscriminatorColumn(name = "company_admin")
@NoArgsConstructor
public class CompanyAdmin extends BaseUser{

    public CompanyAdmin(String email, String password, String firstName, String lastName, LocalDate birthday, String idNumber, String phoneNumber) {
        super(email, password, firstName, lastName, birthday, idNumber, phoneNumber);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(COMPANY_ADMIN_ROLE));
    }

}
