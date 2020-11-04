package com.david0926.scon.screen.onboard;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.viewpager2.widget.ViewPager2;

import com.david0926.scon.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnBoardViewModel extends ViewModel {

    public List<Fragment> fragments = new ArrayList<>();

    public OnBoardViewModel() {
        fragments.addAll(Arrays.asList(
                OnBoardPagerFragment.newInstance(R.layout.fragment_on_board_1),
                OnBoardPagerFragment.newInstance(R.layout.fragment_on_board_2),
                OnBoardPagerFragment.newInstance(R.layout.fragment_on_board_3),
                OnBoardPagerFragment.newInstance(R.layout.fragment_on_board_4)));
    }

    @BindingAdapter("bindPagerCurrentItem")
    public static void bindPagerCurrentItem(ViewPager2 pager, int position) {
        pager.setCurrentItem(position);
    }

    public MutableLiveData<Integer> currentPage = new MutableLiveData<>(0);
    public MutableLiveData<Integer> pageChangeRequest = new MutableLiveData<>(0);

    public Boolean isFirstPage() {
        return currentPage.getValue() == 0;
    }

    public void previousPage() {
        int value = currentPage.getValue();
        if (currentPage.getValue() > 0) {
            value--;
            currentPage.setValue(value);
            pageChangeRequest.setValue(value);
        }
    }

    @BindingAdapter("bindPagerCallback")
    public static void bindPagerCallback(ViewPager2 pager, ViewPager2.OnPageChangeCallback c) {
        pager.registerOnPageChangeCallback(c);
    }

    public ViewPager2.OnPageChangeCallback pagerCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            currentPage.setValue(position);
        }
    };

    @BindingAdapter("bindTabMediator")
    public static void bindTabMediator(TabLayout tab, ViewPager2 pager) {
        new TabLayoutMediator(tab, pager, (t, position) -> {
            t.view.setClickable(false);
            t.view.setFocusable(false);
        }).attach();
    }
}
