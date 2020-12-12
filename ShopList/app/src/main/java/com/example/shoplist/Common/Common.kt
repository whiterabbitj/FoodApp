package com.example.shoplist.Common

import java.math.RoundingMode
import java.text.DecimalFormat

class Common {


    fun formatingDec():DecimalFormat{
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df
    }

    companion object {
        const val CURRENCY_USD:String  = " usd"
    }

}