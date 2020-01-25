package pl.edu.utp.wtie.rejestracja.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.pl.PESEL;

import lombok.Data;
import pl.edu.utp.wtie.rejestracja.constraints.ValidPassword;

/**
 * Doctor
 */
@Entity
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ValidPassword
    private String password;

    @NotBlank(message = "E-mail is mandatory")
    @Column(unique = true)
    @Email(message = "Wrong e-mail address")
    private String email;

    @Size(min = 1, max = 25)
    private String firstName;

    @Size(min = 1, max = 25)
    private String lastName;

    @PESEL(message = "Wrong PESEL")
    private String pesel;
}