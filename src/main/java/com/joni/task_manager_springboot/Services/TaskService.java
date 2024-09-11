package com.joni.task_manager_springboot.Services;

import com.joni.task_manager_springboot.models.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {

    ResponseEntity<List<Task>> getTasks();

    ResponseEntity<Task> getOneTask(Integer taskId);

    ResponseEntity<List<Task>> getTasksByStatus(String status);

    ResponseEntity<Task> createTask(Task task);

    ResponseEntity<Task> modifyTask(Integer taskId, Task updates);

    ResponseEntity<?> deleteTask(Integer taskId);

    ResponseEntity<Task> markInProgress(Integer taskId);

    ResponseEntity<Task> markAsDone(Integer taskId);

}
