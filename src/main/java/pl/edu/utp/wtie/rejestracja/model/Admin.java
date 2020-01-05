package pl.edu.utp.wtie.rejestracja.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Admin
 */
@Entity
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "E-mail is mandatory")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
}