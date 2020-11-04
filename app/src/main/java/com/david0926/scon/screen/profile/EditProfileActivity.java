package com.david0926.scon.screen.profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.david0926.scon.R;
import com.david0926.scon.databinding.ActivityEditProfileBinding;
import com.david0926.scon.dialog.LoadingDialog;
import com.david0926.scon.model.UserModel;
import com.david0926.scon.screen.register.FirebaseUploadProfile;
import com.david0926.scon.util.UserCache;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class EditProfileActivity extends AppCompatActivity {

    private UserModel mUser;

    private ActivityEditProfileBinding binding;
    private EditProfileActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new EditProfileActivityClickHandler());

        viewModel = ViewModelProviders.of(this).get(EditProfileActivityViewModel.class);
        binding.setViewModel(viewModel);

        mUser = UserCache.getUser(this);
        viewModel.profileLink.setValue(mUser.getProfile());
        viewModel.introduce.setValue(mUser.getIntroduce());
    }

    public class EditProfileActivityClickHandler {

        public void btnBackClick() {
            finish();
        }

        public void btnProfileClick() {
            // TODO: implement dark mode on picker
            TedImagePicker
                    .with(EditProfileActivity.this)
                    .startAnimation(R.anim.slide_up, R.anim.slide_up_before)
                    .finishAnimation(R.anim.slide_down_before, R.anim.slide_down)
                    .start((OnSelectedListener) uri -> viewModel.profile.setValue(uri));
        }

        public void btnSaveClick() {
            LoadingDialog dialog = new LoadingDialog(EditProfileActivity.this);
            dialog.setMessage(getString(R.string.register_profile_uploading)).show();

            FirebaseUploadProfile.uploadProfile(viewModel.profile.getValue(), viewModel.introduce.getValue(), getResources(),
                    (profile, introduce) -> {
                        if (profile != null) mUser.setProfile(profile);
                        mUser.setIntroduce(introduce);
                        UserCache.setUser(EditProfileActivity.this, mUser);

                        dialog.setMessage(getString(R.string.edit_profile_complete))
                                .setOnAnimationFinishListener(EditProfileActivity.this::finish)
                                .finish(true);
                    },
                    errorMsg -> {
                        dialog.finish(false);
                        viewModel.errorMsg.setValue(errorMsg);
                    });
        }
    }
}