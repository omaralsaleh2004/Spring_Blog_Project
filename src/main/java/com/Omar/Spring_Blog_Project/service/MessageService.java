package com.Omar.Spring_Blog_Project.service;

import com.Omar.Spring_Blog_Project.dto.MessageResponse;
import com.Omar.Spring_Blog_Project.dto.SendMessageRequest;
import com.Omar.Spring_Blog_Project.exception.BadRequest;
import com.Omar.Spring_Blog_Project.exception.NotFoundException;
import com.Omar.Spring_Blog_Project.exception.UnauthorizedException;
import com.Omar.Spring_Blog_Project.model.Message;
import com.Omar.Spring_Blog_Project.model.User;
import com.Omar.Spring_Blog_Project.repo.MessageRepo;
import com.Omar.Spring_Blog_Project.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final UserRepo userRepo;
    private final MessageRepo messageRepo;
    private final AuthService authService;

    private MessageResponse mapToResponse(Message message) {
        MessageResponse response = new MessageResponse();

        response.setId(message.getId());
        response.setContent(message.getContent());
        response.setCreatedAt(message.getCreatedAt());
        response.setRead(message.isRead());

        response.setSenderId(message.getSender().getId());
        response.setSenderName(message.getSender().getFirstName() + " "+message.getSender().getLastName());
        response.setSenderAvatar("/profile/"+message.getSender().getId()+"image");

        response.setReceiverId(message.getReceiver().getId());
        response.setReceiverName(message.getReceiver().getFirstName() + " "+message.getReceiver().getLastName());

        return response;
    }

    public MessageResponse sendMessage(SendMessageRequest request) {
        User sender = authService.getCurrentUser();

        if(sender == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        if(request.receiverId() == null) {
            throw new BadRequest("Receiver id cannot be null");
        }

        User receiver = userRepo.findById(request.receiverId()).orElseThrow(() -> new NotFoundException("Receiver not found"));

        if(request.content().isBlank()) {
           throw new BadRequest("Message content cannot be empty");
        }

        Message message = new Message();
        message.setRead(false);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(request.content());
        message.setCreatedAt(LocalDateTime.now());

       Message savedMessage = messageRepo.save(message);

       return mapToResponse(savedMessage);
    }
}
