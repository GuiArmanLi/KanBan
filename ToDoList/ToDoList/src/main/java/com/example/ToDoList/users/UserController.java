package com.example.ToDoList.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.getUsers());
    }

    @PostMapping
    public ResponseEntity<UserModel> create(@RequestBody UserModel user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> upDate(@PathVariable UUID id, @RequestBody UserModel user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.upDate(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserModel> delete(@PathVariable UUID id) {
        UserModel user = service.delete(id);

        if (user != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
    }
}
