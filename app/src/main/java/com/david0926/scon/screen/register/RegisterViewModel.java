package com.david0926.scon.screen.register;

import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.david0926.scon.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

public class RegisterViewModel extends ViewModel {

    // Activity

    @BindingAdapter({"bindFragmentWithAnim", "bindFragmentAnimDirection"})
    public static void bindFragmentWithAnim(FrameLayout frame, Fragment frag, AnimDirection dir) {
        FragmentActivity a = (FragmentActivity) frame.getContext();
        FragmentTransaction transaction = a.getSupportFragmentManager().beginTransaction();

        switch (dir) {
            case FORWARD:
                transaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_left_before);
                break;
            case BACKWARD:
                transaction.setCustomAnimations(R.anim.slide_right, R.anim.slide_right_before);
        }

        transaction.replace(frame.getId(), frag);
        transaction.commit();
    }

    public ObservableArrayList<Fragment> fragments = new ObservableArrayList<>();

    public RegisterViewModel() {
        fragments.addAll(Arrays.asList(
                Register1Fragment.newInstance(),
                Register2Fragment.newInstance(),
                Register3Fragment.newInstance()
        ));
    }

    @BindingAdapter("bindIndicatorIndex")
    public static void bindIndicatorIndex(TabLayout t, int i) {
        TabLayout.Tab tab = t.getTabAt(i);
        if (tab != null) tab.select();
    }

    @BindingAdapter("bindTabClickable")
    public static void bindTabClickable(TabLayout t, boolean b) {
        for (View v : t.getTouchables()) {
            v.setClickable(b);
            v.setFocusable(b);
        }
    }

    public MutableLiveData<Integer> currentPage = new MutableLiveData<>(0);

    public enum AnimDirection {INIT, FORWARD, BACKWARD}

    public MutableLiveData<AnimDirection> currentDirection = new MutableLiveData<>(AnimDirection.INIT);

    public Boolean isBackward() {
        return currentDirection.getValue() == AnimDirection.BACKWARD;
    }

    public void nextPage() {
        int value = currentPage.getValue();
        if (value < fragments.size() - 1) {
            currentDirection.setValue(AnimDirection.FORWARD);
            currentPage.setValue(value + 1);
        }
    }

    public void previousPage() {
        int value = currentPage.getValue();
        if (value > 0) {
            currentDirection.setValue(AnimDirection.BACKWARD);
            currentPage.setValue(value - 1);
        }
    }

    // Shared scope

    public MutableLiveData<Boolean> isNextEnabled = new MutableLiveData<>(false);
    public MutableLiveData<String> errorMsg = new MutableLiveData<>("");

    // Fragment 1

    public MutableLiveData<String> name = new MutableLiveData<>("");
    public MutableLiveData<String> email = new MutableLiveData<>("");

    // Fragment 2

    public MutableLiveData<String> pw = new MutableLiveData<>("");
    public MutableLiveData<String> pwConfirm = new MutableLiveData<>("");

    // Fragment 3

    public MutableLiveData<Uri> profile = new MutableLiveData<>();
    public MutableLiveData<String> introduce = new MutableLiveData<>("");

}
