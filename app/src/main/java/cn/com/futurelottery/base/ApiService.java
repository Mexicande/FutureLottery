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
    public static void GET_SERVICE(String url,final Context context, JSONObject params, final OnRequestDataListener listener) {
        newExcuteJsonPost(url, context, params,listener);
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
                                int code = jsonObject.getInt("error_code");
                                if(code==0||code==Api.Special_Code.notEnoughMoney){
                                    listener.requestSuccess(code, jsonObject);
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
