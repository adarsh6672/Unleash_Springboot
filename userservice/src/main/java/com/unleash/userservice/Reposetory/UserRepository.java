package com.unleash.userservice.Reposetory;

import com.unleash.userservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User ,Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = 'USER'")
    List<User> findAllUsersWithUserRole();

    @Query("SELECT u FROM User u WHERE u.role = 'COUNSELOR'")
    List<User> findAllUsersWithCounselorRole();



}
