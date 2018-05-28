package com.xinhe.haoyuncaipiao.base;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.xinhe.haoyuncaipiao.R;

import com.xinhe.haoyuncaipiao.ui.activity.LoginActivity;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.SPUtils;

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
    public static void GET_SERVICE(String url,final Context context, JSONObject params, final OnRequestDataListener listener) {
        newExcuteJsonPost(url, context, params,listener);
    }

    private static void newExcuteJsonPost(String url, final Context context, JSONObject params, final OnRequestDataListener listener){
        final String netError = context.getString(R.string.net_error);
        String token= (String) SPUtils.get(context,Contacts.TOKEN,"");

        OkGo.<String>post(url)
                .tag(context)
                .headers(Contacts.TOKEN,token)
                .upJson(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(response.body()!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(response.body());
                                int code = jsonObject.getInt("error_code");
                                if(code==0||code==Api.Special_Code.notEnoughMoney){
                                    listener.requestSuccess(code, jsonObject);
                                }else if (code==600||code==1003){
                                    ActivityUtils.startActivity(LoginActivity.class);
                                    listener.requestFailure(code, jsonObject.getString("error_message"));
                                }else {
                                    listener.requestFailure(code, jsonObject.getString("error_message"));
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
