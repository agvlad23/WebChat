package webchat.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import webchat.demo.service.SendMessageService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMessageServiceImpl implements SendMessageService {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendMessage(String topic, Object o) {
        log.debug("sendMessage invoked topic: {}, message {}", topic, o);

        messagingTemplate.convertAndSend(topic, o);

        log.debug("sendMessage sent message: {}", o);
    }
}
