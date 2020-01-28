package pl.edu.utp.wtie.rejestracja.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class SearchDoctorModel {
    private String doctorFirstName;

    private String doctorLastName;

    private String city;

}
