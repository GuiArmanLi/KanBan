package com.example.ToDoList.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ITask extends JpaRepository<TaskModel, UUID> {
    TaskModel findTaskById(UUID id);

    TaskModel findByTitle(String title);

    List<TaskModel> findAllByIdUser(UUID idUser);
}
