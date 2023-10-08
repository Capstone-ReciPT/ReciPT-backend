package samdasu.recipt.api.gpt.service;

import samdasu.recipt.api.gpt.controller.dto.chat.Message;
import samdasu.recipt.api.gpt.controller.dto.chat.Request;
import samdasu.recipt.api.gpt.controller.dto.chat.Response;

import java.util.List;

public interface ChatGptService {

    String getResponse(List<Message> conversation);

    Response sendRequest(Request multiChatRequest);
}
