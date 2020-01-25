package pl.edu.utp.wtie.rejestracja.model;

import lombok.Data;

import javax.validation.constraints.Size;


@Data
public class DoctorSettings {

    @Size(min = 1, max = 255)
    private String specialization;

    @Size(min = 1, max = 255)
    private String city;

    @Size(min = 1, max = 255)
    private String currentHospital;

    @Size(min = 9, max = 9)
    private String phone;

}
