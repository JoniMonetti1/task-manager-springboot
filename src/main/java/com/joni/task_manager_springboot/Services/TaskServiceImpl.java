package com.joni.task_manager_springboot.Services;

import com.joni.task_manager_springboot.models.Task;
import com.joni.task_manager_springboot.models.TaskStatus;
import com.joni.task_manager_springboot.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public Task markInProgress(Integer taskId) {
        return changeStatus(taskId, TaskStatus.IN_PROGRESS);
    }

    @Override
    @Transactional
    public Task markAsDone(Integer taskId) {
        return changeStatus(taskId, TaskStatus.DONE);
    }

    private Task changeStatus(Integer taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        task.setStatus(status);
        return taskRepository.save(task);
    }
}
