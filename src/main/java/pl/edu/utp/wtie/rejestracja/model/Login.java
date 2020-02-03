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

    @NotBlank(message = "E-mail jest wymagany")
    @Column(unique = true)
    @Email(message = "Adres e-mail jest niepoprawny")
    private String email;

    @ValidPassword
    private String password;
}