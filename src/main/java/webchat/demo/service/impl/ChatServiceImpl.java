package webchat.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import webchat.demo.entities.ChatMessageEntity;
import webchat.demo.mapping.ChatMessageMapper;
import webchat.demo.model.ChatMessageDto;
import webchat.demo.repository.ChatMessageRepository;
import webchat.demo.service.ChatService;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public ChatMessageDto saveMessage(ChatMessageDto chatMessageDto) {
        log.debug("saveMessage message: {}", chatMessageDto);

        ChatMessageEntity chatMessageEntity = chatMessageMapper.toChatMessageEntity(chatMessageDto);
        ChatMessageEntity savedMessage = chatMessageRepository.save(chatMessageEntity);

        ChatMessageDto messageDto = chatMessageMapper.toChatMessageDto(savedMessage);

        log.debug("saveMessage returned message: {}", messageDto);
        return messageDto;
    }

    @Override
    public List<ChatMessageDto> getHistory(Integer messagesCount) {
        log.debug("getHistory messagesCount: {}", messagesCount);

        List<ChatMessageEntity> messageEntityList = chatMessageRepository
                .findAllByOrderByIdDesc(PageRequest.of(0, messagesCount)).stream()
                .sorted(Comparator.comparing(ChatMessageEntity::getDateTime))
                .toList();
        List<ChatMessageDto> messageDtoList = chatMessageMapper.toChatMessageDto(messageEntityList);

        log.debug("getHistory messages List: {}", messageDtoList);
        return messageDtoList;
    }
}