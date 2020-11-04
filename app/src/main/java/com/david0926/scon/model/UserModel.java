package com.david0926.scon.model;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

public class UserModel {

    public static Map<String, Object> toMap(UserModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        return gson.fromJson(json, (Type) Map.class);
    }

    public static UserModel fromMap(Map<String, Object> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return gson.fromJson(json, UserModel.class);
    }

    public UserModel() {
    }

    private String name, email, time, profile, introduce;

    public UserModel(String name, String email, String profile) {
        this.name = name;
        this.email = email;
        this.profile = profile;
        time = "";
        introduce = "";
    }

    public UserModel(String name, String email, String time, String profile, String introduce) {
        this.name = name;
        this.email = email;
        this.time = time;
        this.profile = profile;
        this.introduce = introduce;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
