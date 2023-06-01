package com.lx.net.base.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/***********************************************************************
 * <p>@description:
 * <p>@author: pengl
 * <p>@created on: 2022/8/20 0020 16:47
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/20 0020 16:47
 **********************************************************************/
interface IDownloadFileService {

    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String?): ResponseBody
}