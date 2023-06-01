package com.lx.net.common.netstate

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.lx.net.common.netstate.NetType
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/***********************************************************************
 * <p>@description: 网络工具类
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 16:11
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 16:11
 **********************************************************************/
object NetTool {

    fun getCurrentNetTypeName(context: Context): String {

        return when (getCurrentNetType(context)) {
            NetType.NET_UNKNOWN, NetType.NONE -> "未知"
            NetType.WIFI -> "WIFI"
            NetType.NET2G -> "2G"
            NetType.NET3G -> "3G"
            NetType.NET4G -> "4G"
            NetType.NET5G -> "5G"
            else -> "未知"
        }
    }

    /**
     * 或者当前网络状态
     *
     *
     * GPRS    2G(2.5) General Packet Radia Service 114kbps
     * EDGE    2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
     * UMTS    3G WCDMA 联通3G Universal MOBILE Telecommunication System 完整的3G移动通信技术标准
     * CDMA    2G 电信 Code Division Multiple Access 码分多址
     * EVDO_0  3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
     * EVDO_A  3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
     * 1xRTT   2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
     * HSDPA   3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
     * HSUPA   3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
     * HSPA    3G (分HSDPA,HSUPA) High Speed Packet Access
     * IDEN    2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
     * EVDO_B  3G EV-DO Rev.B 14.7Mbps 下行 3.5G
     * LTE     4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
     * EHRPD   3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
     * HSPAP   3G HSPAP 比 HSDPA 快些
     * /
     *
     * @param context
     * @return
     */
    fun getCurrentNetType(context: Context): NetType {
        //获得ConnectivityManager对象
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected) {
            return NetType.NONE
        }
        return if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
            NetType.WIFI
        } else when (networkInfo.type) {
            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> NetType.NET2G
            TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> NetType.NET3G
            TelephonyManager.NETWORK_TYPE_LTE -> NetType.NET4G
            TelephonyManager.NETWORK_TYPE_NR -> NetType.NET5G
            else -> NetType.NET_UNKNOWN
        }
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable && mNetworkInfo.isConnected
            }
        }
        return false
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo =
                mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable && mWiFiNetworkInfo.isConnected
            }
        }
        return false
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo =
                mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable && mMobileNetworkInfo.isConnected
            }
        }
        return false
    }

    /**
     * 判断设备 是否使用代理上网
     * */
    fun isWifiProxy(): Boolean {
        val proxyPort: Int
        val proxyAddress = System.getProperty("http.proxyHost")?.toString() ?:""
            val portStr = System.getProperty("http.proxyPort")
            proxyPort = (portStr ?: "-1").toInt()
        return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1
    }

    /**
     * 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap网络3：net网络
     *
     * @param context
     * @return
     */
    fun getAPNType(context: Context): Int {
        var netType = -1
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo ?: return netType
        val nType = networkInfo.type
        if (nType == ConnectivityManager.TYPE_MOBILE)
            netType = if (networkInfo.extraInfo.toLowerCase(Locale.ROOT) == "3gnet") 3 else 2
        else if (nType == ConnectivityManager.TYPE_WIFI) netType = 1
        return netType
    }


    /**
     * 获取指定路径，的数据。
     */
    @Throws(Exception::class)
    fun getImage(urlPath: String?): ByteArray? {
        val url = URL(urlPath)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connectTimeout = 6 * 1000
        // 别超过10秒。
        if (conn.responseCode == 200) {
            val inputStream = conn.inputStream
            return readStream(inputStream)
        }
        return null
    }

    /**
     * 读取数据 输入流
     */
    @Throws(Exception::class)
    fun readStream(inStream: InputStream): ByteArray {
        val outstream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len: Int
        while (inStream.read(buffer).also { len = it } != -1) {
            outstream.write(buffer, 0, len)
        }
        outstream.close()
        inStream.close()
        return outstream.toByteArray()
    }
}