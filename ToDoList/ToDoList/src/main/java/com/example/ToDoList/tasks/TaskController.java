package com.example.ToDoList.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskModel>> getAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.getAll(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel newTask, HttpServletRequest request) {
        TaskModel task = service.create(newTask, request);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> upDate(@PathVariable UUID id, @RequestBody TaskModel task) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.upDate(task, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskModel> delete(@PathVariable UUID id) {
        TaskModel task = service.delete(id);

        if (task != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(task);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(task);
    }

}
