package com.david0926.scon.screen.register;

import android.content.res.Resources;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.david0926.scon.R;
import com.david0926.scon.util.FirebaseErrorUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseUploadProfile {

    private static OnUploadSuccessListener onUploadSuccessListener;
    private static OnUploadFailedListener onUploadFailedListener;

    private static Resources mResources;

    public interface OnUploadSuccessListener {
        void onUploadSuccess(String profile, String introduce);
    }

    public interface OnUploadFailedListener {
        void onUploadFailed(String errorMsg);
    }

    public static void uploadProfile(Uri profile, String introduce, String personality, Resources res, OnUploadSuccessListener s, OnUploadFailedListener e) {
        onUploadSuccessListener = s;
        onUploadFailedListener = e;
        mResources = res;

        if (profile != null
                && !getMimeType(profile).equals("image/jpeg")
                && !getMimeType(profile).equals("image/png")) {
            onUploadFailedListener.onUploadFailed(mResources.getString(R.string.error_image_invalid_type));
            return;
        }

        // TODO: check image size later - file.length()

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            onUploadFailedListener.onUploadFailed(mResources.getString(R.string.error_profile_logged_out));
            return;
        }

        String email = user.getEmail();

        if (profile != null) {
            uploadProfile(email, profile,
                    s1 -> updateUserInfo(email, s1.toString(), introduce, personality,
                            s2 -> onUploadSuccessListener.onUploadSuccess(s1.toString(), introduce)));
        } else {
            updateUserInfo(email, introduce, s1 -> onUploadSuccessListener.onUploadSuccess(null, introduce));
        }

    }

    private static String getMimeType(Uri uri) {
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
    }

    private static void uploadProfile(String email, Uri profile, OnSuccessListener<Uri> s) {

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/png")
                .build();

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("profile/" + email + ".png");

        UploadTask uploadTask = storageReference.putFile(profile, metadata);

        uploadTask.continueWithTask(task -> {
            if (task.isSuccessful()) return storageReference.getDownloadUrl();

            onUploadFailedListener.onUploadFailed(FirebaseErrorUtil
                    .getErrorString(mResources, task.getException(), R.string.error_image_upload_failed));
            return null;
        })
                .addOnSuccessListener(s)
                .addOnFailureListener(e -> onUploadFailedListener.onUploadFailed(
                        FirebaseErrorUtil.getErrorString(mResources, e, R.string.error_image_url_failed)));
    }

    private static void updateUserInfo(String email, String profile, String introduce, String personality, OnSuccessListener<Void> s) {
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(email)
                .update("introduce", introduce, "profile", profile, "personality", personality)
                .addOnSuccessListener(s)
                .addOnFailureListener(e -> onUploadFailedListener.onUploadFailed(
                        FirebaseErrorUtil.getErrorString(mResources, e, mResources.getString(R.string.error_user_info_update_failed))));
    }

    private static void updateUserInfo(String email, String introduce, OnSuccessListener<Void> s) {
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(email)
                .update("introduce", introduce)
                .addOnSuccessListener(s)
                .addOnFailureListener(e -> onUploadFailedListener.onUploadFailed(
                        FirebaseErrorUtil.getErrorString(mResources, e, mResources.getString(R.string.error_user_info_update_failed))));
    }
}
