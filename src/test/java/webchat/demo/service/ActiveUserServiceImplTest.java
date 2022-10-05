package webchat.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webchat.demo.service.impl.ActiveUserServiceImpl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = ActiveUserServiceImpl.class)
class ActiveUserServiceImplTest {
    private static final String TOPIC_ACTIVE_USERS = "/topic/activeUsers";

    private String userName;

    @Autowired
    ActiveUserService activeUserService;
    @MockBean
    SendMessageService sendMessageService;

    @BeforeEach
    void setUp() {
        userName = "userName";
    }

    @Test
    void whenAddUser_andOk() {
        activeUserService.addUser(userName);

        verify(sendMessageService, Mockito.times(1))
                .sendMessage(eq(TOPIC_ACTIVE_USERS), Mockito.anySet());
    }

    @Test
    void whenRemoveUser_andOk() {
        activeUserService.removeUser(userName);

        verify(sendMessageService, Mockito.times(1))
                .sendMessage(eq(TOPIC_ACTIVE_USERS), Mockito.anySet());
    }
}
