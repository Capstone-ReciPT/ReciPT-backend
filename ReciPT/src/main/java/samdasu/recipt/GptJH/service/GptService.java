package samdasu.recipt.GptJH.service;

import samdasu.recipt.GptJH.dto.chat.ChatMessage;
import samdasu.recipt.GptJH.dto.chat.ChatRequest;
import samdasu.recipt.GptJH.dto.chat.ChatResponse;

import java.util.List;

public interface GptService {

    ChatMessage Chat(List<ChatMessage> messages);

    ChatResponse ChatRequest(ChatRequest multiChatRequest);

    /**
     * @param prompt A text description of the desired image(s). The maximum length is 1000 characters.
     * @return generated image url
     */

}
