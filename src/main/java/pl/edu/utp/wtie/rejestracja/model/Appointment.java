package pl.edu.utp.wtie.rejestracja.model;

import java.sql.Timestamp;
import java.util.Calendar;

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

    private Timestamp startDateTime;

    private Timestamp endDateTime;

    private String dayOfTheWeek;
    
    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    public Appointment() {}

    public Appointment(long id, Timestamp startDateTime, Timestamp endDateTime, Doctor doctor,
            Patient patient) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.doctor = doctor;
        this.patient = patient;

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDateTime);

        String[] daysInWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        
        this.dayOfTheWeek = daysInWeek[cal.get(Calendar.DAY_OF_WEEK)-1];
    }
}