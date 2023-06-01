package com.lx.net.base.ext

import com.lx.net.common.constant.Constants.NULL_CHARACTER

/**
 * NullSafetyTypesExt.kt
 * 安全的基础类型null处理
 */
fun Int?.getOrDefault(defaultValue: Int = 0) = this ?: defaultValue
fun Long?.getOrDefault(defaultValue: Long = 0L) = this ?: defaultValue
fun Short?.getOrDefault(defaultValue: Short = 0) = this ?: defaultValue
fun Float?.getOrDefault(defaultValue: Float = 0f) = this ?: defaultValue
fun Double?.getOrDefault(defaultValue: Double = 0.0) = this ?: defaultValue
fun Boolean?.getOrDefault(defaultValue: Boolean = false) = this ?: defaultValue
fun String?.getOrDefault(defaultValue: String = NULL_CHARACTER) = this ?: defaultValue