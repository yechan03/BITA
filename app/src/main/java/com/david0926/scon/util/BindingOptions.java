package com.david0926.scon.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

import com.bumptech.glide.Glide;
import com.david0926.scon.R;

public class BindingOptions {

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter("bindInvisibility")
    public static void bindInvisibility(View v, Boolean b) {
        v.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("bindErrorMsg")
    public static void bindErrorMsg(TextView t, String s) {
        if (s == null) return;
        t.setText(s);
        if (s.isEmpty()) return;
        t.startAnimation(AnimationUtils.loadAnimation(t.getContext(), R.anim.shake));
    }

    @BindingAdapter("bindButtonEnabled")
    public static void bindButtonEnabled(View v, boolean enabled) {
        v.setClickable(enabled);
        v.setFocusable(enabled);
        v.setBackgroundTintList(ContextCompat.getColorStateList(v.getContext(),
                enabled ? R.color.colorPrimary : R.color.buttonDisabledColorPrimary));
    }

    @BindingAdapter("bindImageButtonEnabled")
    public static void bindButtonEnabledTint(ImageView v, boolean enabled) {
        v.setClickable(enabled);
        v.setFocusable(enabled);
        v.setImageTintList(ContextCompat.getColorStateList(v.getContext(),
                enabled ? R.color.colorPrimary : R.color.buttonDisabledColorPrimary));
    }

    @BindingAdapter("bindEditTextAutoFocus")
    public static void bindEditTextAutoFocus(EditText e, boolean enabled) {
        if (!enabled) return;
        e.requestFocus();

        Activity a = (Activity) e.getContext();
        if (a == null) return;

        InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(e, InputMethodManager.SHOW_IMPLICIT);
    }

    @BindingAdapter("bindImageUri")
    public static void bindImageUri(ImageView v, Uri uri) {
        if (uri != null) v.setImageURI(uri);
    }

    @BindingAdapter("bindImageLink")
    public static void bindImageLink(ImageView view, String link) {
        if (link == null || link.isEmpty()) return;
        Glide.with(view).load(link).into(view);
    }

}
