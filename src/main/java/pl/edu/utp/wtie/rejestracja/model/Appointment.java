package pl.edu.utp.wtie.rejestracja.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * Appointment
 */
@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String startDateTime;

    private String endDateTime;

    private String dayOfTheWeek;
    
    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    public Appointment() {}

    public void setDayOfTheWeek() {
        String[] daysInWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(startDateTime, formatter);
        this.dayOfTheWeek = daysInWeek[ldt.getDayOfWeek().getValue()];
    }

    public Appointment(long id, String startDateTime, String endDateTime, Doctor doctor,
            Patient patient) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.doctor = doctor;
        this.patient = patient;
        setDayOfTheWeek();
    }


}