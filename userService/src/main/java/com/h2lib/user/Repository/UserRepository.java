package com.h2lib.user.Repository;

import com.h2lib.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUsersByEmail(String email);

    boolean existsUserByEmail(String email);

    User findUsersById(Long id);
}
