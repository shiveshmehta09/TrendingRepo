package com.shivesh.trendingrepo.utils

import java.text.DecimalFormat

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class NumberFormatter {
    companion object {
        fun formatDecimalNum(decimalNum: Double): String {
            var numPattern = DecimalFormat("###")
            return numPattern.format(decimalNum)
        }
    }
}