package pl.edu.utp.wtie.rejestracja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Doctor;

import java.util.List;

/**
 * DoctorRepository
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByEmail(String email);
    List<Doctor> findByFirstNameOrLastNameOrCity(String firstName, String lastName, String city);
}