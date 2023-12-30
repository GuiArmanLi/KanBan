package com.example.ToDoList.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import com.example.ToDoList.utils.Utils;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    ITask tasks;

    public List<TaskModel> getAll(HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");

        List<TaskModel> listTasks = new ArrayList<>();
        for (TaskModel task : tasks.findAll()) {
            if (idUser.equals(task.getIdUser())) {
                listTasks.add(task);
            }
        }

        return listTasks;
    }

    public TaskModel getById(UUID id) {
        return tasks.findTaskById(id);
    }

    public TaskModel create(TaskModel task, HttpServletRequest request) {
        TaskModel taskFound = tasks.findByTitle(task.getTitle());

        UUID idUser = (UUID) request.getAttribute("idUser");
        task.setIdUser(idUser);

        if (taskFound != null) {
            Logger.getLogger("Error").info("Tarefa já existe!");
            return null;
        }

        if (task.getStartAt().isAfter(task.getEndAt())) {
            Logger.getLogger("Erro").info("Inicio depois do Fim!");
            return null;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        if (task.getStartAt().isBefore(currentDate) || task.getEndAt().isBefore(currentDate)) {
            Logger.getLogger("Error").info("Inicio deve ser maior que agora!");
            return null;
        }

        return tasks.save(task);
    }

    public TaskModel upDate(TaskModel task, UUID id) {
        TaskModel taskFound = tasks.findTaskById(id);

        Utils.copyNunNullProperties(task, taskFound);

        tasks.save(taskFound);
        return taskFound;
    }

    public TaskModel delete(UUID id) {
        TaskModel task = tasks.findTaskById(id);
        tasks.delete(task);

        return tasks.findTaskById(id);
    }
}
