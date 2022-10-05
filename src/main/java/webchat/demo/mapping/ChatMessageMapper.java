package webchat.demo.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import webchat.demo.entities.ChatMessageEntity;
import webchat.demo.model.ChatMessageDto;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(config = MapperConfig.class,
        imports = LocalDateTime.class)
public interface ChatMessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateTime", expression = "java(LocalDateTime.now())")
    ChatMessageEntity toChatMessageEntity(ChatMessageDto chatMessageDto);

    ChatMessageDto toChatMessageDto(ChatMessageEntity chatMessage);

    List<ChatMessageDto> toChatMessageDto(List<ChatMessageEntity> chatMessage);
}
