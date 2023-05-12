package samdasu.recipt.chatgpt.service;

import samdasu.recipt.chatgpt.dto.ChatRequest;
import samdasu.recipt.chatgpt.dto.ChatResponse;
import samdasu.recipt.chatgpt.dto.chat.MultiChatMessage;
import samdasu.recipt.chatgpt.dto.chat.MultiChatRequest;
import samdasu.recipt.chatgpt.dto.chat.MultiChatResponse;

import java.util.List;

public interface ChatgptService {

    String sendMessage(String message);

    ChatResponse sendChatRequest(ChatRequest request);

    String multiChat(List<MultiChatMessage> messages);

    MultiChatResponse multiChatRequest(MultiChatRequest multiChatRequest);

    /**
     * @param prompt A text description of the desired image(s). The maximum length is 1000 characters.
     * @return generated image url
     */

}
