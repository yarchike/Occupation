package com.yarchike.occupation

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun writeDateToSharePrefrens(sharedPref: SharedPreferences){
        var date = Calendar.getInstance(TimeZone.getTimeZone("UTC+0"))
        date.add(Calendar.DAY_OF_MONTH, 1)
        date.set(Calendar.HOUR_OF_DAY, 0)
        date.set(Calendar.MINUTE, 0)
        date.set(Calendar.SECOND, 0)
        date.set(Calendar.MILLISECOND, 0)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        var dateString = sdf.format(date.time)
        sharedPref.edit().putString(DATE_INPUT, dateString).apply()
    }
    fun nowDateAfterDate(sharedPref: SharedPreferences):Boolean{
        try{
        val dateString = sharedPref.getString(DATE_INPUT, "")
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val dateD = sdf.parse(dateString)
        var date = Calendar.getInstance();
        date.time = dateD
        val nowDate = Calendar.getInstance(TimeZone.getTimeZone(" UTC+0"))
            return !date.after(nowDate)
        }catch (e:Exception){
            return true
        }
    }

}