package com.jzh.parents.app

import android.content.Context
import com.alibaba.sdk.android.push.MessageReceiver
import com.alibaba.sdk.android.push.notification.CPushMessage
import com.jzh.parents.utils.AppLogger

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
    }

    override fun onNotification(context: Context?, title: String?, summary: String?, extraMap: MutableMap<String, String>?) {
        super.onNotification(context, title, summary, extraMap)

        AppLogger.i("* message=$title, summary=$summary")

    }
}