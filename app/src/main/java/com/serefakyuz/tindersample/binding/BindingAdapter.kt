package com.serefakyuz.tindersample.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.serefakyuz.tindersample.R
import com.serefakyuz.tindersample.model.charachter.Status


@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}
@BindingAdapter("setStatus")
fun setStatus(view: TextView, status: Status) {
    view.text = status.value
    view.setCompoundDrawablesWithIntrinsicBounds(Status.getStatusDrawable(status), 0, 0, 0)
}

