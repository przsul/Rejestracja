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

    @NotBlank(message = "E-mail jest wymagany")
    @Column(unique = true)
    @Email(message = "Adres e-mail jest niepoprawny")
    private String email;

    @Size(min = 1, max = 25)
    private String firstName;

    @Size(min = 1, max = 25)
    private String lastName;

    @PESEL(message = "Numer PESEL jest niepoprawny")
    private String pesel;

    @Size(min = 1, max = 255)
    private String specialization;

    @Size(min = 1, max = 255)
    private String city;

    @Size(min = 1, max = 255)
    private String currentHospital;

    @Size(min = 9, max = 9)
    private String phone;


}