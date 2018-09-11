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
     * 成功
     */
    int RET_CODE_OK = 0;

    /**
     * wx的token
     */
    String EXTRA_WX_TOKEN = "wx_token";

}
