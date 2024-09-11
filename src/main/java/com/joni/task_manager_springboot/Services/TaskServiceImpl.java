package com.joni.task_manager_springboot.Services;

import com.joni.task_manager_springboot.models.Task;
import com.joni.task_manager_springboot.models.TaskStatus;
import com.joni.task_manager_springboot.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<Task> markInProgress(Integer taskId) {
        Task updatedTask = changeStatus(taskId, TaskStatus.IN_PROGRESS);
        return ResponseEntity.ok(updatedTask);
    }

    @Override
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = taskRepository.findAll();

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<Task> getOneTask(Integer taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        return optionalTask.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<Task> markAsDone(Integer taskId) {
        Task updatedTask = changeStatus(taskId, TaskStatus.DONE);
        return ResponseEntity.ok(updatedTask);
    }

    @Override
    public ResponseEntity<List<Task>> getTasksByStatus(String status) {
        TaskStatus taskStatus;
        try {
            taskStatus = TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        List<Task> tasks = taskRepository.findByStatus(taskStatus);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<Task> createTask(Task task) {
        Task savedTask = taskRepository.save(task);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedTask);
    }

    @Override
    public ResponseEntity<Task> modifyTask(Integer taskId, Task updates) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            updates.setId(taskId);
            Task updatedTask = taskRepository.save(updates);
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> deleteTask(Integer taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            taskRepository.deleteById(taskId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Task changeStatus(Integer taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        task.setStatus(status);
        return taskRepository.save(task);
    }
}
