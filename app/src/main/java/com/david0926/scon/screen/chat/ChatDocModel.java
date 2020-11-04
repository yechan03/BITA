package com.david0926.scon.screen.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatDocModel {

    private List<HashMap<String, Object>> messages;

    public List<ChatModel> getChatModels() {
        List<ChatModel> chatModels = new ArrayList<>();
        if (messages == null) return chatModels;

        for (HashMap<String, Object> m : messages) {
            chatModels.add(ChatModel.fromMap(m));
        }
        return chatModels;
    }

    public ChatDocModel() {
    }

    public ChatDocModel(List<HashMap<String, Object>> messages) {
        this.messages = messages;
    }

    public List<HashMap<String, Object>> getMessages() {
        return messages;
    }

    public void setMessages(List<HashMap<String, Object>> messages) {
        this.messages = messages;
    }
}
