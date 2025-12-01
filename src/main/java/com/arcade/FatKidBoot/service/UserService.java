package com.arcade.FatKidBoot.service;


import com.arcade.FatKidBoot.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveNewUser(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    User updateUser(Long id, User user);

    User partialUpdateUser(Long id, User patchUser);

    void deleteById(Long id);

    Optional<User> findByUserName(String username);

    String verify(User user);

    Page<User> searchUserByName(String username, Pageable pageable);
}
