package pl.edu.utp.wtie.rejestracja.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.edu.utp.wtie.rejestracja.model.User;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {}