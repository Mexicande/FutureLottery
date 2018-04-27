package cn.com.futurelottery.pay.wechat;

import android.app.Activity;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import cn.com.futurelottery.base.Contacts;


/**
 * Created by Administrator on 2016/9/18.
 * Author: XuDeLong
 */
public class Wechat {
    private Activity mContext;
    public static final String WX_APP_ID = Contacts.WeChat.WX_APP_ID;
    public static final String WX_PARTNER_ID = Contacts.WeChat.WX_PARTNER_ID;
    public final IWXAPI msgApi;

    public Wechat(Activity mContext) {
        this.mContext = mContext;
        msgApi = WXAPIFactory.createWXAPI(mContext, WX_APP_ID, true);
        msgApi.registerApp(WX_APP_ID);
    }


    public  void pay(String jsonStr){
        try{
            JSONObject json = new JSONObject(jsonStr);
                if(null !=  json && !json.has("retcode") ){
                    PayReq req = new PayReq();
                    req.appId			= json.getString("appid");
                    req.partnerId		= json.getString("partnerid");
                    req.prepayId		= json.getString("prepayid");
                    req.nonceStr		= json.getString("noncestr");
                    req.timeStamp		= json.getString("timestamp");
                    req.packageValue	= json.getString("package");
                    req.sign			= json.getString("sign");
                    req.extData			= "app data"; // optional
                   // Toast.makeText(this.mContext, "正常调起支付", Toast.LENGTH_SHORT).show();
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    Boolean aa = msgApi.sendReq(req);
                    //Toast.makeText(this.mContext, "正常调起支付"+ aa, Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
                    Toast.makeText(this.mContext, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                }

        }catch(Exception e){
            Log.e("PAY_GET", "异常："+e.getMessage());
            Toast.makeText(this.mContext, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
