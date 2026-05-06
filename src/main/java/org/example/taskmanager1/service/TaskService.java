package org.example.taskmanager1.service;

import org.example.taskmanager1.dto.TaskRequest;
import org.example.taskmanager1.entity.Task;
import org.example.taskmanager1.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());

        if (taskRequest.getCompleted() != null) {
            task.setCompleted(taskRequest.getCompleted());
        } else {
            task.setCompleted(false);
        }

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, TaskRequest taskRequest) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();

            existingTask.setTitle(taskRequest.getTitle());
            existingTask.setDescription(taskRequest.getDescription());

            if (taskRequest.getCompleted() != null) {
                existingTask.setCompleted(taskRequest.getCompleted());
            }

            return taskRepository.save(existingTask);
        }

        return null;
    }

    public boolean deleteTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            taskRepository.deleteById(id);
            return true;
        }

        return false;
    }
}