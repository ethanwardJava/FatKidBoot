package com.arcade.FatKidBoot.repository;

import com.arcade.FatKidBoot.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameIgnoreCase(String username);

    Page<User> findByUsernameContainingIgnoreCase(@Param("username") String username, Pageable pageable);
}
