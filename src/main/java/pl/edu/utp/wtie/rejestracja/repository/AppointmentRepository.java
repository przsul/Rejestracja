package pl.edu.utp.wtie.rejestracja.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Appointment;
import pl.edu.utp.wtie.rejestracja.model.Doctor;

/**
 * AppointmentRepository
 */
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findByDoctorOrderByStartDateTime(Doctor doctor);
}