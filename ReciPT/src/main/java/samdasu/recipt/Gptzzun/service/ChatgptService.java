package samdasu.recipt.Gptzzun.service;

import samdasu.recipt.Gptzzun.dto.chat.MultiChatMessage;
import samdasu.recipt.Gptzzun.dto.chat.MultiChatRequest;
import samdasu.recipt.Gptzzun.dto.chat.MultiChatResponse;

import java.util.List;

public interface ChatgptService {

    String multiChat(List<MultiChatMessage> conversation);

    MultiChatResponse multiChatRequest(MultiChatRequest multiChatRequest);
}
