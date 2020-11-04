package com.david0926.scon.screen.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.david0926.scon.R;
import com.david0926.scon.databinding.FragmentRegister1Binding;

public class Register1Fragment extends Fragment {

    public static Register1Fragment newInstance() {
        return new Register1Fragment();
    }

    private FragmentRegister1Binding binding;
    private RegisterViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_1, container, false);
        binding.setLifecycleOwner(this);

        viewModel = ViewModelProviders.of(requireActivity()).get(RegisterViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.name.observe(getViewLifecycleOwner(), s -> checkNextPageEnabled());
        viewModel.email.observe(getViewLifecycleOwner(), s -> {
            checkNextPageEnabled();
            viewModel.errorMsg.setValue("");
        });

        return binding.getRoot();
    }

    private void checkNextPageEnabled() {
        viewModel.isNextEnabled.setValue(!viewModel.name.getValue().isEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(viewModel.email.getValue()).matches());
    }

}
