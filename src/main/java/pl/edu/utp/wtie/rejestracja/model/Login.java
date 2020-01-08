package pl.edu.utp.wtie.rejestracja.model;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import pl.edu.utp.wtie.rejestracja.constraints.ValidPassword;

/**
 * Login
 */
@Data
public class Login {

    @NotBlank(message = "E-mail is mandatory")
    @Column(unique = true)
    @Email(message = "Wrong e-mail address")
    private String email;

    @ValidPassword
    private String password;
}