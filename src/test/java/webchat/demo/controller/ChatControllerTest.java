package webchat.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import webchat.demo.model.ChatMessageDto;
import webchat.demo.service.ActiveUserService;
import webchat.demo.service.ChatService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = ChatController.class,
        properties = "chat.history.count=3")
class ChatControllerTest {

    private ChatMessageDto chatMessageDto;

    @Value("${chat.history.count}")
    private Integer chatHistoryCount;

    @Autowired
    ChatController chatController;
    @MockBean
    ChatService chatService;
    @MockBean
    ActiveUserService activeUserService;
    @Mock
    SimpMessageHeaderAccessor headerAccessor;

    @BeforeEach
    void setUp() {
        chatMessageDto = ChatMessageDto.builder()
                .content("message content")
                .type(ChatMessageDto.MessageType.CHAT)
                .sender("MessageSender").build();
    }

    @Test
    void whenSendMessage_thenReturnChatMessageDto() {
        ChatMessageDto returnedMessage = ChatMessageDto.builder().build();

        Mockito
                .when(chatService.saveMessage(chatMessageDto))
                .thenReturn(returnedMessage);

        ChatMessageDto result = chatController.sendMessage(chatMessageDto);

        assertSame(returnedMessage, result);
        verify(chatService, Mockito.times(1))
                .saveMessage(chatMessageDto);
    }

    @Test
    void whenAddUser_thenReturnChatMessageDto() {
        ChatMessageDto returnedMessage = ChatMessageDto.builder().build();

        Mockito
                .when(chatService.saveMessage(chatMessageDto))
                .thenReturn(returnedMessage);

        ChatMessageDto result = chatController.addUser(chatMessageDto, headerAccessor);

        assertSame(returnedMessage, result);
        verify(chatService, Mockito.times(1))
                .saveMessage(chatMessageDto);
        verify(activeUserService, Mockito.times(1))
                .addUser(chatMessageDto.getSender());
    }

    @Test
    void whenGetHistory_thenReturnChatMessageList() {
        List<ChatMessageDto> messageDtoList = List.of(chatMessageDto);

        Mockito
                .when(chatService.getHistory(chatHistoryCount))
                .thenReturn(messageDtoList);

        List<ChatMessageDto> result = chatController.getHistory();

        assertSame(messageDtoList, result);
        verify(chatService, Mockito.times(1))
                .getHistory(chatHistoryCount);
    }
}
