package com.lx.net.common.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author : lxm
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2021/4/14 19:08
 * @description ：日期工具
 */
class DateUtil {

    companion object {

        private val TAG = DateUtil::class.java.simpleName
        const val YY = "yyyy"
        const val HH_MM_SS = "HH:mm:ss"
        const val HH_MM = "HH:mm"
        const val MM_SS = "mm:ss"

        //2022-08-05 18:05:09
        const val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
        const val YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"
        const val YYYY_MM_DD = "yyyy-MM-dd"
        const val MM_DD_HH_MM_SS = "MM-dd HH:mm:ss"

        /**
         * 日期格式化对象
         */
        @SuppressLint("SimpleDateFormat")
        fun formatDate(format: String) = SimpleDateFormat(format)

        //消息页面当前显示为 18:05
        fun getMsgStyle(date: String?): String? {

            val now = Calendar.getInstance()
            if (!date.isNullOrEmpty()) {
                val dbDate = Calendar.getInstance()
                dbDate.time = formatDate(YYYY_MM_DD_HH_MM_SS).parse(date) as Date

                //今天
                if (dbDate.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {

                    if (dbDate.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {

                        var min = dbDate.get(Calendar.MINUTE).toString()
                        if (min.length == 1) {
                            min = "0$min"
                        }
                        return "" + dbDate.get(Calendar.HOUR_OF_DAY) + ":" + min
                    }

                    //昨天和前天
                    if (now.get(Calendar.DAY_OF_YEAR) - dbDate.get(Calendar.DAY_OF_YEAR) == 1) {
                        return "昨天"
                    }

                    //昨天和前天
                    if (now.get(Calendar.DAY_OF_YEAR) - dbDate.get(Calendar.DAY_OF_YEAR) == 2) {
                        return "前天"
                    }

                    //昨天和前天
                    if (now.get(Calendar.DAY_OF_YEAR) - dbDate.get(Calendar.DAY_OF_YEAR) > 2) {
                        return "" + (dbDate.get(Calendar.MONTH) + 1) + "月" + dbDate.get(Calendar.DAY_OF_MONTH) + "日"
                    }

                } else {
                    return date
                }
            }
            return null
        }

        //消息页面当前显示为 18:05
        fun getFindStyle(date: String?): String? {

            val now = Calendar.getInstance()
            if (!date.isNullOrEmpty()) {
                val dbDate = Calendar.getInstance()
                dbDate.time = formatDate(YYYY_MM_DD_HH_MM_SS).parse(date) as Date
                val hour = dbDate.get(Calendar.HOUR_OF_DAY).toString()
                var min = dbDate.get(Calendar.MINUTE).toString()
                //今天
                if (dbDate.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {

                    if (dbDate.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {

                        if (min.length == 1) {
                            min = "0$min"
                        }
                        return "$hour:$min"
                    }

                    //昨天和前天
                    if (now.get(Calendar.DAY_OF_YEAR) - dbDate.get(Calendar.DAY_OF_YEAR) == 1) {
                        if (min.length == 1) {
                            min = "0$min"
                        }
                        return "昨天  $hour:$min"
                    }

                    //昨天和前天
                    if (now.get(Calendar.DAY_OF_YEAR) - dbDate.get(Calendar.DAY_OF_YEAR) == 2) {
                        if (min.length == 1) {
                            min = "0$min"
                        }
                        return "前天  $hour:$min"
                    }

                    //昨天和前天
                    if (now.get(Calendar.DAY_OF_YEAR) - dbDate.get(Calendar.DAY_OF_YEAR) > 2) {
                        if (min.length == 1) {
                            min = "0$min"
                        }
                        return "" + (dbDate.get(Calendar.MONTH) + 1) + "月" + dbDate.get(Calendar.DAY_OF_MONTH) + "日" + "  $hour:$min"
                    }

                } else {
                    return date
                }
            }
            return null
        }


        /**
         * 将字符串yyyy-MM-dd HH:mm:ss转换为日期格式时间
         */
        fun strToDate(str: String, pattern: String = YYYY_MM_DD_HH_MM_SS): Date? {
            @SuppressLint("SimpleDateFormat") val formatter =
                SimpleDateFormat(pattern)
            try {
                val time = when (str.length) {
                    16 -> "${str}:00"
                    else -> str
                }
                return formatter.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 将字符串yyyy-MM-dd转换为日期格式时间
         */
        fun strToDateYYYYMMDD(str: String, pattern: String = YYYY_MM_DD): Date? {
            @SuppressLint("SimpleDateFormat") val formatter =
                SimpleDateFormat(pattern)
            try {
                return formatter.parse(str)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 将日期格式时间转换为字符串
         */
        fun strToStrFormat(str: String?, pattern: String = YYYY_MM_DD_HH_MM): String? {
            str?.let {
                val date = strToDate(it, pattern)
                return date?.let { dateToStrTime(it, pattern) }
            }
            return dateToStrTime(Date(), pattern)
        }


        /**
         * 将日期格式时间转换为yyyy-MM-dd
         */
        fun strToStrYYYYMMDD(str: String?, pattern: String = YYYY_MM_DD): String? {
            str?.let {
                val date = strToDate(it)
                return date?.let { dateToStrTime(it, pattern) }
            }
            return dateToStrTime(Date(), pattern)
        }

        /**
         * 将日期格式时间转换为字符串 HH:mm:ss
         */
        fun dateToStrTime(date: Date, pattern: String = HH_MM_SS): String? {
            @SuppressLint("SimpleDateFormat") val formatter =
                SimpleDateFormat(pattern)
            return formatter.format(date)
        }

        /**
         * 将日期格式时间转换为字符串 HH:mm:ss
         */
        fun dateToYYYYMMDD(date: Date, pattern: String = YYYY_MM_DD): String? {
            @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat(pattern)
            return formatter.format(date)
        }

        /**
         * 将日期格式时间转换为字符串 HH:mm:ss
         */
        fun dateToYYYYMMDDHHMM(date: Date, pattern: String = YYYY_MM_DD_HH_MM): String? {
            @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat(pattern)
            return formatter.format(date)
        }

        /**
         * 将日期格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
         */
        fun dateToStr(date: Date): String {
            @SuppressLint("SimpleDateFormat") val formatter =
                SimpleDateFormat(YYYY_MM_DD_HH_MM_SS)
            return formatter.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun timeToDate(timeMiles: Long, pattern: String = YYYY_MM_DD_HH_MM_SS): String? {
            val simpleDateFormat =
                SimpleDateFormat(pattern)
            val date = Date(timeMiles)
            return simpleDateFormat.format(date)
        }

        /**
         * 通过时间秒毫秒数判断两个时间的间隔
         * @param date1
         * @param date2
         * @return
         */
        fun differentDayHours(date1: Date, date2: Date): Int {
            return ((date2.time - date1.time) / (1000 * 3600)).toInt()
        }

        @SuppressLint("SimpleDateFormat")
        fun getTime(day: Int): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -day) //向前走一天
            val date = calendar.time
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            return sdf.format(date) + " 00:00:00"
        }

        @SuppressLint("SimpleDateFormat")
        fun getTimes(day: Int): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -day) //向前走一天
            val date = calendar.time
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            return sdf.format(date) + " 23:59:59"
        }

        fun getCurrentDate(pattern: String = YYYY_MM_DD_HH_MM_SS): String? {
            return timeToDate(timeMiles = System.currentTimeMillis(), pattern = pattern)
        }

        fun generateTime(millisecond: Long): String {
            return generateTime(millisecond, false)
        }

        fun generateTime(millisecond: Long, fit: Boolean): String {
            val totalSeconds = (millisecond / 1000L).toInt()
            val seconds = totalSeconds % 60
            val minutes = totalSeconds / 60 % 60
            val hours = totalSeconds / 3600
            return if (!fit && hours <= 0) String.format(
                Locale.getDefault(), "%02d:%02d", Integer.valueOf(minutes), Integer.valueOf(seconds)
            ) else String.format(
                Locale.getDefault(), "%02d:%02d:%02d",
                Integer.valueOf(hours),
                Integer.valueOf(minutes),
                Integer.valueOf(seconds)
            )
        }

        /**
         * 将指定时间增加 小时或者天
         *
         * @return Date 日期 ,转换异常时返回null。
         */
        fun addDate(date: Date?, day: Int, type: Int): Date? {
            var dates = date ?: return null
            val cal = Calendar.getInstance()
            cal.time = dates
            if (type == 1) {
                cal.add(Calendar.HOUR_OF_DAY, day) //24小时制
            } else if (type == 2) {
                cal.add(Calendar.DAY_OF_MONTH, day) //24小时制
            }
            dates = cal.time
            return dates
        }

        fun strToDay(str: String, format: Int): Int {
            if (str.isNotEmpty()) {
                val date = Calendar.getInstance()
                date.time = formatDate(YYYY_MM_DD).parse(str) as Date
                return date.get(format)
            }
            return -1
        }

        fun isSameDay(str: String, year: Int, month: Int, day: Int): Boolean {
            if (str.isNotEmpty()) {
                val date = Calendar.getInstance()
                date.time = formatDate(YYYY_MM_DD).parse(str) as Date

                return (date.get(Calendar.YEAR) == year &&
                        date.get(Calendar.MONTH) + 1 == month &&
                        date.get(Calendar.DATE) == day)
            }
            return false
        }


        fun getDatePoor(endDate: Date, nowDate: Date): String {
            val nd = (1000 * 24 * 60 * 60).toLong()
            val nh = (1000 * 60 * 60).toLong()
            val nm = (1000 * 60).toLong()
            // long ns = 1000;
            // 获得两个时间的毫秒时间差异
            val diff = endDate.time - nowDate.time
            // 计算差多少天
            val day = diff / nd
            // 计算差多少小时
            val hour = diff % nd / nh
            // 计算差多少分钟
            val min = diff % nd % nh / nm
            // 计算差多少秒//输出结果
            // long sec = diff % nd % nh % nm / ns;
            var content = ""
            if (day > 0) {
                content = content + day.toString() + "天"
            }
            if (hour > 0) {
                content = content + hour + "小时"
            }
            if (min > 0) {
                content = content + min + "分钟"
            }
            return content
        }

        fun getAddZero(value: Int): String {
            return if (value < 10) {
                "0${value}"
            } else {
                value.toString()
            }
        }

    }


}