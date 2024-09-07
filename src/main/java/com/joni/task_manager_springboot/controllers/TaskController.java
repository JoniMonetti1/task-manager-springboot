package com.joni.task_manager_springboot.controllers;


import com.joni.task_manager_springboot.Services.TaskService;
import com.joni.task_manager_springboot.models.Task;
import com.joni.task_manager_springboot.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    // Inject TaskRepository to access the database operations
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;


    // Add methods for CRUD operations on tasks

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = taskRepository.findAll();

        if (tasks.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }


    @CrossOrigin
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getOneTask(@PathVariable Integer taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        return optionalTask.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @CrossOrigin
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedTask);
    }


    @CrossOrigin
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> modifyTask(@PathVariable Integer taskId, @RequestBody Task updates) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            updates.setId(taskId);
            Task updatedTask = taskRepository.save(updates);
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }


    @CrossOrigin
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            taskRepository.deleteById(taskId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{taskId}/mark-in-progress")
    public ResponseEntity<Task> markInProgress(@PathVariable Integer taskId) {
        Task updatedTask = taskService.markInProgress(taskId);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/{taskId}/mark-done")
    public ResponseEntity<Task> markCompleted(@PathVariable Integer taskId) {
        Task updatedTask = taskService.markAsDone(taskId);
        return ResponseEntity.ok(updatedTask);
    }
}
