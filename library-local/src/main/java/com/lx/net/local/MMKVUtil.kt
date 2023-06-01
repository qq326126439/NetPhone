package com.lx.net.local

import com.tencent.mmkv.MMKV

object MMKVUtil {

    private val mmkv by lazy { MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null) }

    fun write(key: String?, value: Int) {
        mmkv.encode(key, value)
    }

    fun write(key: String?, value: Long) {
        mmkv.encode(key, value)
    }

    fun write(key: String?, value: String?) {
        mmkv.encode(key, value)
    }

    fun write(key: String?, value: Boolean) {
        mmkv.encode(key, value)
    }

    fun write(key: String?, value: ByteArray?) {
        mmkv.encode(key, value)
    }

    fun readInt(key: String?): Int {
        return readInt(key, 0)
    }

    fun readInt(key: String?, defaultValue: Int): Int {
        return mmkv.decodeInt(key, defaultValue)
    }

    fun readLong(key: String?): Long {
        return readLong(key, 0L)
    }

    fun readLong(key: String?, defaultValue: Long): Long {
        return mmkv.decodeLong(key, defaultValue)
    }

    fun readString(key: String?): String? {
        return readString(key, "")
    }

    fun readString(key: String?, defaultValue: String?): String? {
        return mmkv.decodeString(key, defaultValue)
    }

    fun readBoolean(key: String?): Boolean {
        return readBoolean(key, false)
    }

    fun readBoolean(key: String?, defaultValue: Boolean): Boolean {
        return mmkv.decodeBool(key, defaultValue)
    }

    fun readBytes(key: String?): ByteArray? {
        return mmkv.decodeBytes(key)
    }

    fun remove(key: String?) {
        mmkv.remove(key)
    }

    fun putStringSet(key: String?, values: Set<String?>?) {
        mmkv.putStringSet(key, values)
    }

    fun getStringSet(key: String?): Set<String?>? {
        return mmkv.getStringSet(key, null)
    }

}