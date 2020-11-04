package com.david0926.scon.screen.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginActivityViewModel extends ViewModel {

    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<String> pw = new MutableLiveData<>("");

    public MutableLiveData<String> errorMsg = new MutableLiveData<>("");

    @BindingAdapter("bindTextWatcher")
    public static void bindTextWatcher(EditText e, TextWatcher w) {
        e.addTextChangedListener(w);
    }

    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            errorMsg.setValue("");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}
