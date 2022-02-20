/*
 * Created by Tedo Manvelidze(ted3x) on 2/17/22, 9:07 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/17/22, 9:07 PM
 */

package chat.revolt.core.extensions

import android.content.Context
import chat.revolt.core.R
import java.text.SimpleDateFormat
import java.util.*

private const val HOUR_FORMAT = "HH:mm"
private const val WEEK_DAY_WITH_HOUR_FORMAT = "EEEE 'at' HH:mm"
private const val DATE_FORMAT = "dd/mm/yyyy HH:mm"
private const val ONE_DAY_IN_MS = 86400000
private const val TWO_DAY_IN_MS = 172800000
private const val ONE_WEEK_IN_MS = 604800000
fun Long.toDate(context: Context): String {
    val date = Date(this)
    val current = System.currentTimeMillis()
    val sdf: SimpleDateFormat
    return when (current - this) {
        in 0 until ONE_DAY_IN_MS -> {
            sdf = SimpleDateFormat(HOUR_FORMAT, Locale.US)
            context.getString(R.string.date_today_at, sdf.format(date))
        }
        in ONE_DAY_IN_MS until TWO_DAY_IN_MS -> {
            sdf = SimpleDateFormat(HOUR_FORMAT, Locale.US)
            context.getString(R.string.date_yesterday_at, sdf.format(date))
        }
        in TWO_DAY_IN_MS..ONE_WEEK_IN_MS -> {
            sdf = SimpleDateFormat(WEEK_DAY_WITH_HOUR_FORMAT, Locale.US)
            context.getString(R.string.date_last_day_at, sdf.format(date))
        }
        else -> {
            sdf = SimpleDateFormat(DATE_FORMAT, Locale.US)
            sdf.format(date)
        }
    }
}

fun Long.toHour(): String? {
    val date = Date(this)
    return SimpleDateFormat(HOUR_FORMAT, Locale.US).format(date)
}