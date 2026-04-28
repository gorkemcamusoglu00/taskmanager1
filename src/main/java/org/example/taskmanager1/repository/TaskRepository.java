package org.example.taskmanager1.repository;

import org.example.taskmanager1.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}