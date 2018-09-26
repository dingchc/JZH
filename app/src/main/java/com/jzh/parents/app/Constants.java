package com.jzh.parents.app;

/**
 * 常量
 *
 * @author Ding
 *         Created by Ding on 2018/9/9.
 */

public interface Constants {


    /**
     * 微信AppId
     */
    String WX_APP_ID = "wx16c33bca2ad8b1bd";

    /**
     * 微信AppSecret
     */
    String WX_APP_SECRET = "9965a66de900edb09b08b97d7111fa0d";

    /**
     * 微信未安装
     */
    int RET_CODE_WX_IS_NOT_INSTALL = -1000;

    /**
     * 入学年份的起始年份
     */
    int MIN_YEAR_OF_LEANING = 1980;

    /**
     * 请求成功
     */
    int REQ_CODE_OK = 200;

    /**
     * 成功
     */
    int RET_CODE_OK = 0;

    /**
     * 是否预约：否
     */
    int TYPE_SUBSCRIBE_NO = 0;

    /**
     * 是否预约：是
     */
    int TYPE_SUBSCRIBE_YES = 1;

    /**
     * 是否已收藏：否
     */
    int TYPE_FAVORITE_NO = 0;

    /**
     * 是否已收藏：是
     */
    int TYPE_FAVORITE_YES = 1;

    /**
     * 首页显示即将播放的个数：2个
     */
    int HOME_LIVE_WILL_LIMIT = 2;

    /**
     * 首页精彩回放即将播放的个数：3个
     */
    int HOME_LIVE_REVIEW_LIMIT = 3;

    /**
     * 一页显示的个数：10个
     */
    int PAGE_CNT = 10;

    /**
     * wx的授权登录的action
     */
    String ACTION_WX_AUTHORIZE = "com.jzh.parents.app.action.wx_authorize";

    /**
     * wx的预约的action
     */
    String ACTION_WX_SUBSCRIBE = "com.jzh.parents.app.action.wx_subscribe";

    /**
     * wx的token
     */
    String EXTRA_WX_TOKEN = "wx_token";

    /**
     * wx的预约的直播action
     */
    String EXTRA_WX_SUBSCRIBE_ACTION = "wx_subscribe_action";

    /**
     * wx的预约的直播Id
     */
    String EXTRA_WX_SUBSCRIBE_SCENE = "wx_subscribe_scene";

    /**
     * wx的预约的直播action - 预约成功
     */
    String WX_SUBSCRIBE_ACTION_CONFIRM = "confirm";

    /**
     * wx的预约的直播action - 取消预约
     */
    String WX_SUBSCRIBE_ACTION_CANCEL = "cancel";

    /**
     * 拍照
     */
    int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    /**
     * 相册
     */
    int PHONE_ALBUM_REQUEST_CODE = CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE + 1;

    /**
     * 裁剪图片
     */
    int CROP_IMAGE_REQUEST_CODE = PHONE_ALBUM_REQUEST_CODE + 1;

    /**
     * 文件前缀
     */
    String FILE_NAME_PREFIX = "jzh";

    /**
     * Android N provider authority
     */
    String PROVIDER_AUTHORIZE = "com.jzh.parents.authority";


    /**
     * 短信验证码计时间隔：60秒
     */
    int SMS_INTERVAL_TIME = 60;

    /**
     * 微信预约的模板
     */
    String WX_SUBSCRIBE_TEMPLATE_ID = "qhcWtTQswPB0j3oVHgW_I_pyKLurGcw8k-wB4Kt20aw";

}
