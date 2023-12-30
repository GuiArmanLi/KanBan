package com.example.ToDoList.tasks;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NonNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.ToDoList.errors.IncorrectTitleException;

@Entity(name = "tTasks")
@Data
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID idUser;

    @Column(length = 30)
    private String title;

    private String describe;

    @NonNull
    private LocalDateTime startAt;

    @NonNull
    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public TaskModel() {
    }

    public void setTitle(String title) throws IncorrectTitleException {
        if (title.length() > 50) {
            throw new IncorrectTitleException("Titulo deve ser menor que 50 caracteres");
        }
        this.title = title;
    }
}
