package com.example.pocketgrow.helper

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.Locale

fun String.ifEmailValid(): Boolean  {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun dateFormat(date: String): String? {
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val output = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val parseDate = date.let { input.parse(it) }

    return parseDate?.let { output.format(it) }
}