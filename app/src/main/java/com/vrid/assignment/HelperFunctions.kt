package com.vrid.assignment

import androidx.core.text.HtmlCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.htmlToString(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
}

fun String.toFormattedDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val date = inputFormat.parse(this)
    return outputFormat.format(date ?: Date())
}