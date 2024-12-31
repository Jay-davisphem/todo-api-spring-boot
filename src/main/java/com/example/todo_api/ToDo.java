package com.example.todo_api;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private boolean completed;

    // Constructors
    public ToDo() {
    }

    public ToDo(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompletion(boolean completion) {
        this.completed = completion;
    }

    
}
