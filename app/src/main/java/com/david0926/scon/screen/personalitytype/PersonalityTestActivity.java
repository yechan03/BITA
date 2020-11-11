package com.david0926.scon.screen.personalitytype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.david0926.scon.R;
import com.david0926.scon.databinding.ActivityPersonalityTestBinding;

public class PersonalityTestActivity extends AppCompatActivity {

    private ActivityPersonalityTestBinding binding;
    private WebSettings webSettings;
    private boolean check_bt = true;

    //MBTI URL
    private final static String MBTI_URL = "https://www.16personalities.com/ko/";
    //private final static String MBTI_URL = "16personalities.com/ko/성격유형-isfp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_personality_test);
        binding.setLifecycleOwner(this);

        binding.mbtiWebview.setWebViewClient(new WebViewClient());
        webSettings = binding.mbtiWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.mbtiWebview.loadUrl(MBTI_URL);


        binding.personalityFinishBt.setOnClickListener(v -> {
            String asd = binding.mbtiWebview.getUrl();
            Log.e("asd",asd);
            String[] array = asd.split("-");
            if (array[1]!=null){
                Log.e("asd",array[1]);
            }else{
                Toast.makeText(this, "검사 완료후 클릭해주세요", Toast.LENGTH_SHORT).show();
            }
        });

    }


}