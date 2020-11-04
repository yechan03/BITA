package com.david0926.scon.screen.chat;

import com.david0926.scon.model.UserModel;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

public class ChatModel {

    public static Map<String, Object> toMap(ChatModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        return gson.fromJson(json, (Type) Map.class);
    }

    public static ChatModel fromMap(Map<String, Object> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return gson.fromJson(json, ChatModel.class);
    }

    public ChatModel() {
    }

    private String name, email, profile, message, time;

    public ChatModel(UserModel user, String message, String time) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.profile = user.getProfile();
        this.message = message;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
