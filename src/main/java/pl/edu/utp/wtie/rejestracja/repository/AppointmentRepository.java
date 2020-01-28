package pl.edu.utp.wtie.rejestracja.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Appointment;
import pl.edu.utp.wtie.rejestracja.model.Doctor;
import pl.edu.utp.wtie.rejestracja.model.Patient;

import java.util.List;

/**
 * AppointmentRepository
 */
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long>{
    List<Appointment> findAppointmentsByPatientOrderByStartDateTimeDesc(Patient patient);
    List<Appointment> findAppointmentsByDoctorOrderByStartDateTimeDesc(Doctor doctor);
}