package rs.isa.medsupply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import rs.isa.medsupply.model.BaseUser;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {

    UserDetails findByEmail(String email);

}
