package com.david0926.scon.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.david0926.scon.R;
import com.david0926.scon.screen.login.LoginActivity;
import com.david0926.scon.screen.main.MainActivity;
import com.david0926.scon.screen.onboard.OnBoardActivity;
import com.david0926.scon.screen.register.RegisterActivity;
import com.david0926.scon.util.SharedPreferenceUtil;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            String userState = SharedPreferenceUtil.getString(this, "user_state", "on_board");

            // TODO: debug - remove this line to show onboard screen once
             userState = "on_board";

            // TODO: replace with enum
            switch (userState) {
                case "on_board":
                    startActivity(new Intent(SplashActivity.this, OnBoardActivity.class));
                    break;
                case "upload_profile":
                    Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                    intent.putExtra("register_page", 2);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    startActivity(intent);
                    break;
                default:
                    startActivity(new Intent(SplashActivity.this,
                            FirebaseAuth.getInstance().getCurrentUser() == null ?
                                    LoginActivity.class : MainActivity.class));
            }
            finish();
        }, 2000);
    }
}
