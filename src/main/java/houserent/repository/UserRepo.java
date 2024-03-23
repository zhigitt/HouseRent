package houserent.repository;

import houserent.dto.SimpleResponse;
import houserent.dto.request.SignUpRequest;
import houserent.entity.User;
import houserent.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {



    @Query("select u from User u where u.email =:email")
    Optional<User> findByEmail(String email);

    default User getByEmail(String email){
        return findByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email: " + email + " not found"));
    }

    boolean existsByEmail(String email);

    SimpleResponse replenish(SignUpRequest signUpRequest);
}
