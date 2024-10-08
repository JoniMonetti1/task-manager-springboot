package com.joni.task_manager_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TaskManagerSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerSpringbootApplication.class, args);
    }

}
