package cn.com.futurelottery.base;

import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.futurelottery.R;
import cn.com.futurelottery.inter.OnRequestDataListener;

/**
 * Created by apple on 2018/4/13.
 */

public class ApiService {
    /**
     * @param context
     * @param params
     * @param listener
     * banner
     */
    public static void GET_BANNER(final Context context, JSONObject params, final OnRequestDataListener listener) {
        newExcuteJsonPost(Api.GET_BANNER, context, params,listener);
    }

    /**
     * @param context
     * @param params
     * @param listener
     * 弹窗
     */
    public static void GET_POPUP(final Context context, JSONObject params, final OnRequestDataListener listener) {
        newExcuteJsonPost(Api.GET_POPUP, context, params,listener);
    }

    /**
     * @param context
     * @param params
     * @param listener
     * 往期中奖和截止时间
     */
    public static void GET_BYTIME(final Context context, JSONObject params, final OnRequestDataListener listener) {
        newExcuteJsonPost(Api.GET_BYTIME, context, params,listener);
    }
    /**
     * @param context
     * @param params
     * @param listener
     * 双色球下注
     */
    public static void POST_DOUBLE_BALL(final Context context, JSONObject params, final OnRequestDataListener listener) {
        newExcuteJsonPost(Api.POST_DOUBLE_BALL, context, params,listener);
    }

    private static void newExcuteJsonPost(String url, final Context context, JSONObject params, final OnRequestDataListener listener){
        final String netError = context.getString(R.string.net_error);
        OkGo.<String>post(url)
                .tag(context)
                .upJson(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(response.body()!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(response.body());
                                int code = jsonObject.getInt("code");
                                if(code==200){
                                    listener.requestSuccess(0, jsonObject);
                                }else {
                                    listener.requestFailure(-1, jsonObject.getString("descrp"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            listener.requestFailure(-1, netError);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        listener.requestFailure(-1, netError);
                    }
                });


    }

}
