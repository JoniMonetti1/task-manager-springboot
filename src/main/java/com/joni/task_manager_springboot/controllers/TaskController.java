package com.joni.task_manager_springboot.controllers;


import com.joni.task_manager_springboot.Services.TaskService;
import com.joni.task_manager_springboot.models.Task;
import com.joni.task_manager_springboot.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return taskService.getTasks();
    }


    @CrossOrigin
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getOneTask(@PathVariable Integer taskId) {
        return taskService.getOneTask(taskId);
    }


    @CrossOrigin
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        return taskService.getTasksByStatus(status);
    }


    @CrossOrigin
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }


    @CrossOrigin
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> modifyTask(@PathVariable Integer taskId, @RequestBody Task updates) {
        return taskService.modifyTask(taskId, updates);
    }


    @CrossOrigin
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer taskId) {
        return taskService.deleteTask(taskId);
    }


    @PutMapping("/{taskId}/mark-in-progress")
    public ResponseEntity<Task> markInProgress(@PathVariable Integer taskId) {
        return taskService.markInProgress(taskId);
    }


    @PutMapping("/{taskId}/mark-done")
    public ResponseEntity<Task> markCompleted(@PathVariable Integer taskId) {
        return taskService.markAsDone(taskId);
    }
}
