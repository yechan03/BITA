package com.david0926.scon.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.david0926.scon.R;
import com.david0926.scon.databinding.FragmentMain1Binding;
import com.david0926.scon.screen.chat.ChatActivity;

public class Main1Fragment extends Fragment {

    public static Main1Fragment newInstance() {
        return new Main1Fragment();
    }

    private FragmentMain1Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_1, container, false);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new Main1FragmentClickHandler());

        return binding.getRoot();
    }

    public class Main1FragmentClickHandler {
        public void btnPrivateClick() {
            Intent intent = new Intent(requireContext(), ChatActivity.class);
            intent.putExtra("private", true);
            startActivity(intent);
        }

        public void btnPublicClick() {
            Intent intent = new Intent(requireContext(), ChatActivity.class);
            intent.putExtra("public", true);
            startActivity(intent);
        }
    }

}
