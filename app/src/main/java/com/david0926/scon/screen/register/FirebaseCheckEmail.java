package com.david0926.scon.screen.register;

import android.content.res.Resources;

import com.david0926.scon.R;
import com.david0926.scon.util.FirebaseErrorUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;

public class FirebaseCheckEmail {

    private static OnCheckSuccessListener onCheckSuccessListener;
    private static OnCheckFailedListener onCheckFailedListener;

    private static Resources mResources;

    public interface OnCheckSuccessListener {
        void onCheckSuccess();
    }

    public interface OnCheckFailedListener {
        void onCheckFailed(String errorMsg);
    }

    public static void checkEmail(String email, Resources res, OnCheckSuccessListener s, OnCheckFailedListener e) {
        onCheckSuccessListener = s;
        onCheckFailedListener = e;
        mResources = res;

        checkAccountExists(email, s1 -> onCheckSuccessListener.onCheckSuccess());
    }

    private static void checkAccountExists(String email, OnSuccessListener<SignInMethodQueryResult> s) {
        FirebaseAuth
                .getInstance()
                .fetchSignInMethodsForEmail(email)
                .addOnSuccessListener(result -> {
                    List<String> signInMethods = result.getSignInMethods();
                    if (signInMethods == null || signInMethods.isEmpty()) s.onSuccess(result);
                    else onCheckFailedListener.onCheckFailed(
                            mResources.getString(R.string.error_user_account_taken));
                })
                .addOnFailureListener(e -> onCheckFailedListener.onCheckFailed(
                        FirebaseErrorUtil.getErrorString(mResources, e, R.string.error_user_account_taken)));
    }
}
