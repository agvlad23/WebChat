package webchat.demo.service;

import webchat.demo.model.ChatMessageDto;

import java.util.List;

public interface ChatService {

    ChatMessageDto saveMessage(ChatMessageDto chatMessageDto);

    List<ChatMessageDto> getHistory(Integer messageCount);
}
