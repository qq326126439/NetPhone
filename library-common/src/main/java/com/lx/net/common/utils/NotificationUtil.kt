package com.lx.net.common.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.lx.net.common.constant.Constants.DEFAULT_NOTIFICATION_ID

/**
 * @author : lxm
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022-7-30 15:52:37
 * @description ：通知工具
 */
object NotificationUtil {

    fun notify(context: Context, intent: PendingIntent, title: String, icon: Int, content: String, clickToCancel : Boolean) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder =
            NotificationCompat.Builder(context, context.packageName)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(clickToCancel) //设置点击通知栏消息后，通知消息自动消失
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知消息优先级
                .setVibrate(longArrayOf(0, 1000, 1000, 1000)) //通知栏消息震动
                .setContentIntent(intent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道---8.0必须设置Channel
            val notificationChannel = NotificationChannel(
                context.packageName,
                "saiwei",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification = builder.build()
        notificationManager.notify(DEFAULT_NOTIFICATION_ID, notification)
    }

}