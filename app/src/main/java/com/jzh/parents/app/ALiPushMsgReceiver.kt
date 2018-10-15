package com.jzh.parents.app

import android.content.Context
import android.text.TextUtils
import com.alibaba.sdk.android.push.MessageReceiver
import com.alibaba.sdk.android.push.notification.CPushMessage
import com.google.gson.reflect.TypeToken
import com.jzh.parents.datamodel.response.PushMessageRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util

/**
 * 阿里消息推送
 *
 * @author ding
 * Created by Ding on 2018/10/13.
 */
class ALiPushMsgReceiver : MessageReceiver() {

    override fun onMessage(context: Context?, message: CPushMessage?) {
        super.onMessage(context, message)

        AppLogger.i("* message ${message?.title}, ${message?.content}")

        // 内容不为空
        if (TextUtils.isEmpty(message?.content)) {

            val pushMsg: PushMessageRes? = Util.fromJson(message?.content ?: "", object : TypeToken<PushMessageRes>() {
            }.type)
        }
    }

    override fun onNotification(context: Context?, title: String?, summary: String?, extraMap: MutableMap<String, String>?) {
        super.onNotification(context, title, summary, extraMap)

        AppLogger.i("* message=$title, summary=$summary")

    }
}