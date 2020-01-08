package pl.edu.utp.wtie.rejestracja.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Patient;

/**
 * UserRepository
 */
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {}