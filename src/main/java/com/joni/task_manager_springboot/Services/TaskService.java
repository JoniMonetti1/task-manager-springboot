package com.joni.task_manager_springboot.Services;

import com.joni.task_manager_springboot.models.Task;

public interface TaskService {
    Task markInProgress(Integer taskId);
    Task markAsDone(Integer taskId);
}
