package org.example.taskmanager1.controller;

import jakarta.validation.Valid;
import org.example.taskmanager1.dto.TaskRequest;
import org.example.taskmanager1.entity.Task;
import org.example.taskmanager1.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        Task createdTask = taskService.createTask(taskRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        Task updatedTask = taskService.updateTask(id, taskRequest);

        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);

        if (deleted) {
            return ResponseEntity.ok("Görev silindi.");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/completed/{completed}")
    public List<Task> getTasksByCompletedStatus(@PathVariable boolean completed) {
        return taskService.getTasksByCompletedStatus(completed);
    }

    @GetMapping("/search")
    public List<Task> searchTasksByTitle(@RequestParam String title) {
        return taskService.searchTasksByTitle(title);
    }

    @GetMapping("/filter")
    public List<Task> filterTasks(
            @RequestParam String title,
            @RequestParam boolean completed
    ) {
        return taskService.filterTasksByTitleAndCompleted(title, completed);
    }

    @GetMapping("/count")
    public long countTasksByCompletedStatus(@RequestParam boolean completed) {
        return taskService.countTasksByCompletedStatus(completed);
    }

    @GetMapping("/exists")
    public boolean existsTaskByTitle(@RequestParam String title) {
        return taskService.existsTaskByTitle(title);
    }

    @GetMapping("/latest")
    public List<Task> getLatestFiveTasks() {
        return taskService.getLatestFiveTasks();
    }
}