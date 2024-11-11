package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskResponce;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @CrossOrigin
    @PostMapping
    public TaskResponce createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponce task = taskMapper.toResponce(taskService.createTask(
                taskRequest.recipientUsername(),
                taskRequest.text()));
        return task;
    }

    // returns all tasks where the current authenticated user is either an author or
    // a recepient
    @CrossOrigin
    @GetMapping
    public List<TaskResponce> getTasksForCurrentUser() {
        return taskService.getTaskListForCurrentUser().stream().map(task -> taskMapper.toResponce(task)).toList();
    }
}