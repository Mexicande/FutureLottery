package com.xinhe.haoyuncaipiao.ui.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xinhe.haoyuncaipiao.ui.service.update.UpdateService;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.BaseActivity;


public class WebViewActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.common_web_bar)
    ProgressBar bar;
    @BindView(R.id.my_web)
    WebView myWebView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String myUrl;

    private String title;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化控件
        initViews();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_web_view;
    }


    private void initViews() {
        Intent intent = getIntent();
        myUrl = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        if (TextUtils.isEmpty(title)) {
            toolbar.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }


        // 设置WebView属性，能够执行Javascript脚本
        myWebView.getSettings().setJavaScriptEnabled(true);// 是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞

        // 有些网页webview不能加载
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);// 设置js可以直接打开窗口，如window.open()，默认为false
        myWebView.getSettings().setSupportZoom(true);// 是否可以缩放，默认true
        myWebView.getSettings().setBuiltInZoomControls(false);// 是否显示缩放按钮，默认false
        myWebView.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放。大视图模式
        myWebView.getSettings().setLoadWithOverviewMode(true);// 和setUseWideViewPort(true)一起解决网页自适应问题
        myWebView.getSettings().setAppCacheEnabled(true);// 是否使用缓存
        myWebView.getSettings().setDomStorageEnabled(true);// DOM Storage
//		myWebView.getSettings().setUseWideViewPort(true);
//		myWebView.getSettings().setDatabaseEnabled(true);
//		myWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//		myWebView.getSettings().setBlockNetworkImage(true);
//		myWebView.getSettings().setAllowFileAccess(true);
//		myWebView.getSettings().setSaveFormData(false);
//		myWebView.getSettings().setLoadsImagesAutomatically(true);
//        myWebView.getSettings().setSupportMultipleWindows(true);
//        myWebView.getSettings().setLoadWithOverviewMode(true);
//        myWebView.getSettings().setUseWideViewPort(true);

        if (Build.VERSION.SDK_INT >= 21) {
            myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        /**
         * 设置获取位置
         */
        //启用数据库
        myWebView.getSettings().setDatabaseEnabled(true);
        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        myWebView.getSettings().setGeolocationDatabasePath(dir);
        //启用地理定位
        myWebView.getSettings().setGeolocationEnabled(true);
        //开启DomStorage缓存
        myWebView.getSettings().setDomStorageEnabled(true);


        myWebView.setWebChromeClient(new WebChromeClient() {
            //加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if (newProgress == 100) {
                    bar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    bar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    bar.setProgress(newProgress);//设置进度值
                }
            }

            //获取位置
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

        });

        myWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                if (url.endsWith(".apk")) {//判断是否是.apk结尾的文件路径
                    if (DeviceUtil.isWifiAvailable(WebViewActivity.this)) {
                        UpdateService.Builder.create(url).build(WebViewActivity.this);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("亲，您现在是非wifi状态下，确定要下载吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UpdateService.Builder.create(url).build(WebViewActivity.this);
                                alertDialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        builder.setCancelable(true);
                        alertDialog = builder.create();
                        alertDialog.show();
                    }

                }
            }
        });

        myWebView.setWebViewClient(new MyWebViewClient());

        myWebView.loadUrl(myUrl);
    }

    @OnClick({R.id.layout_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            WebView.HitTestResult hitTestResult = view.getHitTestResult();
            if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                view.loadUrl(url);
                return true;
            } else if (url.contains("platformapi/startapp")) {
                try {
                    Intent intent;
                    intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    // intent.setSelector(null);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(WebViewActivity.this, "请安装最新版支付宝", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else if (url.contains("weixin://wap/pay?")) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(WebViewActivity.this, "请安装最新版微信", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else if (url.contains("mqqapi://forward/url?")) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(WebViewActivity.this, "请安装最新版QQ", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else if (url.contains("tmast://appdetails?")) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(WebViewActivity.this, "请安装最新版应用宝", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else if (!(url.contains("http://") || url.contains("https://"))) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
//            else {
//                view.loadUrl(url);
//            }
            return false;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myWebView.destroy();
    }
}
