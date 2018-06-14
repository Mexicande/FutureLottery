package com.xinhe.haoyuncaipiao.ui.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.itheima.roundedimageview.RoundedImageView;
import com.xinhe.haoyuncaipiao.pay.wechat.Share;
import com.xinhe.haoyuncaipiao.ui.activity.WithdrawActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseApplication;
import com.xinhe.haoyuncaipiao.base.BaseFragment;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.ui.activity.LoginActivity;
import com.xinhe.haoyuncaipiao.ui.activity.RedPacketActivity;
import com.xinhe.haoyuncaipiao.ui.activity.order.OrderActivity;
import com.xinhe.haoyuncaipiao.ui.activity.PersonalInformationActivity;
import com.xinhe.haoyuncaipiao.ui.activity.RechargeActivity;
import com.xinhe.haoyuncaipiao.ui.activity.SetActivity;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.Base64Utils;
import com.xinhe.haoyuncaipiao.utils.BitmapUtils;
import com.xinhe.haoyuncaipiao.utils.CropUtils;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.SPUtils;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.SettingService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tantan on 2018/4/9.
 * 我的
 */

public class CenterFragment extends BaseFragment {
    @BindView(R.id.center_fragment_iv)
    RoundedImageView centerFragmentIv;
    //刷新余额
    private final int WITHDRAW_REQUEST_CODE = 1001;
    private final int SET_REQUEST_CODE = 1002;
    private final int RESULT_CODE = -1;
    @BindView(R.id.center_fragment_number_tv)
    TextView centerFragmentNumberTv;
    @BindView(R.id.center_fragment_money_tv1)
    TextView centerFragmentMoneyTv1;
    @BindView(R.id.center_fragment_money_ll1)
    LinearLayout centerFragmentMoneyLl1;
    @BindView(R.id.center_fragment_money_ll2)
    LinearLayout centerFragmentMoneyLl2;
    @BindView(R.id.center_fragment_money_ll3)
    LinearLayout centerFragmentMoneyLl3;
    @BindView(R.id.center_fragment_order_ll1)
    LinearLayout centerFragmentOrderLl1;
    @BindView(R.id.center_fragment_order_ll2)
    LinearLayout centerFragmentOrderLl2;
    @BindView(R.id.center_fragment_order_ll3)
    LinearLayout centerFragmentOrderLl3;
    @BindView(R.id.center_fragment_order_ll4)
    LinearLayout centerFragmentOrderLl4;
    @BindView(R.id.center_fragment_rl1)
    RelativeLayout centerFragmentRl1;
    @BindView(R.id.center_fragment_rl2)
    RelativeLayout centerFragmentRl2;
    private InnerReceiver receiver;
    private AlertDialog alertDialog;
    private KProgressHUD hud;
    private BottomSheetDialog dialogChoose;
    private File file;
    private Uri origUri;
    private final int REQUEST_CODE_TAKE_PICETURE=1003;
    private String fileName;
    private File protraitFile;
    private Uri cropUri;
    private Bitmap bitmap;
    private final int REQUEST_CODE_GETIMAGE_BYCROP=1004;
    private final int REQUEST_CODE_GETIMAGE_BYSDCARD=1005;

    @Override
    public int getLayoutResource() {
        return R.layout.center_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        // 注册广播接收者
        receiver = new InnerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contacts.INTENT_EXTRA_LOGIN_SUCESS);
        getContext().registerReceiver(receiver, filter);

