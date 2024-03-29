package com.sarftec.lifehacks.view.file

import android.app.Activity
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sarftec.lifehacks.domain.model.Hack

val rebindHacks = hashSetOf<Hack>()

fun ImageView.loadImage(ref: View, uri: Uri?) {
    Glide.with(ref)
        .load(uri)
        .into(this)
}

fun ImageView.loadImage(ref: Activity, uri: Uri?) {
    Glide.with(ref)
        .load(uri)
        .into(this)
}