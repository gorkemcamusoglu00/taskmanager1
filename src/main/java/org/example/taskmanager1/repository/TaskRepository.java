package org.example.taskmanager1.repository;

import org.example.taskmanager1.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompleted(boolean completed);

    List<Task> findByTitleContainingIgnoreCase(String title);

    List<Task> findByTitleContainingIgnoreCaseAndCompleted(String title, boolean completed);

    long countByCompleted(boolean completed);

    boolean existsByTitleIgnoreCase(String title);

    List<Task> findTop5ByOrderByIdDesc();
}