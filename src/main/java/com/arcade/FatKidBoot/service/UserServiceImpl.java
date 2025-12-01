package com.arcade.FatKidBoot.service;

import com.arcade.FatKidBoot.entity.User;
import com.arcade.FatKidBoot.exception.UserNotFoundException;
import com.arcade.FatKidBoot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.arcade.FatKidBoot.config.WebSecurityConfig.bCryptPasswordEncoder;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // =========================  /REGISTER ==========================
    @Transactional
    @Override
    public User saveNewUser(User user) {
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }


    // =========================  GET THE USER BY ID  ==========================
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("USER YOU ARE SEARCHING FOR NOT FOUND")));
    }

    // =========================  GET ALL USERS  ==========================
    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Empty");
        }
        return users;
    }


    // =========================  UPDATE USER (FULL)  ==========================
    @Transactional
    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Full replace – copy all fields (except id)
                    existingUser.setUsername(updatedUser.getUsername());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setPassword(updatedUser.getPassword()); // NO SECURITY YET
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }


    // =========================  UPDATE USER (partial)  ==========================
    @Transactional
    @Override
    public User partialUpdateUser(Long id, User patchUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (Objects.nonNull(patchUser.getUsername()) && !patchUser.getUsername().isBlank()) {
                existingUser.setUsername(patchUser.getUsername());
            }
            if (Objects.nonNull(patchUser.getEmail()) && !patchUser.getEmail().isBlank()) {
                existingUser.setEmail(patchUser.getEmail());
            }
            if (Objects.nonNull(patchUser.getPassword()) && !patchUser.getPassword().isBlank()) {
                existingUser.setPassword(patchUser.getPassword()); // NO SECURITY YET
            }
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    // =========================  DELETE BY ID   ==========================
    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // =========================  /LOGIN  ==========================
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUserName(String username) {
        return Optional.ofNullable(userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("USER YOU ARE SEARCHING FOR NOT FOUND")));
    }

    @Override
    public String verify(User user) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        authenticate.isAuthenticated();
        return jwtService.generateToken(user);
    }

    @Override
    public Page<User> searchUserByName(String username, Pageable pageable) {
        if (username == null || username.isBlank()) {
            return userRepository.findAll(pageable);
        }return userRepository.findByUsernameContainingIgnoreCase(username , pageable);
    }

}
