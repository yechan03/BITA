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
import com.david0926.scon.databinding.FragmentMain2Binding;
import com.david0926.scon.screen.profile.EditProfileActivity;
import com.david0926.scon.util.UserCache;

public class Main2Fragment extends Fragment {

    public static Main2Fragment newInstance() {
        return new Main2Fragment();
    }

    private FragmentMain2Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_2, container, false);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new Main2FragmentClickHandler());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.setUserModel(UserCache.getUser(requireContext()));
    }

    public class Main2FragmentClickHandler {
        public void btnEditClick() {
            startActivity(new Intent(requireContext(), EditProfileActivity.class));
        }
    }
}
