package com.example.demo.dto;

public record TaskResponce(Long taskId, String authorUsername, String recipientUsername, String text) {
}
