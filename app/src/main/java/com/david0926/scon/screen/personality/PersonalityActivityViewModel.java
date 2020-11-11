package com.david0926.scon.screen.personality;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PersonalityActivityViewModel extends ViewModel {
    public MutableLiveData<Boolean> isFinish = new MutableLiveData<>(false);
}
