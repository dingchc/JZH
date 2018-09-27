package com.jzh.parents.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.jzh.parents.R;
import com.jzh.parents.app.Constants;
import com.jzh.parents.utils.AppLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信回调类
 *
 * @author ding
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);

        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);

        Intent intent = getIntent();
        api.handleIntent(intent, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        int result;

        AppLogger.i("* code=" + baseResp.errCode);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.wx_errcode_success;

                // 微信授权
                if (baseResp instanceof SendAuth.Resp) {
                    processWxAuthorize(baseResp);
                }
                // 预约直播
                else if (baseResp instanceof SubscribeMessage.Resp) {
                    processWxSubscribeResult(baseResp);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.wx_errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.wx_errcode_deny;
                break;
            default:
                result = R.string.wx_errcode_unknown;
                break;
        }

        this.finish();
    }

    /**
     * 处理微信授权登录
     *
     * @param baseResp 响应数据
     */
    private void processWxAuthorize(BaseResp baseResp) {

        if (baseResp != null) {

            try {
                String wxToken = ((SendAuth.Resp) baseResp).code;

                Intent intent = new Intent(Constants.ACTION_WX_AUTHORIZE);
                intent.putExtra(Constants.EXTRA_WX_TOKEN, wxToken);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                finish();

            } catch (Exception e) {
                AppLogger.i("error_msg:" + e.getMessage());
            }
        }

    }

    /**
     * 处理微信预约结果
     *
     * @param baseResp 响应数据
     */
    private void processWxSubscribeResult(BaseResp baseResp) {

        if (baseResp != null) {

            try {

                SubscribeMessage.Resp resp = ((SubscribeMessage.Resp) baseResp);

                Intent intent = new Intent(Constants.ACTION_WX_SUBSCRIBE);
                intent.putExtra(Constants.EXTRA_WX_SUBSCRIBE_ACTION, resp.action);
                intent.putExtra(Constants.EXTRA_WX_SUBSCRIBE_SCENE, resp.scene);
                intent.putExtra(Constants.EXTRA_WX_SUBSCRIBE_OPEN_ID, resp.openId);

                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                finish();

            } catch (Exception e) {
                AppLogger.i("error_msg:" + e.getMessage());
            }
        }

    }
}
