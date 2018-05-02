package cn.com.futurelottery.pay.wechat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Contacts;

/**
 * Created by tantan on 2018/4/25.
 */

public class Share {
    private Context mContext;
    private IWXAPI api;
    private BottomSheetDialog dialog;


    public Share(Context mContext) {
        this.mContext = mContext;
        // 将该app注册到微信
        api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp(Contacts.WeChat.WX_APP_ID);

        initView();
    }

    private void initView() {
        dialog=new BottomSheetDialog(mContext);
        View dialogView= LayoutInflater.from(mContext)
                .inflate(R.layout.share,null);
        ImageView friendsIv= (ImageView) dialogView.findViewById(R.id.weixin_friends_iv);
        ImageView friendIv= (ImageView) dialogView.findViewById(R.id.weixin_friend_iv);
        TextView tvCancel= (TextView) dialogView.findViewById(R.id.cancel);

        friendsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareToFriends();
                dialog.dismiss();
            }
        });
        friendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareToFriend();
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogView);

    }

    public void ShareToFriend(){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.baidu.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "标题";
        msg.description = "描述信息";
        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage"+System.currentTimeMillis();
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline ;
        api.sendReq(req);
    }

    public void ShareToFriends(){
        WXWebpageObject webpage = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        webpage.webpageUrl = "http://www.baidu.com";
        msg.title = "标题";
        msg.description = "描述信息";
        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage"+System.currentTimeMillis();
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession ;
        api.sendReq(req);
    }

    public void show(){
        dialog.show();
    }
}
