package com.ehedgehog.android.getweather;

import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

public class ImageViewAdapter {

    @BindingAdapter("android:src")
    public static void setIconId(@NonNull ImageView imageView, @NonNull String iconId) {
        if (TextUtils.isEmpty(iconId)) return;

        String url = BuildConfig.IMAGES_ENDPOINT + iconId + "@2x.png";
        Picasso.get().load(url).into(imageView);
    }

}
