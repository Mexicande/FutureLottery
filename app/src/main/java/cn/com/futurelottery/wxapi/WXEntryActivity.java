package cn.com.futurelottery.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.ui.activity.BindPhoneActivity;
import cn.com.futurelottery.ui.activity.MainActivity;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.SPUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.progressdialog.KProgressHUD;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;


    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private KProgressHUD hud;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Contacts.WeChat.WX_APP_ID, false);
        api.handleIntent(getIntent(), this);

        /*
        launchBtn = (Button) findViewById(R.id.launch_wx_btn);
        launchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(WXEntryActivity.this, "launch result = " + api.openWXApp(), Toast.LENGTH_LONG).show();
			}
		});
        
        checkBtn = (Button) findViewById(R.id.check_timeline_supported_btn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int wxSdkVersion = api.getWXAppSupportAPI();
				if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
					Toast.makeText(WXEntryActivity.this, "wxSdkVersion = " + Integer.toHexString(wxSdkVersion) + "\ntimeline supported", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(WXEntryActivity.this, "wxSdkVersion = " + Integer.toHexString(wxSdkVersion) + "\ntimeline not supported", Toast.LENGTH_LONG).show();
				}
			}
		});
        
        api.handleIntent(getIntent(), this);*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                //goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        if(resp.getType()==RETURN_MSG_TYPE_SHARE){
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = R.string.errcode_success;
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = R.string.errcode_cancel;
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = R.string.errcode_deny;
                    break;
                default:
                    result = R.string.errcode_unknown;
                    break;
            }
        }else if (resp.getType()==RETURN_MSG_TYPE_LOGIN){
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //拿到了微信返回的code,立马再去请求access_token
                    String code = ((SendAuth.Resp) resp).code;
                    getWX(code);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = R.string.errcode_login_cancel;
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = R.string.errcode_login_deny;
                    break;
                default:
                    result = R.string.errcode_login_unknown;
                    break;
            }
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        finish();
    }

    /**
     * 获取access_token
     * @param code
     */
    private void getWX(String code) {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("登录中...")
                .setDimAmount(0.5f)
                .show();
        String url  = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Contacts.WeChat.WX_APP_ID
                + "&secret="
                + Contacts.WeChat.SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String access_token = jsonObject.getString("access_token");
                            String openid = jsonObject.getString("openid");
                            String expires_in = jsonObject.getString("expires_in");
                            String refresh_token = jsonObject.getString("refresh_token");
                            getInformation(access_token,openid,expires_in);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ToastUtils.showToast("登录失败");
                        hud.dismiss();
                        finish();
                    }
                });
    }

    /**
     * 获取用户个人信息
     * @param access_token
     * @param openid
     */
    private void getInformation(final String access_token, String openid, final String expires_in) {
        String url  = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String nickName = jsonObject.getString("nickname");
                            String sex = jsonObject.getString("sex");
                            String province = jsonObject.getString("province");
                            String city = jsonObject.getString("city");
                            String country = jsonObject.getString("country");
                            String headimgurl = jsonObject.getString("headimgurl");
                            String privilege = jsonObject.getString("privilege");
                            String unionid = jsonObject.getString("unionid");

                            login(nickName,headimgurl,access_token,expires_in,unionid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ToastUtils.showToast("登录失败");
                        hud.dismiss();
                        finish();
                    }
                });
    }


    private void login(String nickName, String headimgurl, String access_token, String expires_in, final String unionid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", nickName);
            jsonObject.put("head_img", headimgurl);
            jsonObject.put("access_token", access_token);
            jsonObject.put("expires_date", expires_in);
            jsonObject.put("openid", unionid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.Login.wlogin, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject jo = data.getJSONObject("data");
                    if ("0".equals(jo.getString("isbind"))){
                        Intent intent=new Intent(WXEntryActivity.this, BindPhoneActivity.class);
                        intent.putExtra("unionid",unionid);
                        startActivity(intent);
                    }else {
                        BaseApplication.getInstance().mobile = jo.getString("mobile");
                        BaseApplication.getInstance().token = jo.getString("token");
                        BaseApplication.getInstance().amount = jo.getString("amount");
                        BaseApplication.getInstance().userName = jo.getString("user_name");
                        BaseApplication.getInstance().integral = jo.getString("integral");
                        SPUtils.put(WXEntryActivity.this,Contacts.MOBILE,BaseApplication.getInstance().mobile);
                        SPUtils.put(WXEntryActivity.this,Contacts.TOKEN,BaseApplication.getInstance().token);
                        SPUtils.put(WXEntryActivity.this,Contacts.AMOUNT,BaseApplication.getInstance().amount);
                        SPUtils.put(WXEntryActivity.this,Contacts.NICK,BaseApplication.getInstance().userName);
                        SPUtils.put(WXEntryActivity.this,Contacts.INTEGRAL,BaseApplication.getInstance().integral);
                        // 发广播
                        Intent intent = new Intent();
                        intent.setAction(Contacts.INTENT_EXTRA_LOGIN_SUCESS);
                        sendBroadcast(intent);
                    }

                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hud.dismiss();
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                hud.dismiss();
                finish();
            }
        });
    }

    private void goToGetMsg() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();
    }

	/*
	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
		WXMediaMessage wxMsg = showReq.message;		
		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
		
		StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
		msg.append("description: ");
		msg.append(wxMsg.description);
		msg.append("\n");
		msg.append("extInfo: ");
		msg.append(obj.extInfo);
		msg.append("\n");
		msg.append("filePath: ");
		msg.append(obj.filePath);
		
		Intent intent = new Intent(this, ShowFromWXActivity.class);
		intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
		intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
		intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
		startActivity(intent);
		finish();
	}*/
}