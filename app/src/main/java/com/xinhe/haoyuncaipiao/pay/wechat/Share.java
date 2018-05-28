package com.xinhe.haoyuncaipiao.pay.wechat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.pay.wechat.Util;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;


/**
 *
 * @author tantan
 * @date 2018/4/25
 * 分享
 */

public class Share {
    private String url="http://a.app.qq.com/o/simple.jsp?pkgname=com.xinhe.haoyuncaipiao";
    private String title="幸运彩";
    private String content="带给你不一样的中奖体验";
    private Context mContext;
    private Activity activity;
    private IWXAPI api;
    private BottomSheetDialog dialog;
    private Bitmap mBitmap;
    private LinearLayout view;

    //网页分享
    public Share(Context mContext,String url,String title,String content) {
        this.mContext = mContext;
        if (!TextUtils.isEmpty(url)){
            this.url=url;
        }
        if (!TextUtils.isEmpty(title)){
            this.title=title;
        }
        if (!TextUtils.isEmpty(content)){
            this.content=content;
        }
        // 将该app注册到微信
        api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp(Contacts.WeChat.WX_APP_ID);
        initView();
    }
    //截图分享
    public Share(Context mContext, LinearLayout view) {
        this.mContext = mContext;
        this.view=view;
        // 将该app注册到微信
        api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp(Contacts.WeChat.WX_APP_ID);
        initView();
        //截图
        cropBitmip();
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
                ShareToFriend(false);
                dialog.dismiss();
            }
        });
        friendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareToFriend(true);
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

    public void ShareToFriend(boolean isShareFriend){
        if(mBitmap==null){
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);

            msg.title = title;
            msg.description = content;
//            Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//            msg.thumbData = Util.bmpToByteArray(thumb, true);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = isShareFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            api.sendReq(req);
        }else {
            WXImageObject imgObj = new WXImageObject(mBitmap);
            WXMediaMessage msg = new WXMediaMessage(imgObj);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(mBitmap, 131, 180, true);
            //缩略图大小
            mBitmap.recycle();
            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
            // 设置缩略图
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            req.scene = isShareFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            api.sendReq(req);
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    //截图与分享
    private void cropBitmip() {
        try {
            //开启缓存功能
            view.setDrawingCacheEnabled(true);
            //创建缓存
            view.buildDrawingCache();
            //获取缓存Bitmap
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.logo);
            int width = bmp.getWidth();
            int secreenWidth = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
            float scaleWidth = ((float) secreenWidth) / width;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleWidth);
            Bitmap visiableBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                    bmp.getHeight(), matrix, true);
            Bitmap bitmap1 = splitVertical(visiableBitmap, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //拼图
    public static Bitmap splitVertical(Bitmap first, Bitmap second) {
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }


    public void show(){
        dialog.show();
    }
}
