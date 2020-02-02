package pl.edu.utp.wtie.rejestracja.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Appointment;
import pl.edu.utp.wtie.rejestracja.model.Doctor;
import pl.edu.utp.wtie.rejestracja.model.Patient;

/**
 * AppointmentRepository
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAppointmentsByPatientOrderByStartDateTimeDesc(Patient patient);

    List<Appointment> findByDoctorOrderByStartDateTime(Doctor doctor);

    Page<Appointment> findByDoctorFirstNameContainsOrDoctorLastNameContainsOrDoctorCityContainsOrDoctorSpecializationContainsOrderByStartDateTimeDesc(String firstName, String lastName, String city, String specialization, Pageable pageable);

}
