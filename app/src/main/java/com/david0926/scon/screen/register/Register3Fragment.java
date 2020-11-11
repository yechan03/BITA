package com.david0926.scon.screen.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.david0926.scon.R;
import com.david0926.scon.databinding.FragmentRegister3Binding;
import com.david0926.scon.screen.personality.PersonalityActivity;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class Register3Fragment extends Fragment {

    public static Register3Fragment newInstance() {
        return new Register3Fragment();
    }

    private FragmentRegister3Binding binding;
    private RegisterViewModel viewModel;

    private ActivityResultLauncher<Intent> requestActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK)
                        viewModel.personality.setValue(
                                result.getData().getStringExtra("personality"));
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_3, container, false);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new Register3FragmentClickHandler());

        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
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

        public void edtPersonalityClick() {
            requestActivity.launch(new Intent(requireContext(), PersonalityActivity.class));
        }
    }

    private void checkNextPageEnabled() {
        viewModel.isNextEnabled.setValue(!viewModel.introduce.getValue().isEmpty());
    }


}
