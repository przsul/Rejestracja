package pl.edu.utp.wtie.rejestracja.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Appointment;

/**
 * AppointmentRepository
 */
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long>{}