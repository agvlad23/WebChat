package webchat.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import webchat.demo.entities.ChatMessageEntity;
import webchat.demo.mapping.ChatMessageMapper;
import webchat.demo.model.ChatMessageDto;
import webchat.demo.repository.ChatMessageRepository;
import webchat.demo.service.impl.ChatServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest(classes = ChatServiceImpl.class)
class ChatServiceImplTest {
    private static final Integer MESSAGES_COUNT = 11;
    private ChatMessageDto chatMessageDto;

    @Autowired
    ChatService chatService;
    @MockBean
    ChatMessageMapper chatMessageMapper;
    @MockBean
    ChatMessageRepository chatMessageRepository;

    @BeforeEach
    void setUp() {
        chatMessageDto = ChatMessageDto.builder()
                .content("message content")
                .type(ChatMessageDto.MessageType.CHAT)
                .sender("MessageSender").build();
    }

    @Test
    void whenSaveMessage_thenReturnMessageDto() {
        ChatMessageEntity chatMessageEntityMapper = new ChatMessageEntity();
        ChatMessageEntity chatMessageEntityRepo = new ChatMessageEntity();
        ChatMessageDto chatMessageDtoInput = ChatMessageDto.builder().build();

        Mockito
                .when(chatMessageMapper.toChatMessageEntity(chatMessageDtoInput))
                .thenReturn(chatMessageEntityMapper);
        Mockito
                .when(chatMessageRepository.save(chatMessageEntityMapper))
                .thenReturn(chatMessageEntityRepo);
        Mockito
                .when(chatMessageMapper.toChatMessageDto(chatMessageEntityRepo))
                .thenReturn(chatMessageDto);

        ChatMessageDto result = chatService.saveMessage(chatMessageDtoInput);

        assertEquals(chatMessageDto, result);
    }

    @Test
    void whenGetHistory_thenReturnChatMessageList() {
        PageRequest pageRequest = PageRequest.of(0, MESSAGES_COUNT);
        List<ChatMessageEntity> messageEntityList = List.of(new ChatMessageEntity());
        List<ChatMessageDto> messageDtoList = List.of(chatMessageDto);

        Mockito
                .when(chatMessageRepository.findAllByOrderByIdDesc(pageRequest))
                .thenReturn(messageEntityList);
        Mockito
                .when(chatMessageMapper.toChatMessageDto(messageEntityList))
                .thenReturn(messageDtoList);

        List<ChatMessageDto> result = chatService.getHistory(MESSAGES_COUNT);

        assertIterableEquals(messageDtoList, result);
    }
}
