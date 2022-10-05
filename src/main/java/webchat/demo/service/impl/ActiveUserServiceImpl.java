package webchat.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webchat.demo.service.ActiveUserService;
import webchat.demo.service.SendMessageService;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveUserServiceImpl implements ActiveUserService {
    private final SendMessageService sendMessageService;

    private final Set<String> activeUsers = new HashSet<>();
    private static final String TOPIC_ACTIVE_USERS = "/topic/activeUsers";

    public void addUser(String user) {
        log.debug("addUser user: {}", user);

        activeUsers.add(user);
        sendMessageService.sendMessage(TOPIC_ACTIVE_USERS, activeUsers);

        log.debug("addUser added user: {}", user);
    }

    public void removeUser(String user) {
        log.debug("removeUser user: {}", user);

        activeUsers.remove(user);
        sendMessageService.sendMessage(TOPIC_ACTIVE_USERS, activeUsers);

        log.debug("removeUser removed user: {}", user);
    }
}