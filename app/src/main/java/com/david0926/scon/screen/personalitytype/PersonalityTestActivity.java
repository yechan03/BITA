package com.david0926.scon.screen.personalitytype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.david0926.scon.R;
import com.david0926.scon.databinding.ActivityPersonalityTestBinding;

public class PersonalityTestActivity extends AppCompatActivity {
    private ActivityPersonalityTestBinding binding;
    private WebSettings webSettings;
    private final static String MBTI_URL = "https://www.16personalities.com/ko/%EB%AC%B4%EB%A3%8C-%EC%84%B1%EA%B2%A9-%EC%9C%A0%ED%98%95-%EA%B2%80%EC%82%AC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_personality_test);
        binding.setLifecycleOwner(this);

        binding.mbtiWebview.setWebViewClient(new WebViewClient());
        webSettings = binding.mbtiWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        binding.mbtiWebview.loadUrl(MBTI_URL);



    }
}