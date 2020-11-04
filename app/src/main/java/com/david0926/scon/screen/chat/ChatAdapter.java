package com.david0926.scon.screen.chat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.david0926.scon.databinding.RowChatReceiveBinding;
import com.david0926.scon.databinding.RowChatSendBinding;
import com.david0926.scon.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    // TODO: better approach? - https://thdev.tech/android/2018/01/31/Recycler-Adapter-Distinguish/

    private static final int CHAT_RECEIVE = 0;
    private static final int CHAT_SEND = 1;

    public static class ChatHolder extends RecyclerView.ViewHolder {
        private RowChatReceiveBinding receiveBinding;
        private RowChatSendBinding sendBinding;

        public ChatHolder(RowChatReceiveBinding binding) {
            super(binding.getRoot());
            receiveBinding = binding;
        }

        public ChatHolder(RowChatSendBinding binding) {
            super(binding.getRoot());
            sendBinding = binding;
        }

    }

    private List<ChatModel> chatModels;
    private UserModel userModel;

    public ChatAdapter(UserModel userModel) {
        chatModels = new ArrayList<>();
        this.userModel = userModel;
    }

    public void setChatModels(ObservableArrayList<ChatModel> chatModels) {
        this.chatModels = chatModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case CHAT_RECEIVE:
                return new ChatHolder(RowChatReceiveBinding.inflate(layoutInflater, parent, false));
            case CHAT_SEND:
                return new ChatHolder(RowChatSendBinding.inflate(layoutInflater, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case CHAT_RECEIVE:
                holder.receiveBinding.setChatModel(chatModels.get(position));
                break;
            case CHAT_SEND:
                holder.sendBinding.setChatModel(chatModels.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (chatModels.get(position).getEmail() == null) return CHAT_SEND;
        if (chatModels.get(position).getEmail().equals(userModel.getEmail())) return CHAT_SEND;
        else return CHAT_RECEIVE;
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }


}
