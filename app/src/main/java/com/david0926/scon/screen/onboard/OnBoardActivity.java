package com.david0926.scon.screen.onboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.david0926.scon.R;
import com.david0926.scon.databinding.ActivityOnBoardBinding;
import com.david0926.scon.screen.login.LoginActivity;
import com.david0926.scon.util.SharedPreferenceUtil;


public class OnBoardActivity extends AppCompatActivity {

    private ActivityOnBoardBinding binding;
    private OnBoardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_board);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new OnBoardActivityClickHandler());

        viewModel = new ViewModelProvider(this).get(OnBoardViewModel.class);
        binding.setViewModel(viewModel);

        OnBoardPagerAdapter adapter = new OnBoardPagerAdapter(this, viewModel.fragments);
        binding.pagerOnBoard.setAdapter(adapter);
    }

    public class OnBoardActivityClickHandler {
        public void btnFinishClick() {
            SharedPreferenceUtil.put(OnBoardActivity.this, "user_state", "login");
            startActivity(new Intent(OnBoardActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (!viewModel.isFirstPage()) viewModel.previousPage();
        else super.onBackPressed();
    }
}