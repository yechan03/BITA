package com.david0926.scon.screen.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.david0926.scon.R;
import com.david0926.scon.databinding.FragmentMain3Binding;
import com.david0926.scon.screen.login.LoginActivity;
import com.david0926.scon.util.UserCache;
import com.google.firebase.auth.FirebaseAuth;

public class Main3Fragment extends Fragment {

    public static Main3Fragment newInstance() {
        return new Main3Fragment();
    }

    private FragmentMain3Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_3, container, false);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new Main3FragmentClickHandler());

        return binding.getRoot();
    }

    public class Main3FragmentClickHandler {

        public void btnLinkClick(String url) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }

        public void btnLogoutClick() {
            FirebaseAuth.getInstance().signOut();
            UserCache.logout(requireContext());
            requireActivity().finish();
            startActivity(new Intent(requireContext(), LoginActivity.class));
        }
    }
}
