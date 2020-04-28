package hr.tvz.android.racuni.shared

import android.content.DialogInterface
import hr.tvz.android.racuni.ui.troskovi.PATTERN
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        fun transformDate(dayOfMonth: Int, monthh: Int, yearr: Int): String? {
            val day: Int = dayOfMonth
            val month: Int = monthh
            val year: Int = yearr
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val sdf = SimpleDateFormat(PATTERN, Locale.getDefault())
            return sdf.format(calendar.time)
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->
        }
    }
}