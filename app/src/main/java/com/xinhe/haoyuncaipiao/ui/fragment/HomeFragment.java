package com.xinhe.haoyuncaipiao.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinhe.haoyuncaipiao.ui.activity.SuperLottoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseFragment;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.Banner;
import com.xinhe.haoyuncaipiao.model.Notification;
import com.xinhe.haoyuncaipiao.model.Lottery;
import com.xinhe.haoyuncaipiao.ui.activity.DoubleBallActivity;
import com.xinhe.haoyuncaipiao.ui.activity.Football.FootBallActivity;
import com.xinhe.haoyuncaipiao.ui.activity.arrange.Line3Activity;
import com.xinhe.haoyuncaipiao.ui.activity.arrange.Line5Activity;
import com.xinhe.haoyuncaipiao.ui.activity.WebViewActivity;
import com.xinhe.haoyuncaipiao.ui.activity.arrange.Lottery3DActivity;
import com.xinhe.haoyuncaipiao.ui.adapter.LotteryAdapter;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.CommonUtil;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.ProductItemDecoration;
import com.xinhe.haoyuncaipiao.utils.SPUtil;
import com.xinhe.haoyuncaipiao.utils.SPUtils;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.marqueeview.MarqueeView;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

/**
 * A simple {@link Fragment} subclass.
 * @author apple
 *
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.main_Recycler)
    RecyclerView mMainRecycler;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    BGABanner mConvenientBanner;
    MarqueeView marqueeView;
    List<String> info = new ArrayList<>();
    private LotteryAdapter mProductAdapter;
    private ArrayList<Lottery> mProductList = new ArrayList<>();
    private KProgressHUD hud;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initDate();
        setListener();
    }

    private void initView() {
        LinearLayout temp = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.head_layout, null);
        marqueeView = temp.findViewById(R.id.marqueeView);
        mConvenientBanner = temp.findViewById(R.id.banner_fresco_demo_content);
        mConvenientBanner.setAdapter(new BGABanner.Adapter<ImageView, Banner>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, Banner string, int position) {
                Glide.with(getActivity())
                        .load(string.getImg())
                        .into(itemView);
            }
        });
        mConvenientBanner.setDelegate(new BGABanner.Delegate<ImageView, Banner>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, Banner model, int position) {
                if(!TextUtils.isEmpty(model.getLink_url())){
                    Intent intent=new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("url",model.getLink_url());
                    intent.putExtra("title",model.getTitle());
                    startActivity(intent);
                }

            }
        });
        mProductAdapter = new LotteryAdapter(null);
        mMainRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mMainRecycler.addItemDecoration(new ProductItemDecoration(CommonUtil.dip2px(10)));
        mMainRecycler.setAdapter(mProductAdapter);
        mProductAdapter.addHeaderView(temp);
    }

    private void initDate() {
        if (!DeviceUtil.IsNetWork(getContext())){
            ToastUtils.showToast("网络异常，请检查网络");
            return;
        }
        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str_jiazai))
                .setDimAmount(0.5f)
                .show();
        //Banner
        ApiService.GET_SERVICE(Api.GET_BANNER, getActivity(), new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray bannerArray = data.getJSONArray("data");
                    Gson gson=new Gson();
                    Type bannerType=new TypeToken<ArrayList<Banner>>(){}.getType();
                    ArrayList<Banner> jsonArray = gson.fromJson(bannerArray.toString(), bannerType);
                    mConvenientBanner.setData(jsonArray,null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hud.dismiss();
            }
            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                hud.dismiss();
            }
        });
        //Notice
        ApiService.GET_SERVICE(Api.GET_NOTIFITION, getActivity(), new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray bannerArray = data.getJSONArray("data");
                    Gson gson=new Gson();
                    Type bannerType=new TypeToken<ArrayList<Notification>>(){}.getType();
                    ArrayList<Notification> jsonArray = gson.fromJson(bannerArray.toString(), bannerType);
                    marqueeView.startWithList(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void requestFailure(int code, String msg) {

            }
        });
        ApiService.GET_SERVICE(Api.GET_LOTTERY, getActivity(), new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray bannerArray = data.getJSONArray("data");
                    Gson gson=new Gson();
                    Type bannerType=new TypeToken<ArrayList<Lottery>>(){}.getType();
                    ArrayList<Lottery> jsonArray = gson.fromJson(bannerArray.toString(), bannerType);
                    mProductAdapter.setNewData(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void requestFailure(int code, String msg) {

            }
        });


    }

    private void setListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initDate();
                        mRefreshLayout.finishRefresh();
                    }
                }, 500);

            }
        });

        mProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Lottery item = mProductAdapter.getItem(position);
                if (item != null&&"1".equals(item.getStatus())) {
                    String  lotid = item.getLotid();
                    //SPUtil.putString(getActivity(),"chip","3d");
                    SPUtil.remove(getActivity(),"chip");
                    if(Contacts.Lottery.SSQ.equals(lotid)){
                        //双色球
                        ActivityUtils.startActivity(DoubleBallActivity.class);
                    }else if(Contacts.Lottery.DIL.equals(lotid)){
                        //大乐透
                        ActivityUtils.startActivity(SuperLottoActivity.class);
                    }else if(Contacts.Lottery.FTB.equals(lotid)){
                        //足彩
                        ActivityUtils.startActivity(FootBallActivity.class);
                    }else if(Contacts.Lottery.P5.equals(lotid)){
                        //排5
                        ActivityUtils.startActivity(Line5Activity.class);
                    }else if(Contacts.Lottery.P3.equals(lotid)){
                        //排3
                        ActivityUtils.startActivity(Line3Activity.class);
                    }else if(Contacts.Lottery.D3.equals(lotid)){
                        //排3
                        ActivityUtils.startActivity(Lottery3DActivity.class);
                    }
                    //浏览记录
                    record(lotid);
                }else {
                    ToastUtils.showToast("敬请期待");
                }
            }
        });
    }

    //浏览统计
    private void record(String lotid) {
        if (!DeviceUtil.IsNetWork(getContext())){
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("lotid",lotid);
            json.put("token",(String) SPUtils.get(getContext(),Contacts.TOKEN,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.record.envelopes, getActivity(),json , new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

}
