package webchat.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import webchat.demo.model.ChatMessageDto;
import webchat.demo.service.ActiveUserService;
import webchat.demo.service.ChatService;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ActiveUserService activeUsersService;
    private final ChatService chatService;

    @Value("${chat.history.count}")
    private Integer historyCount;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessageDto) {
        log.trace("sendMessage message: {}", chatMessageDto);

        ChatMessageDto messageDto = chatService.saveMessage(chatMessageDto);

        log.trace("sendMessage returned message: {}", messageDto);
        return messageDto;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDto addUser(@Payload ChatMessageDto chatMessageDto, SimpMessageHeaderAccessor headerAccessor) {
        log.trace("addUser message: {}", chatMessageDto);

        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessageDto.getSender());
        activeUsersService.addUser(chatMessageDto.getSender());

        ChatMessageDto messageDto = chatService.saveMessage(chatMessageDto);

        log.trace("addUser returned message: {}", messageDto);
        return messageDto;
    }

    @GetMapping("/chat.history")
    public List<ChatMessageDto> getHistory() {
        log.trace("getHistory invoked");

        List<ChatMessageDto> history = chatService.getHistory(historyCount);

        log.trace("getHistory returned messages: {}", history);
        return history;
    }
}
