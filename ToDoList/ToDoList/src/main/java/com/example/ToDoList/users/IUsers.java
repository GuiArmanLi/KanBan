package com.example.ToDoList.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface IUsers extends JpaRepository<UserModel, UUID> {
    UserModel findUserById(UUID id);

    UserModel findByUsername(String username);
}
