package com.jzh.parents.datamodel.remote

import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.OSSLog
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.model.OSSRequest
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import com.jzh.parents.R
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.io.File
import java.util.*

/**
 * 编辑个人信息
 *
 * @author ding
 * Created by Ding on 2018/9/27.
 */
class MyselfEditRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 获取OSS配置
     */
    fun initOSSConfig() {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("type", "headimg")

        TSHttpController.INSTANCE.doGet(Api.URL_API_GET_OSS_CONFIG, paramsMap, object : TSHttpCallback {

            override fun onSuccess(response: TSBaseResponse?, json: String?) {

                AppLogger.i("json = $json")

            }

            override fun onException(e: Throwable?) {

                AppLogger.i("errorMsg = ${e?.message}")
            }
        })
    }

    fun initOSS(endpoint: String, localFile: String, bucket: String) {


        val credentialProvider: OSSCredentialProvider

        credentialProvider = OSSAuthCredentialsProvider(Constants.STS_SERVER)

        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000
        conf.socketTimeout = 15 * 1000
        conf.maxConcurrentRequest = 5
        conf.maxErrorRetry = 2
        val oss = OSSClient(JZHApplication.instance, endpoint, credentialProvider, conf)
        OSSLog.enableLog()

        asyncPutImage("headimg", localFile, bucket, oss)

    }

    /**
     * 上传文件
     */
    fun asyncPutImage(key: String, localFile: String, bucket: String, oss: OSSClient) {
        val upload_start = System.currentTimeMillis()


        val file = File(localFile)
        if (!file.exists()) {
            AppLogger.w("AsyncPutImage", "FileNotExist")
            return
        }


        // 构造上传请求
        val put = PutObjectRequest(bucket, key, localFile)
        put.crC64 = OSSRequest.CRC64Config.YES

        val task = oss.asyncPutObject(put, object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
            override fun onSuccess(request: PutObjectRequest, result: PutObjectResult) {
                AppLogger.d("PutObject", "UploadSuccess")

                AppLogger.d("RequestId", result.requestId)

                val upload_end = System.currentTimeMillis()
                OSSLog.logDebug("upload cost: " + (upload_end - upload_start) / 1000f)
            }

            override fun onFailure(request: PutObjectRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                var info = ""
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace()
                }
            }
        })

    }
}