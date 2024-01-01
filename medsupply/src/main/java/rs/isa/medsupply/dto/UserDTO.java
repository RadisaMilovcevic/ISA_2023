package rs.isa.medsupply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    String email;

    String password;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private String idNumber;

    private String phoneNumber;

}
