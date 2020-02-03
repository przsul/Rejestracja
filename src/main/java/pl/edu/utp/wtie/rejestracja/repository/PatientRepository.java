package pl.edu.utp.wtie.rejestracja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Patient;

/**
 * UserRepository
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);
    Patient findByEmailAndPassword(String email, String password);
    List<Patient> findByEmailOrderById(String email);
}