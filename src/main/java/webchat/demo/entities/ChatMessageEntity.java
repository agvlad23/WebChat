package webchat.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import webchat.demo.model.ChatMessageDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "messages")
@ToString
@NoArgsConstructor
public class ChatMessageEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "date")
    private LocalDateTime dateTime;

    @Column(nullable = false, name = "type")
    private ChatMessageDto.MessageType type;

    @Column(name = "content")
    private String content;

    @Column(nullable = false, name = "sender")
    private String sender;
}
