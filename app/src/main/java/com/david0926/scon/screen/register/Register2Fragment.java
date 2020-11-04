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
import com.david0926.scon.databinding.FragmentRegister2Binding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register2Fragment extends Fragment {

    public static Register2Fragment newInstance() {
        return new Register2Fragment();
    }

    private FragmentRegister2Binding binding;
    private RegisterViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_2, container, false);
        binding.setLifecycleOwner(this);

        viewModel = ViewModelProviders.of(requireActivity()).get(RegisterViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.pw.observe(getViewLifecycleOwner(), s -> checkNextPageEnabled());
        viewModel.pwConfirm.observe(getViewLifecycleOwner(), s -> checkNextPageEnabled());

        return binding.getRoot();
    }

    private void checkNextPageEnabled() {
        String pw = viewModel.pw.getValue();
        viewModel.isNextEnabled.setValue(isValidPw(pw) && viewModel.pwConfirm.getValue().equals(pw));
    }

    private boolean isValidPw(String target) {
        //6~24 letters, 0~9 + A-z
        Pattern p = Pattern.compile("(^.*(?=.{6,24})(?=.*[0-9])(?=.*[A-z]).*$)");
        Matcher m = p.matcher(target);
        //except korean letters
        return m.find() && !target.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
    }
}
