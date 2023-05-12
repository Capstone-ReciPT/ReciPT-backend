package samdasu.recipt.Gptzzun.service;

import samdasu.recipt.Gptzzun.dto.chat.MultiChatMessage;
import samdasu.recipt.Gptzzun.dto.chat.MultiChatRequest;
import samdasu.recipt.Gptzzun.dto.chat.MultiChatResponse;

import java.util.List;

public interface ChatgptService {

    String multiChat(List<MultiChatMessage> messages);

    MultiChatResponse multiChatRequest(MultiChatRequest multiChatRequest);

    /**
     * @param prompt A text description of the desired image(s). The maximum length is 1000 characters.
     * @return generated image url
     */

}
