package pl.edu.utp.wtie.rejestracja.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endDateTime;

    private String dayOfTheWeek;

    private boolean isBooked;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    public Appointment() {
    }

    public void setDayOfTheWeek() {
        String[] daysInWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date;
        Calendar cal = Calendar.getInstance();

        try {
            date = (Date) formatter.parse("2020-01-29T09:00");
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.dayOfTheWeek = daysInWeek[cal.get(Calendar.DAY_OF_WEEK)];
    }

    public Appointment(long id, Date startDateTime, Date endDateTime, Doctor doctor,
            Patient patient, boolean isBooked) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.doctor = doctor;
        this.patient = patient;
        this.isBooked = isBooked;
        setDayOfTheWeek();
    }
}