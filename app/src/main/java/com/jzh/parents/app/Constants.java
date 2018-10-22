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
     * 跳转小程序的用户名
     */
    String WX_MINI_PROGRAM_USER_NAME = "gh_46f85bd327bb";

    /**
     * 跳转小程序的路径
     */
    String WX_MINI_PROGRAM_PATH = "/pages/live-home/home?source=1005&pos=list";

    /**
     * 微信授权登录的渠道Id
     */
    String WX_AUTHORIZE_CHANNEL_ID = "20";

    /**
     * 阿里云AppKey
     */
    String ALI_APP_KEY = "25048548";

    /**
     * 阿里云AppSecret
     */
    String ALI_APP_SECRET = "632b6bee8481fcb10c1d5e9b704c46a1";

    /**
     *
     */
    String STS_SERVER = "632b6bee8481fcb10c1d5e9b704c46a1";

    /**
     * 入学年份的起始年份
     */
    int MIN_YEAR_OF_LEANING = 1980;

    /**
     * Token失效
     */
    int TOKEN_EXCEPTION = 401;

    /**
     * 是否预约：否
     */
    int TYPE_SUBSCRIBE_NO = 0;

    /**
     * 是否预约：是
     */
    int TYPE_SUBSCRIBE_YES = 1;

    /**
     * 设备类型:安卓-2
     */
    String DEVICE_TYPE_ANDROID = "2";

    /**
     * 是否已收藏：否
     */
    int TYPE_FAVORITE_NO = 0;

    /**
     * 是否已收藏：是
     */
    int TYPE_FAVORITE_YES = 1;

    /**
     * 直播是VIP
     */
    int LIVE_IS_VIP = 1;

    /**
     * 首页显示即将播放的个数：2个
     */
    int HOME_LIVE_WILL_LIMIT = 2;

    /**
     * 首页精彩回放即将播放的个数：3个
     */
    int HOME_LIVE_REVIEW_LIMIT = 3;

    /**
     * 一页显示的个数：5个
     */
    int PAGE_CNT = 5;

    /**
     * 搜索关键词最大个数：5个
     */
    int MAX_KEY_WORD_CNT = 5;

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
     * wx的openId
     */
    String EXTRA_WX_OPEN_ID = "wx_open_id";

    /**
     * wx的预约的直播action
     */
    String EXTRA_WX_SUBSCRIBE_ACTION = "wx_subscribe_action";

    /**
     * wx的预约的直播openId
     */
    String EXTRA_WX_SUBSCRIBE_OPEN_ID = "wx_subscribe_open_id";

    /**
     * wx的预约的直播Id
     */
    String EXTRA_WX_SUBSCRIBE_SCENE = "wx_subscribe_scene";

    /**
     * 直播列表-状态类型
     */
    String EXTRA_LIVES_STATUS_TYPE = "lives_status_type";

    /**
     * 直播列表-分类类型
     */
    String EXTRA_LIVES_CATEGORY_ID = "lives_category_id";

    /**
     * 直播列表-分类名称
     */
    String EXTRA_LIVES_CATEGORY_NAME = "lives_category_name";

    /**
     * 直播列表-分类描述
     */
    String EXTRA_LIVES_CATEGORY_TIP = "lives_category_tip";

    /**
     * 我的列表-页面类型(我的收藏、我的预约)
     */
    String EXTRA_MY_LIVES_PAGE_TYPE = "lives_page_type";

    /**
     * 提示框标题
     */
    String EXTRA_TIP_DIALOG_TITLE = "tip_dialog_title";

    /**
     * 提示框内容
     */
    String EXTRA_TIP_DIALOG_CONTENT = "tip_dialog_content";

    /**
     * 版本升级
     */
    String EXTRA_UPDATE_VERSION_DIALOG_OBJ = "update_version_dialog_obj";

    /**
     * 注册学段
     */
    String EXTRA_REGISTER_SECTION = "register_section";

    /**
     * Url地址
     */
    public static final String EXTRA_PAGE_URL = "page_url";

    /**
     * 我的列表-页面类型-我的收藏
     */
    int MY_LIVES_PAGE_TYPE_FAVORITE = 1;

    /**
     * 我的列表-页面类型-我的预约
     */
    int MY_LIVES_PAGE_TYPE_SUBSCRIBE = 2;

    /**
     * wx的预约的直播action - 预约成功
     */
    String WX_SUBSCRIBE_ACTION_CONFIRM = "confirm";

    /**
     * wx的预约的直播action - 取消预约
     */
    String WX_SUBSCRIBE_ACTION_CANCEL = "cancel";

    /**
     * 新消息提醒
     */
    String ACTION_NEW_MSG = "com.jzh.parents.app.action.new_msg";

    /**
     * 用户信息变更了
     */
    String ACTION_USER_INFO_CHANGED = "com.jzh.parents.app.action.user_info_changed";

    /**
     * 直播被操作了
     */
    String ACTION_LIVE_OPERATED = "com.jzh.parents.app.action.live_operated";

    /**
     * 直播id
     */
    String EXTRA_LIVE_ID = "live_id";

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

    /**
     * 请求位置来源的app
     */
    int GET_UNKNOWN_APP_SOURCES = 100;

    /**
     * 通知渠道
     */
    String NOTIFICATION_CHANNEL_ID_LIVE = "live";

}
