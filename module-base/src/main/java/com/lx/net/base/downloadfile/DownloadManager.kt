package com.lx.net.base.downloadfile

import android.os.Environment
import com.lx.net.base.api.RetrofitService
import com.lx.net.base.downloadfile.HttpResult
import com.lx.net.base.downloadfile.fold
import com.lx.net.common.ext.getCachePath
import com.lx.net.common.toast.ToastUtils
import com.lx.net.common.utils.HseAlcImpl
import com.lx.net.common.utils.LogCompat.logI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream

typealias download_error = suspend (Throwable) -> Unit
typealias download_process = suspend (downloadedSize: Long, length: Long, progress: Float,pos: Int) -> Unit
typealias download_success = suspend (uri: File) -> Unit

object DownloadManager {

    @JvmOverloads
    suspend fun download(
        url: String, fileName: String,
        onError: download_error = {},
        onProcess: download_process = { _, _, _, _-> },
        onSuccess: download_success = { },
        pos: Int = -1  //用于在适配器每个item更新进度
    ) {
        flow {
            try {
                val body = RetrofitService.getApiService().downloadFile(url)
                val contentLength = body.contentLength()
                val inputStream = body.byteStream()

                val pathName = "${HseAlcImpl.getApplication().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}${File.separator}$fileName"

                val file = File(pathName)
                if (!file.exists()) {
                    val fileResult = file.createNewFile()
                    (" createNewFile file $fileResult --- ${file.absolutePath}").logI("DownloadManager")
                    if(!fileResult){
                        ToastUtils.show("创建文件路径失败")
                        return@flow
                    }
                }
                val outputStream = FileOutputStream(file)
                var currentLength = 0
                val bufferSize = 1024 * 8
                val buffer = ByteArray(bufferSize)
                val bufferedInputStream = BufferedInputStream(inputStream, bufferSize)
                var readLength: Int
                while (bufferedInputStream.read(buffer, 0, bufferSize)
                        .also { readLength = it } != -1
                ) {
                    outputStream.write(buffer, 0, readLength)
                    currentLength += readLength
                    emit(
                        HttpResult.progress(
                            currentLength.toLong(),
                            contentLength,
                            currentLength.toFloat() / contentLength.toFloat() * 100
                        )
                    )
                }
                bufferedInputStream.close()
                outputStream.close()
                inputStream.close()
                emit(HttpResult.success(file))
            } catch (e: Exception) {
                emit(HttpResult.failure(e))
            }
        }.flowOn(Dispatchers.IO)
            .collect {
                it.fold(onFailure = { e ->
                    e?.let { it1 -> onError(it1) }
                }, onSuccess = { file ->
                    onSuccess(file)
                }, onLoading = { progress ->
                    onProcess(progress.currentLength, progress.length, progress.process, pos)
                })
            }
    }

    @JvmOverloads
    suspend fun downloadApk(
        url: String, fileName: String,
        onError: download_error = {},
        onProcess: download_process = { _, _, _, _-> },
        onSuccess: download_success = { },
        pos: Int = -1
    ) {
        flow {
            try {
                val body = RetrofitService.getApiService().downloadFile(url)
                val contentLength = body.contentLength()
                val inputStream = body.byteStream()

                val pathName = "${HseAlcImpl.getApplication().getCachePath()}${File.separator}$fileName"

                val file = File(pathName)
                if (!file.exists()) {
                    val fileResult = file.createNewFile()
                    (" createNewFile file $fileResult --- ${file.absolutePath}").logI("DownloadManager")
                    if(!fileResult){
                        ToastUtils.show("创建文件路径失败")
                        return@flow
                    }
                }
                val outputStream = FileOutputStream(file)
                var currentLength = 0
                val bufferSize = 1024 * 8
                val buffer = ByteArray(bufferSize)
                val bufferedInputStream = BufferedInputStream(inputStream, bufferSize)
                var readLength: Int
                while (bufferedInputStream.read(buffer, 0, bufferSize)
                        .also { readLength = it } != -1
                ) {
                    outputStream.write(buffer, 0, readLength)
                    currentLength += readLength
                    emit(
                        HttpResult.progress(
                            currentLength.toLong(),
                            contentLength,
                            currentLength.toFloat() / contentLength.toFloat() * 100
                        )
                    )
                }
                bufferedInputStream.close()
                outputStream.close()
                inputStream.close()
                emit(HttpResult.success(file))
            } catch (e: Exception) {
                emit(HttpResult.failure(e))
            }
        }.flowOn(Dispatchers.IO)
            .collect {
                it.fold(onFailure = { e ->
                    e?.let { it1 -> onError(it1) }
                }, onSuccess = { file ->
                    onSuccess(file)
                }, onLoading = { progress ->
                    onProcess(progress.currentLength, progress.length, progress.process,pos)
                })
            }
    }
}
