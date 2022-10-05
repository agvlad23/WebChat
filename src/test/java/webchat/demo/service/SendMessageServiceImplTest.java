package webchat.demo.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import webchat.demo.service.impl.SendMessageServiceImpl;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = SendMessageServiceImpl.class)
class SendMessageServiceImplTest {
    @Autowired
    SendMessageService sendMessageService;
    @MockBean
    SimpMessagingTemplate messagingTemplate;

    @Test
    void when_then() {
        String topic = "topic";
        Object o = new Object();

        sendMessageService.sendMessage(topic, o);

        verify(messagingTemplate, Mockito.times(1))
                .convertAndSend(topic, o);
    }
}
