package com.david0926.scon.screen.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.david0926.scon.R;
import com.david0926.scon.databinding.ActivityLoginBinding;
import com.david0926.scon.dialog.LoadingDialog;
import com.david0926.scon.screen.main.MainActivity;
import com.david0926.scon.screen.register.RegisterActivity;
import com.david0926.scon.util.KeyboardUtil;
import com.david0926.scon.util.UserCache;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new LoginActivityClickHandler());

        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        binding.setViewModel(viewModel);
    }

    public class LoginActivityClickHandler {

        public void btnResetPwClick() {
            startActivity(new Intent(LoginActivity.this, ResetPwActivity.class));
        }

        public void btnLoginClick() {
            KeyboardUtil.hideKeyboard(LoginActivity.this);

            LoadingDialog dialog = new LoadingDialog(LoginActivity.this);
            dialog.setMessage(getString(R.string.login_loading)).show();

            FirebaseLogin.login(viewModel.email.getValue(), viewModel.pw.getValue(), getResources(),
                    user -> {
                        if (user.getPersonality() == null || user.getPersonality().isEmpty()) {
                            dialog.finish(false);
                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            intent.putExtra("register_page", 2);
                            startActivity(intent);
                            return;
                        }
                        UserCache.setUser(LoginActivity.this, user);
                        dialog.setMessage(getString(R.string.login_success)).setOnAnimationFinishListener(() -> {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }).finish(true);
                    },
                    errorMsg -> {
                        dialog.finish(false);
                        viewModel.errorMsg.setValue(errorMsg);
                    });
        }

        public void btnRegisterClick() {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            overridePendingTransition(R.anim.slide_up, R.anim.slide_up_before);
        }

    }
}