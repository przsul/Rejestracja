package pl.edu.utp.wtie.rejestracja.model;

import lombok.Data;

@Data
public class SearchDoctorModel {

    private String doctorFirstName;

    private String doctorLastName;

    private String city;

    private String specialization;

}
