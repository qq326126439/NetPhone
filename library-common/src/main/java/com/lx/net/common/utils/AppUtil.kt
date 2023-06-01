package com.lx.net.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.security.MessageDigest


class AppUtil private constructor() {


    init {
        throw Error("Do not need instantiate!")
    }

    companion object {

        private val TAG = AppUtil::class.java.simpleName


        /**
         * 得到软件版本号
         *
         * @param context 上下文
         * @return 当前版本Code
         */
        fun getVerCode(context: Context): Int {
            var verCode = -1
            try {
                verCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    context.packageManager
                        .getPackageInfo(
                            context.packageName,
                            0
                        ).longVersionCode.toInt()
                } else {
                    context.packageManager
                        .getPackageInfo(context.packageName, 0).versionCode
                }

            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return verCode
        }


        /**
         * 获取应用运行的最大内存
         *
         * @return 最大内存
         */
        val maxMemory: Long
            get() = Runtime.getRuntime().maxMemory() / 1024


        /**
         * 得到软件显示版本信息
         *
         * @param context 上下文
         * @return 当前版本信息
         */
        fun getVerName(context: Context): String {
            var verName = ""
            try {
                verName = context.packageManager
                    .getPackageInfo(context.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return verName
        }


        /**
         * 得到软件包信息
         *
         * @param context 上下文
         * @return 当前版本信息
         */
        fun getPackageInfo(context: Context): PackageInfo? {
            try {
                val packageManager = context.packageManager
                return packageManager.getPackageInfo(context.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

        fun getAppName(context: Context): String {
            var name = ""
            try {
                val applicationInfo = context.packageManager.getApplicationInfo(context.applicationInfo.packageName, 0)
                name = (context.packageManager.getApplicationLabel(applicationInfo)) as String
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return name
        }


        @SuppressLint("PackageManagerGetSignatures")
                /**
                 * 获取应用签名
                 *
                 * @param context 上下文
                 * @param pkgName 包名
                 * @return 返回应用的签名
                 */
        fun getSign(context: Context, pkgName: String): String? {
            return try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val pis = context.packageManager
                        .getPackageInfo(
                            pkgName,
                            PackageManager.GET_SIGNING_CERTIFICATES
                        )
                    hexDigest(
                        paramArrayOfByte = pis.signingInfo.apkContentsSigners.first().toByteArray()
                    )
                } else {
                    val pis = context.packageManager
                        .getPackageInfo(
                            pkgName,
                            PackageManager.GET_SIGNATURES
                        )
                    hexDigest(paramArrayOfByte = pis.signatures.first().toByteArray())
                }

            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }

        }

        /**
         * 将签名字符串转换成需要的32位签名
         *
         * @param paramArrayOfByte 签名byte数组
         * @return 32位签名字符串
         */
        private fun hexDigest(paramArrayOfByte: ByteArray): String {
            val hexDigits = charArrayOf(
                48.toChar(),
                49.toChar(),
                50.toChar(),
                51.toChar(),
                52.toChar(),
                53.toChar(),
                54.toChar(),
                55.toChar(),
                56.toChar(),
                57.toChar(),
                97.toChar(),
                98.toChar(),
                99.toChar(),
                100.toChar(),
                101.toChar(),
                102.toChar()
            )
            try {
                val localMessageDigest = MessageDigest.getInstance("MD5")
                localMessageDigest.update(paramArrayOfByte)
                val arrayOfByte = localMessageDigest.digest()
                val arrayOfChar = CharArray(32)
                var i = 0
                var j = 0
                while (true) {
                    if (i >= 16) {
                        return String(arrayOfChar)
                    }
                    val k = arrayOfByte[i].toInt()
                    arrayOfChar[j] = hexDigits[0xF and k.ushr(4)]
                    arrayOfChar[++j] = hexDigits[k and 0xF]
                    i++
                    j++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        /**
         * 设置蓝牙超时时间位永远可见
         */
        fun setDiscoverableTimeout(timeout: Int) {
            val adapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            try {
                val setDiscoverableTimeout =
                    BluetoothAdapter::class.java.getMethod(
                        "setDiscoverableTimeout",
                        Int::class.javaPrimitiveType
                    )
                setDiscoverableTimeout.isAccessible = true
                val setScanMode =
                    BluetoothAdapter::class.java.getMethod(
                        "setScanMode",
                        Int::class.javaPrimitiveType,
                        Int::class.javaPrimitiveType
                    )
                setScanMode.isAccessible = true
                setDiscoverableTimeout.invoke(adapter, timeout)
                setScanMode.invoke(
                    adapter,
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE,
                    timeout
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        /**
         * 获取设备的可用内存大小
         *
         * @param context 应用上下文对象context
         * @return 当前内存大小
         */
        fun getDeviceUsableMemory(context: Context): Int {
            val am = context.getSystemService(
                Context.ACTIVITY_SERVICE
            ) as ActivityManager
            val mi = ActivityManager.MemoryInfo()
            am.getMemoryInfo(mi)
            // 返回当前系统的可用内存
            return (mi.availMem / (1024 * 1024)).toInt()
        }


        fun getMobileModel(): String {
            var model: String? = Build.MODEL
            model = model?.trim { it <= ' ' } ?: ""
            return model
        }

        /**
         * 获取手机系统SDK版本
         *
         * @return 如API 17 则返回 17
         */
        val sdkVersion: Int
            get() = Build.VERSION.SDK_INT

        /**
         * 安装apk
         */
        fun installApk(filePath: String,activity: Activity) {
            Log.e(TAG, " installApk  $filePath")
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                val uri: Uri = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    FileProvider.getUriForFile(activity,
                        activity.packageName + ".provider",
                        File(filePath)
                    )
                } else {
                    Uri.fromFile(File(filePath))
                }
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.startActivity(intent)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e(TAG, " installApk  Exception " + e.localizedMessage)
            }
        }
    }



}