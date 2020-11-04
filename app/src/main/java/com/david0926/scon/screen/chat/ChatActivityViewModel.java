package com.david0926.scon.screen.chat;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivityViewModel extends ViewModel {

    @BindingAdapter("bindToolbarNavigationClick")
    public static void bindToolbarIconClick(Toolbar t, Runnable r) {
        t.setNavigationOnClickListener(v -> r.run());
    }

    @BindingAdapter("bindChatModels")
    public static void bindChatModels(RecyclerView r, ObservableArrayList<ChatModel> models) {
        ChatAdapter adapter = (ChatAdapter) r.getAdapter();
        if (adapter != null) adapter.setChatModels(models);
    }

    public ObservableArrayList<ChatModel> chatModels = new ObservableArrayList<>();

    public MutableLiveData<String> message = new MutableLiveData<>("");


}
