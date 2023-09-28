package samdasu.recipt.api.gpt.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import samdasu.recipt.api.gpt.controller.dto.chat.Message;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Conversation {
    private List<Message> conversation = new ArrayList<>();

    public List<Message> getConversation() {
        return conversation;
    }

    public void clearConversation() {
        conversation.clear();
    }

    public void add(Message message) {
        conversation.add(message);
    }

    public int size() {
        return conversation.size();
    }
}