package com.david0926.scon.screen.main;

import android.widget.FrameLayout;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.david0926.scon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;

public class MainActivityViewModel extends ViewModel {

    @BindingAdapter("bindFragment")
    public static void bindFragment(FrameLayout frame, Fragment frag) {
        FragmentActivity a = (FragmentActivity) frame.getContext();
        FragmentTransaction transaction = a.getSupportFragmentManager().beginTransaction();

        //transition

        transaction.replace(frame.getId(), frag);
        transaction.commit();
    }

    public ObservableArrayList<Fragment> fragments = new ObservableArrayList<>();
    public MutableLiveData<Integer> currentTab = new MutableLiveData<>(0);

    public MainActivityViewModel() {
        fragments.addAll(Arrays.asList(
                Main1Fragment.newInstance(),
                Main2Fragment.newInstance(),
                Main3Fragment.newInstance()));
    }

    @BindingAdapter("bindBottomNavListener")
    public static void bindBottomNavListener(BottomNavigationView b, BottomNavigationView.OnNavigationItemSelectedListener l) {
        b.setOnNavigationItemSelectedListener(l);
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {

        // TODO: better way?
        int position;
        switch (item.getItemId()) {
            case R.id.action_1:
                position = 0;
                break;
            case R.id.action_2:
                position = 1;
                break;
            default: //R.id.action_3:
                position = 2;
                break;
        }
        currentTab.setValue(position);

        return true;
    };
}
