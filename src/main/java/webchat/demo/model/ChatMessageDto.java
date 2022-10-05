package webchat.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageDto {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
