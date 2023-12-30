package com.example.ToDoList.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ToDoList.utils.Utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    IUsers users;

    public List<UserModel> getUsers() {
        return users.findAll();
    }

    public UserModel create(UserModel user) {
        UserModel username = users.findByUsername(user.getUsername());

        if (username != null) {
            return null;
        }

        String hashPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hashPassword);
        users.save(user);

        return user;
    }

    public UserModel upDate(UUID id, UserModel user) {
        UserModel taskFound = users.findUserById(id);

        Utils.copyNunNullProperties(user, taskFound);

        users.save(taskFound);
        return taskFound;
    }

    public UserModel delete(UUID id) {
        UserModel userFound = users.findUserById(id);

        users.delete(userFound);

        return userFound;
    }
}
