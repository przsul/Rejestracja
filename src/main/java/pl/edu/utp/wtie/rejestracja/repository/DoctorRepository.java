package pl.edu.utp.wtie.rejestracja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Doctor;

/**
 * DoctorRepository
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByEmail(String email);
    Doctor findByEmailAndPassword(String email, String password);
    List<Doctor> findByFirstNameOrLastNameOrCity(String firstName, String lastName, String city);
}