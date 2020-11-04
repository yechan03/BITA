package com.david0926.scon.screen.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResetPwActivityViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<Boolean> isSendEnabled = new MutableLiveData<>(false);

}
