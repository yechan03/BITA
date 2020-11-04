package com.david0926.scon.dialog;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.david0926.scon.R;

public class LoadingDialogViewModel extends ViewModel {

    @BindingAdapter("bindMyLottieLoop")
    public static void bindLottieLoop(LottieAnimationView l, Boolean loop) {
        if (loop == null) return;
        l.setRepeatCount(loop ? LottieDrawable.INFINITE : 0);
    }

    @BindingAdapter("bindMyLottieAnimation")
    public static void bindMyLottieAnimation(LottieAnimationView l, Integer id) {
        if (id == null) return;
        l.setAnimation(id);
        l.playAnimation();
    }

    public MutableLiveData<String> msg = new MutableLiveData<>("");

    public MutableLiveData<Integer> lottieAnimation = new MutableLiveData<>(R.raw.loading);
    public MutableLiveData<Boolean> lottieLoop = new MutableLiveData<>(true);
}
