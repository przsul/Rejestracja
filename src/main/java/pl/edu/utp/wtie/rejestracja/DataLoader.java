package pl.edu.utp.wtie.rejestracja;

import java.sql.Timestamp;

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
    
    Timestamp startDateTime = new Timestamp(1579949255613L);
    Timestamp endDateTime = new Timestamp(1579949255613L);

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

        Appointment appointment = new Appointment(1L, startDateTime, endDateTime, doctor, patient);        
        appointmentRepository.save(appointment);

        // Appointment appointment2 = new Appointment(2L, startDateTime, endDateTime, doctor, patient);        
        // appointmentRepository.save(appointment2);
    }
}