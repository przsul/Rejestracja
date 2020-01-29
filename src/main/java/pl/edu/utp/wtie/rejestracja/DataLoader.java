package pl.edu.utp.wtie.rejestracja;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import pl.edu.utp.wtie.rejestracja.model.Appointment;
import pl.edu.utp.wtie.rejestracja.model.Doctor;
import pl.edu.utp.wtie.rejestracja.model.Patient;
import pl.edu.utp.wtie.rejestracja.repository.AppointmentRepository;
import pl.edu.utp.wtie.rejestracja.repository.DoctorRepository;
import pl.edu.utp.wtie.rejestracja.repository.PatientRepository;

/**
 * DataLoader
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    Date startDateTime;
    Date endDateTime;

    public DataLoader() {
        try {
            startDateTime = (Date) formatter.parse("2020-01-29T09:00");
            endDateTime = (Date) formatter.parse("2020-01-29T10:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Doctor doctor = new Doctor();
		doctor.setId(1L);
		doctor.setEmail("doctor@doctor.pl");
		doctor.setFirstName("Jan");
		doctor.setLastName("Kowal");
		doctor.setPassword(passwordEncoder.encode("Jankowal123!@#"));
		doctor.setPesel("97020406075");

        doctorRepository.save(doctor);
        
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@patient.pl");
        patient.setFirstName("Adam");
        patient.setLastName("Nowak");
        patient.setPassword(passwordEncoder.encode("Adamnowak123!@#"));
        patient.setPesel("84101126551");

        patientRepository.save(patient);

        Appointment appointment = new Appointment(1L, startDateTime, endDateTime, doctor, patient, false);
        appointmentRepository.save(appointment);

        Appointment appointment2 = new Appointment(2L, startDateTime, endDateTime, doctor, patient, true);
        appointmentRepository.save(appointment2);
    }
}