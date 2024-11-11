package com.example.demo.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // creates a new task
    public Task createTask(String recipientUsername, String text) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User authenticatedUser = (org.springframework.security.core.userdetails.User) currentAuth
                .getPrincipal();

        User author = userRepository.findByUsername(authenticatedUser.getUsername()).orElseThrow();
        User recipient = userRepository.findByUsername(recipientUsername).orElseThrow();

        Task task = new Task();
        task.setAuthor(author);
        task.setRecipient(recipient);
        task.setText(text);

        return taskRepository.save(task);
    }

    // gets a list of all tasks if the user has rights to view them (is either
    // author or recepient of the task)
    public List<Task> getTaskListForCurrentUser() {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User authenticatedUser = (org.springframework.security.core.userdetails.User) currentAuth
                .getPrincipal();
        User currentUser = userRepository.findByUsername(authenticatedUser.getUsername()).orElseThrow();
        return Stream.concat(taskRepository.findByAuthorId(currentUser.getId()).stream(),
                taskRepository.findByRecipientId(currentUser.getId()).stream())
                .toList();
    }
}
