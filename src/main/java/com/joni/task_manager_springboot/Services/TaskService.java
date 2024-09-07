package com.joni.task_manager_springboot.Services;

import com.joni.task_manager_springboot.models.Task;

import java.util.List;

public interface TaskService {
    Task markInProgress(Integer taskId);
    Task markAsDone(Integer taskId);
    List<Task> GetTasksByStatus(String status);
}
