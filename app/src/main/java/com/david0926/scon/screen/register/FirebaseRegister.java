package com.david0926.scon.screen.register;

import android.content.res.Resources;

import com.david0926.scon.R;
import com.david0926.scon.model.UserModel;
import com.david0926.scon.util.FirebaseErrorUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseRegister {

    // TODO: add Cloud Functions - to upload register time

    private static OnRegisterSuccessListener onRegisterSuccessListener;
    private static OnRegisterFailedListener onRegisterFailedListener;

    private static Resources mResources;

    public interface OnRegisterSuccessListener {
        void onRegisterSuccess();
    }

    public interface OnRegisterFailedListener {
        void onRegisterFailed(String msg);
    }

    public static void register(String name, String email, String pw, Resources res, OnRegisterSuccessListener s, OnRegisterFailedListener e) {
        onRegisterSuccessListener = s;
        onRegisterFailedListener = e;
        mResources = res;

        uploadData(name, email, res.getString(R.string.profile_placeholder),
                s1 -> createUser(email, pw,
                        s2 -> onRegisterSuccessListener.onRegisterSuccess()));
    }

    private static void uploadData(String name, String email, String profile, OnSuccessListener<Void> s) {
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(email)
                .set(new UserModel(name, email, profile))
                .addOnSuccessListener(s)
                .addOnFailureListener(e -> onRegisterFailedListener.onRegisterFailed(
                        FirebaseErrorUtil.getErrorString(mResources, e, R.string.error_user_data_upload_failed)));
    }

    private static void createUser(String email, String pw, OnSuccessListener<AuthResult> s) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth
                .createUserWithEmailAndPassword(email, pw)
                .addOnSuccessListener(s)
                .addOnFailureListener(e -> onRegisterFailedListener.onRegisterFailed(
                        FirebaseErrorUtil.getErrorString(mResources, e, R.string.error_user_create_failed)));
    }
}
