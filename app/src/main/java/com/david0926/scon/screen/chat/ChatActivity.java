package com.david0926.scon.screen.chat;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.david0926.scon.R;
import com.david0926.scon.databinding.ActivityChatBinding;
import com.david0926.scon.util.LinearLayoutManagerWrapper;
import com.david0926.scon.util.UserCache;

import gun0912.tedkeyboardobserver.TedKeyboardObserver;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ChatActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new ChatActivityClickHandler());

        viewModel = new ViewModelProvider(this).get(ChatActivityViewModel.class);
        binding.setViewModel(viewModel);

        binding.recyclerChat.setLayoutManager(new LinearLayoutManagerWrapper(
                this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerChat.setAdapter(new ChatAdapter(UserCache.getUser(this)));

        FirebaseObserveChat.observeMessage(getResources(),
                chatDoc -> {
                    viewModel.chatModels.clear();
                    viewModel.chatModels.addAll(chatDoc.getChatModels());
                    scrollToBottom();
                },
                errorMsg -> Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show());

        new TedKeyboardObserver(this).listen(isShow -> {
            if (isShow) scrollToBottom();
        });
    }

    public class ChatActivityClickHandler {
        public void btnBackClick() {
            onBackPressed();
        }

        public void btnSendClick() {
            FirebaseSendChat.sendMessage(UserCache.getUser(ChatActivity.this),
                    viewModel.message.getValue(), getResources(),
                    () -> {

                    },
                    errorMsg -> Toast.makeText(ChatActivity.this, errorMsg, Toast.LENGTH_SHORT).show());
            viewModel.message.setValue("");
        }
    }

    private void scrollToBottom() {
        if (!viewModel.chatModels.isEmpty())
            binding.recyclerChat.smoothScrollToPosition(viewModel.chatModels.size() - 1);
    }


}

