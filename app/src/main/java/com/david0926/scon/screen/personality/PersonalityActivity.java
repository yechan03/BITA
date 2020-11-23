package com.david0926.scon.screen.personality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.david0926.scon.R;
import com.david0926.scon.databinding.ActivityPersonalityBinding;

public class PersonalityActivity extends AppCompatActivity {

    private ActivityPersonalityBinding binding;
    private WebSettings webSettings;

    private PersonalityActivityViewModel viewModel;

    private final static String MBTI_URL = "https://www.16personalities.com/ko/%EB%AC%B4%EB%A3%8C-%EC%84%B1%EA%B2%A9-%EC%9C%A0%ED%98%95-%EA%B2%80%EC%82%AC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personality);
        binding.setLifecycleOwner(this);

        viewModel = new ViewModelProvider(this).get(PersonalityActivityViewModel.class);
        binding.setViewModel(viewModel);

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!url.equals(MBTI_URL)) {
                    viewModel.isFinish.setValue(true);
                }
            }
        };

        binding.toolbarPersonality.setNavigationOnClickListener(v -> finish());

        binding.mbtiWebview.setWebViewClient(webViewClient);
        webSettings = binding.mbtiWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.mbtiWebview.loadUrl(MBTI_URL);


        binding.personalityFinishBt.setOnClickListener(v -> {
            String personality = binding.mbtiWebview.getUrl().split("-")[1];
            if (personality != null) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("personality", personality.toUpperCase()));
                finish();
            } else {
                Toast.makeText(this, "검사가 완료되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}