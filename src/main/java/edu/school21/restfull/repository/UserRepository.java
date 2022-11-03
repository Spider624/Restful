package edu.school21.restfull.repository;

import edu.school21.restfull.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByLogin(String login);

}
