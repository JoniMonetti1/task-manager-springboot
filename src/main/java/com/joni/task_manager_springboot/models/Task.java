package com.joni.task_manager_springboot.models;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private Date createdAt;
    private Date updatedAt;
}
