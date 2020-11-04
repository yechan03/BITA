package com.david0926.scon.screen.chat;

import android.content.res.Resources;

import com.david0926.scon.R;
import com.david0926.scon.util.FirebaseErrorUtil;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseObserveChat {

    private static OnSnapshotListener onSnapshotListener;
    private static OnSnapshotFailedListener onSnapshotFailedListener;

    private static Resources mResources;

    public interface OnSnapshotListener {
        void onSnapshot(ChatDocModel chatDoc);
    }

    public interface OnSnapshotFailedListener {
        void onSnapshotFailed(String errorMsg);
    }

    public static void observeMessage(Resources res, OnSnapshotListener s, OnSnapshotFailedListener e) {
        onSnapshotListener = s;
        onSnapshotFailedListener = e;
        mResources = res;

        FirebaseFirestore
                .getInstance()
                .collection("chat")
                .document("ssf")
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) {
                        onSnapshotFailedListener.onSnapshotFailed(
                                FirebaseErrorUtil.getErrorString(mResources,
                                        error, R.string.error_send_chat_failed));
                        return;
                    }
                    onSnapshotListener.onSnapshot(value.toObject(ChatDocModel.class));
                });
    }
}
