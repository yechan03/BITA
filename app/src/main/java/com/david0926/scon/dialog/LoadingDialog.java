package com.david0926.scon.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.david0926.scon.R;
import com.david0926.scon.databinding.DialogLoadingBinding;

public class LoadingDialog extends Dialog {

    private FragmentActivity mActivity;
    private String msg = "";

    private DialogLoadingBinding binding;
    private LoadingDialogViewModel viewModel;

    private OnAnimationFinishListener onAnimationFinishListener;

    public interface OnAnimationFinishListener {
        void onAnimationFinish();
    }

    public LoadingDialog setOnAnimationFinishListener(OnAnimationFinishListener listener) {
        onAnimationFinishListener = listener;
        return this;
    }

    public LoadingDialog(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        super.setCancelable(false);
        mActivity = fragmentActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_loading, null, false);
        setContentView(binding.getRoot());

        binding.setLifecycleOwner(mActivity);

        ViewModelProvider.NewInstanceFactory f = new ViewModelProvider.NewInstanceFactory();
        viewModel = f.create(LoadingDialogViewModel.class);

        binding.setViewModel(viewModel);

        viewModel.msg.setValue(msg);
    }

    public LoadingDialog setMessage(String msg) {
        if (viewModel != null) viewModel.msg.setValue(msg);
        else this.msg = msg;
        return this;
    }

    public void finish(boolean success) {
        if (!success) {
            cancel();
            return;
        }

        viewModel.lottieLoop.setValue(false);
        viewModel.lottieAnimation.setValue(R.raw.success);

        //TODO: hardcoded delay - replace with lottie duration
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            onAnimationFinishListener.onAnimationFinish();
            cancel();
        }, 1500);
    }


}