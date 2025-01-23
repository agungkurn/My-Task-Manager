package id.ak.mytaskmanager.common.extension

import java.text.NumberFormat
import java.util.Locale

fun Number.format(locale: Locale = Locale.getDefault()): String {
    val formatter = NumberFormat.getInstance(locale)
    return formatter.format(this)
}