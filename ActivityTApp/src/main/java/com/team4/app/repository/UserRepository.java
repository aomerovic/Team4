package com.team4.app.repository;

import com.team4.app.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT DISTINCT u.role FROM User u")
    Set<String> findDistinctRole();

    User findByPasswordResetToken(String passwordResetToken);

    User findByConfirmAccountToken(String confirmAccountToken);

    User findByReactivateAccountToken(String reactivateAccountToken);
}