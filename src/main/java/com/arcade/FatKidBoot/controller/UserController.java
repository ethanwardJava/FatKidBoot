package com.arcade.FatKidBoot.controller;

import com.arcade.FatKidBoot.entity.User;
import com.arcade.FatKidBoot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        return service.findByUserName(username).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/register")
    public ResponseEntity<User> saveNewUser(@RequestBody User user) {
        User addedUser = service.saveNewUser(user);
        URI location = URI.create("/register/" + addedUser.getId());
        return ResponseEntity.created(location).body(addedUser);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = service.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/par/update/{id}")
    public ResponseEntity<User> partialUpdateUser(@PathVariable Long id, @RequestBody User user) {
        User partialUser = service.partialUpdateUser(id, user);
        return ResponseEntity.ok(partialUser);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content :)
    }


}
