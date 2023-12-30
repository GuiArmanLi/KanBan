package com.example.ToDoList.errors;

import java.util.logging.Logger;

public class IncorrectTitleException extends Exception {
    public IncorrectTitleException(String message) {
        Logger.getLogger("Error").warning(message);
    }
}
