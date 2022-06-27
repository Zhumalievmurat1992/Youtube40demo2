package com.example.youtube40.core.extentions

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun Context.loadImage(url: String, view: ImageView){
    Glide.with(this)
        .load(url)
        .into(view)
}
fun Context.showMessage(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}