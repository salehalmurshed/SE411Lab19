package psu.edu.ch06.crud04.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import psu.edu.ch06.crud04.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	// Spring Data JDBC derives: SELECT * FROM users WHERE username = ?
	Optional<User> findByUsername(String username);
}
