package com.example.todo_api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    @Query("SELECT t FROM ToDo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<ToDo> searchByTitle(@Param("title") String title);
}
