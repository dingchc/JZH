package com.jzh.parents.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jzh.parents.R;
import com.jzh.parents.activity.LoginActivity;
import com.jzh.parents.app.Constants;
import com.jzh.parents.utils.AppLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
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

        AppLogger.i("code=" + baseResp.errCode);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.wx_errcode_success;
                goLoginPageWithToken(baseResp);
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

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        this.finish();
    }

    /**
     * 回到登录页面
     * @param baseResp 响应数据
     */
    private void goLoginPageWithToken(BaseResp baseResp) {

        if (baseResp != null) {

            try {
                String wxToken = ((SendAuth.Resp) baseResp).code;

                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra(Constants.EXTRA_WX_TOKEN, wxToken);
                startActivity(intent);

            } catch (Exception e) {
                AppLogger.i("error_msg:" + e.getMessage());
            }
        }

    }
}
