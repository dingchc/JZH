package com.jzh.parents.app

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import com.alibaba.sdk.android.push.MessageReceiver
import com.alibaba.sdk.android.push.notification.CPushMessage
import com.google.gson.reflect.TypeToken
import com.jzh.parents.datamodel.response.PushMessageRes
import com.jzh.parents.db.DbController
import com.jzh.parents.db.entry.MessageEntry
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

        // 内容不为空
        if (!TextUtils.isEmpty(message?.content)) {

            val pushMsg: PushMessageRes? = Util.fromJson(message?.content ?: "", object : TypeToken<PushMessageRes>() {
            }.type)

            pushMsg.let {

                pushMsg?.messageId = message?.messageId ?: ""

                DbController.INSTANCE.messageDb.insert(MessageEntry(messageId = pushMsg?.messageId ?: "", image = pushMsg?.image, title = pushMsg?.title, desc = pushMsg?.desc, type = pushMsg?.type ?: 0, appId = pushMsg?.appId, link = pushMsg?.link, startAt = pushMsg?.startAt ?: 0))

                // 提醒新消息
                notifyNewMsg()
            }
        }
    }

    override fun onNotification(context: Context?, title: String?, summary: String?, extraMap: MutableMap<String, String>?) {
        super.onNotification(context, title, summary, extraMap)

        AppLogger.i("* message=$title, summary=$summary")

    }

    /**
     * 提醒新消息
     */
    private fun notifyNewMsg() {

        val intent = Intent()
        intent.action = Constants.ACTION_NEW_MSG

        LocalBroadcastManager.getInstance(JZHApplication.instance!!).sendBroadcast(intent)


    }
}