/*
 * Created by Tedo Manvelidze(ted3x) on 2/17/22, 9:07 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/17/22, 9:07 PM
 */

package chat.revolt.core.extensions

import android.content.Context
import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import chat.revolt.core.R
import java.text.SimpleDateFormat
import java.util.*

private const val HOUR_FORMAT = "HH:mm"
private const val WEEK_DAY_WITH_HOUR_FORMAT = "EEEE 'at' HH:mm"
private const val DATE_FORMAT = "dd/MM/yyyy HH:mm"

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun Long.toDate(context: Context): String {
    val date = Date(this)
    val current = System.currentTimeMillis()
    val sdf: SimpleDateFormat
    DateUtils.isToday(current)
    return when {
        DateUtils.isToday(this) -> {
            sdf = SimpleDateFormat(HOUR_FORMAT, Locale.US)
            context.getString(R.string.date_today_at, sdf.format(date))
        }
        isYesterday(this) -> {
            sdf = SimpleDateFormat(HOUR_FORMAT, Locale.US)
            context.getString(R.string.date_yesterday_at, sdf.format(date))
        }
        (current - this) <= DateUtils.WEEK_IN_MILLIS -> {
            sdf = SimpleDateFormat(WEEK_DAY_WITH_HOUR_FORMAT, Locale.US)
            context.getString(R.string.date_last_day_at, sdf.format(date))
        }
        else -> {
            sdf = SimpleDateFormat(DATE_FORMAT, Locale.US)
            sdf.format(date)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun isYesterday(millis: Long): Boolean {
    return DateUtils.isToday(millis + DateUtils.DAY_IN_MILLIS)
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun isLastWeek(millis: Long): Boolean {
    return DateUtils.isToday(millis + DateUtils.WEEK_IN_MILLIS)
}

fun Long.toHour(): String? {
    val date = Date(this)
    return SimpleDateFormat(HOUR_FORMAT, Locale.US).format(date)
}