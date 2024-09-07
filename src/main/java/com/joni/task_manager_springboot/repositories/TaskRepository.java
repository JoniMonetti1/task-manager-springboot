package com.joni.task_manager_springboot.repositories;

import com.joni.task_manager_springboot.models.Task;
import com.joni.task_manager_springboot.models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByStatus(TaskStatus taskStatus);
}
