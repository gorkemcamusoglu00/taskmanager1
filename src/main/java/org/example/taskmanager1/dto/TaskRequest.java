package org.example.taskmanager1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class TaskRequest {

    @NotBlank(message = "Başlık boş bırakılamaz.")
    @Size(min = 3, max = 100, message = "Başlık 3 ile 100 karakter arasında olmalıdır.")
    private String title;

    @Size(max = 500, message = "Açıklama en fazla 500 karakter olabilir.")
    private String description;

    private Boolean completed;

    public TaskRequest() {
    }

    public TaskRequest(String title, String description, Boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }
    //private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}