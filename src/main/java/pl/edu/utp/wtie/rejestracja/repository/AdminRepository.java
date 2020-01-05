package pl.edu.utp.wtie.rejestracja.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.Admin;

/**
 * AdminRepository
 */
@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {}