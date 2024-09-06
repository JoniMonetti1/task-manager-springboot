package com.joni.task_manager_springboot.repositories;

import com.joni.task_manager_springboot.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
