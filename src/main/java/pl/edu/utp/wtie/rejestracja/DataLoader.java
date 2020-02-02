package pl.edu.utp.wtie.rejestracja;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    Date startDateTime1;
    Date endDateTime1;
    Date startDateTime2;
    Date endDateTime2;


    public DataLoader() {
        try {
            startDateTime = (Date) formatter.parse("2020-01-29T09:00");
            endDateTime = (Date) formatter.parse("2020-01-29T10:00");
            startDateTime1 = (Date) formatter.parse("2020-01-29T11:00");
            endDateTime1 = (Date) formatter.parse("2020-01-29T12:00");
            startDateTime2 = (Date) formatter.parse("2020-01-29T13:00");
            endDateTime2 = (Date) formatter.parse("2020-01-29T14:00");
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
        doctor.setCurrentHospital("Bydgoszcz szpital Wuhang");
        doctor.setCity("Bydgoszcz");
        doctor.setSpecialization("Ginekolog");
        doctor.setPassword(passwordEncoder.encode("Jankowal123!@#"));
        doctor.setPesel("97020406075");

        doctorRepository.save(doctor);

        Doctor doctor2 = new Doctor();
        doctor2.setId(2L);
        doctor2.setEmail("doctor2@doctor.pl");
        doctor2.setFirstName("Tomek");
        doctor2.setLastName("Las");
        doctor2.setCurrentHospital("Toruń Jurasza");
        doctor2.setCity("Toruń");
        doctor2.setSpecialization("Chirrug");
        doctor2.setPassword(passwordEncoder.encode("Jankowal123!@#"));
        doctor2.setPesel("97050907531");

        doctorRepository.save(doctor2);
        
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

        Appointment appointment2 = new Appointment(2L, startDateTime1, endDateTime1, doctor, patient, true);
        appointmentRepository.save(appointment2);
        Appointment appointment3 = new Appointment(3L, startDateTime2, endDateTime2, doctor, null, true);
        appointmentRepository.save(appointment3);
    }
}