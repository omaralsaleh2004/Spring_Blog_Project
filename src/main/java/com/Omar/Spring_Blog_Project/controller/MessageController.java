package com.Omar.Spring_Blog_Project.controller;

import com.Omar.Spring_Blog_Project.dto.*;
import com.Omar.Spring_Blog_Project.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/chat/{otherUserId}")
    public ResponseEntity<ApiResponse<PaginatedMessageResponse>> getChat(@PathVariable int otherUserId , @RequestParam(defaultValue = "0") int page) {
        PaginatedMessageResponse paginatedMessageResponse = messageService.getChat(otherUserId , page);
        ApiResponse<PaginatedMessageResponse> response = new ApiResponse<>(
                "Chat fetched Successfully",
                paginatedMessageResponse
        );
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/chat/{messageId}")
    public ResponseEntity<ApiResponse<MessageResponse>> markAsRead (@PathVariable int messageId){
        MessageResponse messageResponse = messageService.markAsRead(messageId);
        ApiResponse<MessageResponse> response = new ApiResponse<>(
                "Message Marked as Read",
                messageResponse
        );
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/chat/{messageId}")
    public ResponseEntity<ApiResponse<MessageResponse>> editMessage (@RequestBody EditMessageRequest request, @PathVariable int messageId) {
        MessageResponse messageResponse = messageService.editMessage(request,messageId);
        ApiResponse<MessageResponse> response = new ApiResponse<>(
                "Message Edited Successfully",
                messageResponse
        );
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/chat/{messageId}")
    public ResponseEntity<ApiResponse<String>> deleteMessage (@PathVariable int messageId) {
        messageService.deleteMessage(messageId);
        ApiResponse<String> response = new ApiResponse<>(
                "Message Deleted Successfully",
                null
        );
        return ResponseEntity.ok().body(response);
    }

}
