package com.shivesh.trendingrepo.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.text.DecimalFormat

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 *//*
class ImageUtils {
    companion object {
        fun loadImage(
            context: Context,
            url: String,
            placeHolder: Int,
            imageView: ImageView,
            radius: Int
        ) {
            val requestOptions = RequestOptions().placeholder(placeHolder).centerCrop()
            Glide.with(context).load(url)
                .apply(requestOptions.transform(CenterCrop(), RoundedCorners(radius)))
                .into(imageView)
        }
    }
}

class NumberFormatter {
    companion object {
        fun formatDecimalNum(decimalNum: Double): String {
            var numPattern = DecimalFormat("###")
            return numPattern.format(decimalNum)
        }
    }
}

class ViewUtils {
    companion object {
        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
*/

fun ImageView.fetchImage(context: Context, url: String, placeHolder: Int, radius: Int) {
    val requestOptions = RequestOptions().placeholder(placeHolder)
        .centerCrop()
    Glide.with(context)
        .load(url)
        .apply(requestOptions.transform(CenterCrop(), RoundedCorners(radius)))
        .into(this)
}

fun formatDecimalNum(decimalNum: Double): String {
    val numPattern = DecimalFormat("###")
    return numPattern.format(decimalNum)
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        .show()
}