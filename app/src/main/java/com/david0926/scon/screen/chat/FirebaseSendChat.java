package com.david0926.scon.screen.chat;

import android.content.res.Resources;
import android.util.Log;

import com.david0926.scon.R;
import com.david0926.scon.model.UserModel;
import com.david0926.scon.util.FirebaseErrorUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class FirebaseSendChat {

    private static OnSendSuccessListener onSendSuccessListener;
    private static OnSendFailedListener onSendFailedListener;

    private static Resources mResources;

    public interface OnSendSuccessListener {
        void onSendSuccess();
    }

    public interface OnSendFailedListener {
        void onSendFailed(String errorMsg);
    }

    public static void sendMessage(UserModel user, String msg, String channel, Resources res, OnSendSuccessListener s, OnSendFailedListener e) {
        onSendSuccessListener = s;
        onSendFailedListener = e;
        mResources = res;

        sendMessage(user, msg, channel, s1 -> onSendSuccessListener.onSendSuccess());
    }

    private static void sendMessage(UserModel user, String msg, String channel, OnSuccessListener<Void> s) {
        FirebaseFirestore
                .getInstance()
                .collection("chat")
                .document(channel)
                .update("messages", FieldValue.arrayUnion(ChatModel.toMap(new ChatModel(user, msg, getTime()))))
                .addOnSuccessListener(s)
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    onSendFailedListener.onSendFailed(
                            FirebaseErrorUtil.getErrorString(mResources, e, R.string.error_send_chat_failed));
                });
    }

    // TODO: replace with server time - cloud functions
    private static String getTime() {
        return new Date().toString();
    }
}
