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
import com.david0926.scon.databinding.FragmentRegister3Binding;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class Register3Fragment extends Fragment {

    public static Register3Fragment newInstance() {
        return new Register3Fragment();
    }

    private FragmentRegister3Binding binding;
    private RegisterViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_3, container, false);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new Register3FragmentClickHandler());

        viewModel = ViewModelProviders.of(requireActivity()).get(RegisterViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.profile.observe(getViewLifecycleOwner(), uri -> checkNextPageEnabled());
        viewModel.introduce.observe(getViewLifecycleOwner(), s -> checkNextPageEnabled());

        return binding.getRoot();
    }

    public class Register3FragmentClickHandler {
        public void btnProfileClick() {
            // TODO: implement dark mode on picker
            TedImagePicker
                    .with(requireContext())
                    .startAnimation(R.anim.slide_up, R.anim.slide_up_before)
                    .finishAnimation(R.anim.slide_down_before, R.anim.slide_down)
                    .start((OnSelectedListener) uri -> viewModel.profile.setValue(uri));
        }
    }

    private void checkNextPageEnabled() {
        viewModel.isNextEnabled.setValue(!viewModel.introduce.getValue().isEmpty());
    }


}
