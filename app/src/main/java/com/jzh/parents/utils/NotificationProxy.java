package com.jzh.parents.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.jzh.parents.R;
import com.jzh.parents.activity.HomeActivity;
import com.jzh.parents.app.Constants;
import com.jzh.parents.app.JZHApplication;


/**
 * 客户端通知中心
 *
 * @author Ding
 *         Created by ding on 12/23/15.
 */
public class NotificationProxy {

    public final static int NOTIFICATION_ID = 1000;

    private static Context mContext;

    public NotificationProxy(Context context) {
        mContext = context;
    }

    /**
     * 显示通知
     *
     * @param title   标题
     * @param content 内容
     */
    public void showNotification(int notificationId, String title, String content) {

        showNotification(notificationId, title, content, 0);
    }

    /**
     * 显示通知
     *
     * @param title        标题
     * @param content      内容
     * @param iconResource icon资源id
     */
    public void showNotification(int notificationId, String title, String content, int iconResource) {

        Bitmap bitmap = null;

        // Icon
        if (iconResource > 0) {
            try {
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), iconResource);
            } catch (Exception e) {
                e.printStackTrace();
            }

            showNotification(notificationId, title, content, bitmap);
        }


    }

    /**
     * 显示通知
     *
     * @param title   标题
     * @param content 内容
     * @param bitmap  图片
     */
    public void showNotification(int notificationId, String title, String content, Bitmap bitmap) {

        showNotification(notificationId, title, content, bitmap, null);
    }

    /**
     * 显示通知
     *
     * @param notificationId 通知id
     * @param title          标题
     * @param content        内容
     * @param bitmap         图片
     */
    public void showNotification(int notificationId, String title, String content, Bitmap bitmap, Intent intent) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, Constants.NOTIFICATION_CHANNEL_ID_LIVE);

        // 标题
        if (!TextUtils.isEmpty(title)) {
            mBuilder.setContentTitle(title);
        }

        // 内容
        if (!TextUtils.isEmpty(content)) {
            mBuilder.setContentText(content);
        }

        // Icon
        if (bitmap != null) {
            mBuilder.setLargeIcon(bitmap);
        }
        else {
            JZHApplication app = JZHApplication.Companion.getInstance();
            if (app != null) {
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(app.getResources(), R.mipmap.ic_app_round));
            }
        }

        mBuilder.setAutoCancel(true).setDefaults(NotificationCompat.DEFAULT_ALL).setWhen(System.currentTimeMillis()).setSmallIcon(R.mipmap.notify_icon_status_bar);

        if (intent == null) {
            intent = new Intent(mContext, HomeActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(
                mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            PendingIntent screenIntent = PendingIntent.getActivity(
                    mContext, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setFullScreenIntent(screenIntent, false);
        }

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {

            tipOpenNotification(notificationManager);
            notificationManager.notify(notificationId, mBuilder.build());
        }
    }

    /**
     * 提示用户打开通知
     *
     * @param notificationManager 通知manager
     */
    private void tipOpenNotification(NotificationManager notificationManager) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            JZHApplication app = JZHApplication.Companion.getInstance();

            if (app != null) {
                NotificationChannel channel = notificationManager.getNotificationChannel(Constants.NOTIFICATION_CHANNEL_ID_LIVE);
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, Util.Companion.getPackageName(app));
                    intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                    app.startActivity(intent);
                }
            }

        }
    }

    /**
     * 创建通知渠道
     */
    public static void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            JZHApplication app = JZHApplication.Companion.getInstance();

            if (app != null) {

                NotificationManager notificationManager = (NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
                String channelId = Constants.NOTIFICATION_CHANNEL_ID_LIVE;

                CharSequence name = "讲座";
                String description = "直播讲座";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(channelId, name, importance);

                channel.setDescription(description);
                channel.setShowBadge(true);
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }
        }
    }

    /**
     * 清除通知栏显示
     */
    public static void clearNotification() {

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }


    /**
     * 根据ID清除通知栏显示
     *
     * @param notificationId 通知ID
     */
    public static void clearNotificationByID(int notificationId) {

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);

    }
}
