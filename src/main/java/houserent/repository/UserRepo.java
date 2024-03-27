package houserent.repository;

import houserent.dto.response.SimpleResponse;
import houserent.dto.request.SignUpRequest;
import houserent.entity.User;
import houserent.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Transactional
    @Query("delete from Post p where p.users.id =:userId")
    void deletePost(Long userId);


    @Modifying
    @Transactional
    @Query("delete from Comment c where c.user.id =:userId")
    void deleteComment(Long userId);

    @Modifying
    @Transactional
    @Query("delete from RentInfo r where r.user.id =:userId")
    void deleteRentInfo(Long userId);

    // SimpleResponse replenish(SignUpRequest signUpRequest);
//    SimpleResponse replenish(SignUpRequest signUpRequest);
}
