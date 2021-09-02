package com.shivesh.trendingrepo.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class ViewUtils {
    companion object {
        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}