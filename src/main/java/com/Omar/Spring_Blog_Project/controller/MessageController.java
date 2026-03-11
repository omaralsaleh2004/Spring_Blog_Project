package com.Omar.Spring_Blog_Project.controller;

import com.Omar.Spring_Blog_Project.dto.ApiResponse;
import com.Omar.Spring_Blog_Project.dto.MessageResponse;
import com.Omar.Spring_Blog_Project.dto.SendMessageRequest;
import com.Omar.Spring_Blog_Project.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<MessageResponse>> sendMessage(@RequestBody SendMessageRequest request) {
        MessageResponse messageResponse = messageService.sendMessage(request);
        ApiResponse<MessageResponse> response = new ApiResponse<>(
                "Message Sent Successfully",
                messageResponse
        );
        return ResponseEntity.ok().body(response);
    }
}
