package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.TaskResponce;
import com.example.demo.entity.Task;

@Component
public class TaskMapper {

    public TaskResponce toResponce(Task task) {
        return new TaskResponce(task.getId(), task.getAuthor().getUsername(), task.getRecipient().getUsername(),
                task.getText());
    }

}
