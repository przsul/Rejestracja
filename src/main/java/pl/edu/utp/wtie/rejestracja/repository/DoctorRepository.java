package pl.edu.utp.wtie.rejestracja.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Doctor;

/**
 * DoctorRepository
 */
@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findByEmail(String email);
}