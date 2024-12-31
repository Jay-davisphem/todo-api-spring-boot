package com.example.todo_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    // Get all ToDo items
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ToDo>>> getAllToDos(Pageable pageable, PagedResourcesAssembler<ToDo> assembler) {
        Page<ToDo> todos = toDoRepository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(todos));
    }

    // Get a single ToDo item by ID
    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable Long id) {
        return toDoRepository.findById(id)
                .map(todo -> ResponseEntity.ok().body(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new ToDo item
    @PostMapping
    public ToDo createToDo(@Valid @RequestBody ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    // Update an existing ToDo item
    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo toDoDetails) {
        return toDoRepository.findById(id)
                .map(todo -> {
                    todo.setTitle(toDoDetails.getTitle());
                    todo.setCompletion(toDoDetails.isCompleted());
                    ToDo updatedToDo = toDoRepository.save(todo);
                    return ResponseEntity.ok().body(updatedToDo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a ToDo item
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteToDo(@PathVariable Long id) {
        return toDoRepository.findById(id)
                .map(todo -> {
                    toDoRepository.delete(todo);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public List<ToDo> searchToDos(@RequestParam String title) {
        return toDoRepository.searchByTitle(title);
    }

    @PutMapping("/mark-all-completed")
    public ResponseEntity<Void> markAllAsCompleted() {
        List<ToDo> todos = toDoRepository.findAll();
        todos.forEach(todo -> todo.setCompletion(true));
        toDoRepository.saveAll(todos);
        return ResponseEntity.noContent().build();
    }

}