        if (!TextUtils.isEmpty(BaseApplication.getInstance().token)){
            getBalance();
            setLoginView();
        }
    }

    private void setLoginView() {
        if (centerFragmentIv==null){
            return;
        }
        centerFragmentNumberTv.setText(BaseApplication.getInstance().userName);
        centerFragmentMoneyTv1.setText(BaseApplication.getInstance().amount);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE) {
            switch (requestCode) {
                case WITHDRAW_REQUEST_CODE:
                    getBalance();
                    break;
                case SET_REQUEST_CODE:
                    exit();
                    break;
                case 400:
                    // 这个400就是你上面传入的数字。
                    // 你可以在这里检查你需要的权限是否被允许，并做相应的操作。
                    checkMyPermission();
                    break;
                case REQUEST_CODE_TAKE_PICETURE:
                    // 异常处理
                    try {
                        startActionCrop(origUri);// 拍照后裁剪
                    } catch (Exception e) {
                        ToastUtils.showToast("获取剪裁失败，请尝试其它方式");
                    }
                    break;
                case REQUEST_CODE_GETIMAGE_BYCROP:
                    // 异常处理
                    try {
//                        bitmap = BitmapUtils.getBitmapFormUri(getActivity(), data.getData());
//                        getModify(bitmap);
                        startActionCrop(data.getData());// 选图后裁剪
                    } catch (Exception e) {
                        ToastUtils.showToast("获取剪裁失败，请尝试其它方式");
                    }
                    break;
                case REQUEST_CODE_GETIMAGE_BYSDCARD:
                    // 获取头像缩略图
                    if (protraitFile != null && protraitFile.exists()) {
                        try {
                            Uri newUri;
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                newUri = Uri.parse("file:///" + CropUtils.getPath(getContext(), cropUri));
                            } else {
                                newUri = cropUri;
                            }
                            bitmap = BitmapUtils.getBitmapFormUri(getActivity(),newUri);
                            getModify(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    private void getModify(Bitmap bitmap) {
        if (!DeviceUtil.IsNetWork(getContext())) {
            Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
            return;
        }

        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("上传中...")
                .setDimAmount(0.5f)
                .show();

        String base64 = Base64Utils.bitmapToBase64(bitmap);
        Map<String, String> map = new HashMap<>();
        map.put("avatar", base64);
        JSONObject jsonObject = new JSONObject(map);
        ApiService.GET_SERVICE(Api.user.avatar, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (0==code){
                        Glide.with(getContext()).load(bitmap)
                                .apply(new RequestOptions())
                                .into(centerFragmentIv);
                    }else {
                        ToastUtils.showToast("修改失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hud.dismiss();
            }

            @Override
            public void requestFailure(int code, String msg) {
                hud.dismiss();
                ToastUtils.showToast(msg);
            }
        });
    }

    //用户退出时处理
    private void exit() {
        centerFragmentIv.setImageResource(R.mipmap.me_fragment_head1);
        centerFragmentNumberTv.setText("登录/注册");
        centerFragmentMoneyTv1.setText("0.00");
        BaseApplication.getInstance().token="";
        BaseApplication.getInstance().mobile="";
        BaseApplication.getInstance().amount="";
        BaseApplication.getInstance().userName="";
        BaseApplication.getInstance().integral="";
        SPUtils.remove(getContext(),Contacts.MOBILE);
        SPUtils.remove(getContext(),Contacts.TOKEN);
        SPUtils.remove(getContext(),Contacts.AMOUNT);
        SPUtils.remove(getContext(),Contacts.NICK);
        SPUtils.remove(getContext(),Contacts.INTEGRAL);
    }


    @OnClick({R.id.center_fragment_money_ll1, R.id.center_fragment_money_ll2, R.id.center_fragment_money_ll3, R.id.center_fragment_order_ll1, R.id.center_fragment_order_ll2, R.id.center_fragment_order_ll3, R.id.center_fragment_order_ll4, R.id.center_fragment_rl1, R.id.center_fragment_rl2,
            R.id.center_fragment_iv, R.id.center_fragment_number_tv, R.id.me_fragment_rl3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.center_fragment_iv:
                if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
                    ActivityUtils.startActivity(LoginActivity.class);
                }else {
                    //修改头像
                    checkMyPermission();
                }
                break;
            case R.id.center_fragment_number_tv:
                if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
                    ActivityUtils.startActivity(LoginActivity.class);
                }else {
                    //修改昵称
                    modifyNickname();
                }
                break;
            case R.id.center_fragment_money_ll1:
                if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
                    ActivityUtils.startActivity(LoginActivity.class);
                }else {
                    Intent intent=new Intent(getContext(),RechargeActivity.class);
                    startActivityForResult(intent,WITHDRAW_REQUEST_CODE);
                }
                break;
            case R.id.center_fragment_money_ll2:
                break;
            case R.id.center_fragment_money_ll3:
                if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
                    ActivityUtils.startActivity(LoginActivity.class);
                }else {
                    getWithdrawStatus();
                }
                break;

            case R.id.center_fragment_order_ll1:
                //全部订单
                if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }
                Intent intent3 = new Intent(getActivity(), OrderActivity.class);
                intent3.putExtra("intentType","1");
                startActivity(intent3);
                break;
            case R.id.center_fragment_order_ll2:
                if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }
                Intent intent4 = new Intent(getActivity(), OrderActivity.class);
                intent4.putExtra("intentType","2");
                startActivity(intent4);
                break;
            case R.id.center_fragment_order_ll3:
                if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }
                Intent intent5 = new Intent(getActivity(), OrderActivity.class);
                intent5.putExtra("intentType","3");
                startActivity(intent5);
                break;
            case R.id.center_fragment_order_ll4:
                if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }
                Intent intent6 = new Intent(getActivity(), OrderActivity.class);
                intent6.putExtra("intentType","4");
                startActivity(intent6);
                break;
            case R.id.center_fragment_rl1:
                if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
                    ActivityUtils.startActivity(LoginActivity.class);
                }else {
                    ActivityUtils.startActivity(RedPacketActivity.class);
                }
                break;
            case R.id.center_fragment_rl2:
                //分享
                getShareContent();
                break;
            case R.id.me_fragment_rl3:
                Intent intent8 = new Intent(getActivity(), SetActivity.class);
                startActivityForResult(intent8,SET_REQUEST_CODE);
                break;
        }
    }

    //获取分享的内容
    private void getShareContent() {
        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中...")
                .setDimAmount(0.5f)
                .show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.user.share, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();

                try {
                    if (0==code){
                        JSONObject data1 = data.getJSONObject("data");
                        String url = data1.getString("url");
                        String title = data1.getString("title");
                        String desc = data1.getString("desc");
                        Share share=new Share(getContext(),url,title,desc);
                        share.show();
                    }else {
                        ToastUtils.showToast("分享失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast("分享失败");
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                hud.dismiss();
                ToastUtils.showToast(msg);
            }
        });
    }

    private void modifyHead() {
        dialogChoose=new BottomSheetDialog(getContext());
        View dialogView= LayoutInflater.from(getContext())
                .inflate(R.layout.picture_choose,null);
        TextView firstTv= (TextView) dialogView.findViewById(R.id.tv_first_button);
        firstTv.setText("从相册选取");
        TextView secondTv= (TextView) dialogView.findViewById(R.id.tv_second_button);
        secondTv.setText("拍照");
        TextView tvCancel= (TextView) dialogView.findViewById(R.id.tv_cancel);
        tvCancel.setText("取消");

        firstTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//相册
                try {
                    startImagePick();
                } catch (Exception e) {
                    ToastUtils.showToast("选图失败，请尝试其它方式");
                }
                dialogChoose.dismiss();
            }
        });
        secondTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//拍照
                // 异常处理
                try {
                    takeForPicture();
                } catch (Exception e) {
                    ToastUtils.showToast("拍照失败，请尝试其它方式");
                }
                dialogChoose.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChoose.dismiss();
            }
        });

        dialogChoose.setContentView(dialogView);
        dialogChoose.show();
    }

    /**
     * 选择图片裁剪
     */
    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    REQUEST_CODE_GETIMAGE_BYCROP);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    REQUEST_CODE_GETIMAGE_BYCROP);
        }
    }

    /**
     * 拍照后裁剪
     *
     * @param data 原始图片
     */
    private void startActionCrop(Uri data) {
        Uri newUri;
        //android7.0和7.0以下的不同的uri
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            newUri = Uri.parse("file:///" + CropUtils.getPath(getContext(), data));
        } else {
            newUri = data;
        }
        if (newUri != null) {
            startPhotoZoom(newUri);
        } else {
            ToastUtils.showToast("没有得到相册图片");
        }


    }

    /**
     * 裁剪方法
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX",400);//图片输出大小,可以不需要
        intent.putExtra("outputY", 400);
        //注意这里的输出的是上面的文件路径的Uri格式，这样在才能获取图片
        intent.putExtra("output", getUploadTempFile());
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    // 裁剪头像的绝对路径
    private Uri getUploadTempFile() {
        String storageState = Environment.getExternalStorageState();
        File savedir ;
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {

            savedir = new File(Environment.getExternalStorageDirectory().getPath()
                    + "/xingyuncai/");//图片位置
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            ToastUtils.showToast("无法保存照片，请检查SD卡是否挂载");
            return null;
        }

        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new
                Date()) + ".jpg";

        // 裁剪头像的绝对路径
        protraitFile = new File(savedir, fileName);

//        if (Build.VERSION.SDK_INT > 23) {//7.0及以上
//            cropUri = FileProvider.getUriForFile(getContext(), Contacts.fileprovider, protraitFile);
//            getContext().grantUriPermission(getContext().getPackageName(), cropUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }else {
            cropUri = Uri.fromFile(protraitFile);
//        }
        return this.cropUri;
    }


    /**
     * 调用相机
     */
    private void takeForPicture() {

        try {
            String storageState = Environment.getExternalStorageState();
            File vFile;
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                vFile = new File(Environment.getExternalStorageDirectory().getPath()
                        + "/xingyuncai/");//图片位置
                if (!vFile.exists()) {
                    vFile.mkdirs();
                }
            } else {
                ToastUtils.showToast("未挂载sdcard");
                return;
            }

            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new
                    Date()) + ".jpg";

            file = new File(vFile, fileName);
            //拍照所存路径
            origUri = Uri.fromFile(file);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT > 23) {//7.0及以上
                origUri = FileProvider.getUriForFile(getContext(), Contacts.fileprovider, new File(vFile, fileName));
                getContext().grantUriPermission(getContext().getPackageName(), origUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, origUri);
            } else {//7.0以下
                intent.putExtra(MediaStore.EXTRA_OUTPUT, origUri);
            }
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICETURE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 权限管理
     */
    private void checkMyPermission() {
        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .rationale((requestCode, rationale) ->
                        // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(getContext(), rationale).show())
                .callback(listener)
                .start();
    }

    //权限监听
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if(requestCode == 200) {
                //修改头像
                modifyHead();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if(requestCode == 200) {
                // 是否有不再提示并拒绝的权限。
                if (AndPermission.hasAlwaysDeniedPermission(getActivity(), deniedPermissions)) {
                    // 第三种：自定义dialog样式。
                    SettingService settingService = AndPermission.defineSettingDialog(getActivity(), 400);
                    // 你的dialog点击了确定调用：
                    settingService.execute();
                    // 你的dialog点击了取消调用：
                    settingService.cancel();
                    ToastUtils.showToast("请允许调用权限");
                }else {
                    ToastUtils.showToast("权限被拒绝");
                }
            }
        }
    };


    /*
     *
     *
     * 弹出对话框的步骤 1.创建alertDialog的builder. 2.要给builder设置属行, 对话框的内容,样式,按钮
     * 3.通过builder 创建个对话框 4.对话框show()出来
     */
    protected void modifyNickname() {
        alertDialog = new AlertDialog.Builder(getContext(), R.style.CustomDialog).create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(false);
        /**
         * 下面三行可以让对话框里的输入框可以输入
         */
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.verify, null);
        alertDialog.setView(layout);

        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.modify_dialog);
        EditText etVerify = (EditText) window.findViewById(R.id.verify_et);
        Button btn = (Button) window.findViewById(R.id.verify_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DeviceUtil.IsNetWork(getContext())) {
                    ToastUtils.showToast("网络未连接");
                    return;
                }
                String nickname = etVerify.getText().toString().trim();

                if (TextUtils.isEmpty(nickname)) {
                    ToastUtils.showToast("昵称不能为空");
                    return;
                }

                //对话框隐藏
                alertDialog.dismiss();
                modify(nickname);
            }
        });
    }

    //修改昵称
    private void modify(String nickname) {
        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("修改中...")
                .setDimAmount(0.5f)
                .show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.user.name, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();
                try {
                    if (0==code){
                        BaseApplication.getInstance().userName = nickname;
                        SPUtils.put(getContext(),Contacts.NICK,BaseApplication.getInstance().userName);
                        centerFragmentNumberTv.setText(BaseApplication.getInstance().userName);
                    }else {
                        ToastUtils.showToast("修改失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                hud.dismiss();
                ToastUtils.showToast(msg);
            }
        });
    }

    /**
     * 获取用户绑定银行卡状态
     */
    private void getWithdrawStatus() {
        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str_jiazai))
                .setDimAmount(0.5f)
                .show();
        JSONObject jsonObject = new JSONObject();
        ApiService.GET_SERVICE(Api.Withdraw.binding, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();

                try {
                    if (0==code){
                        String status = data.getJSONObject("data").getString("status");
                        if ("0".equals(status)){
                            Intent intent = new Intent(getActivity(), WithdrawActivity.class);
                            startActivityForResult(intent, WITHDRAW_REQUEST_CODE);
                        }else {
                            Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                            startActivityForResult(intent, WITHDRAW_REQUEST_CODE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                hud.dismiss();
            }
        });
    }

    /**
     * 获取余额
     */
    private void getBalance() {
        JSONObject jsonObject = new JSONObject();
        ApiService.GET_SERVICE(Api.Login.balance, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (0==code){
                        BaseApplication.getInstance().amount=data.getJSONObject("data").getString("amount");
                        String avatar = data.getJSONObject("data").getString("avatar");
                        if (!TextUtils.isEmpty(avatar)){
                            Glide.with(getContext()).load(avatar)
                                    .apply(new RequestOptions())
                                    .into(centerFragmentIv);
                        }
                        centerFragmentMoneyTv1.setText(BaseApplication.getInstance().amount);
                        SPUtils.put(getContext(),Contacts.AMOUNT,BaseApplication.getInstance().amount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    // 广播接收者
    private class InnerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取Intent中的Action
            String action = intent.getAction();
            // 判断Action
            if (Contacts.INTENT_EXTRA_LOGIN_SUCESS.equals(action)) {
                getBalance();
                setLoginView();
            }
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (!TextUtils.isEmpty(BaseApplication.getInstance().token)){
                getBalance();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(BaseApplication.getInstance().token)){
            getBalance();
        }
    }
}
