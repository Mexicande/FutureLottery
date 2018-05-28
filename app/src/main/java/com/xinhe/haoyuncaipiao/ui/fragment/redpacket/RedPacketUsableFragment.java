package com.xinhe.haoyuncaipiao.ui.fragment.redpacket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinhe.haoyuncaipiao.ui.activity.SuperLottoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseFragment;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.RedPacket;
import com.xinhe.haoyuncaipiao.model.RedPacketUse;
import com.xinhe.haoyuncaipiao.ui.activity.DoubleBallActivity;
import com.xinhe.haoyuncaipiao.ui.activity.Football.FootBallActivity;
import com.xinhe.haoyuncaipiao.ui.activity.RedPacketActivity;
import com.xinhe.haoyuncaipiao.ui.activity.arrange.Line3Activity;
import com.xinhe.haoyuncaipiao.ui.activity.arrange.Line5Activity;
import com.xinhe.haoyuncaipiao.ui.activity.arrange.Lottery3DActivity;
import com.xinhe.haoyuncaipiao.ui.adapter.RedPacketAdapter;
import com.xinhe.haoyuncaipiao.ui.adapter.RedPacketUseAdapter;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

/**
 * 红包可用
 */
public class RedPacketUsableFragment extends BaseFragment {

    @BindView(R.id.exchange_et)
    EditText exchangeEt;
    @BindView(R.id.exchange_btn)
    Button exchangeBtn;
    @BindView(R.id.red_packet_rv)
    RecyclerView redPacketRv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ArrayList<RedPacket.DataProduct.InfoProduct> redPackets = new ArrayList<>();
    private RedPacketAdapter adapter;
    private KProgressHUD hud;
    private ArrayList<RedPacketUse> redPacketUses;
    private String money="";
    private String lotid="";
    private String exchange;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_red_packet_usable;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (!DeviceUtil.IsNetWork(getContext())){
                    ToastUtils.showToast("网络异常");
                    refreshLayout.finishRefresh();
                    return;
                }
                getData(0,money,lotid);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (!DeviceUtil.IsNetWork(getContext())){
                    ToastUtils.showToast("网络异常");
                    refreshLayout.finishLoadMore();
                    return;
                }
                getData(redPackets.size(),money,lotid);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                //下单时选择红包，这里直接销毁
                if (!TextUtils.isEmpty(money)) {
                    Intent intent=new Intent();
                    intent.putExtra("id",redPackets.get(position).getId());
                    intent.putExtra("redMoney",redPackets.get(position).getAmount());
                    getActivity().setResult(-1,intent);
                    getActivity().finish();
                    return;
                }

                if (!DeviceUtil.IsNetWork(getContext())) {
                    ToastUtils.showToast("网络异常，请检查网络连接");
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", redPackets.get(position).getRed_id());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hud = KProgressHUD.create(getContext())
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("加载中...")
                        .setDimAmount(0.5f)
                        .show();

                ApiService.GET_SERVICE(Api.RedPacket.rightoff, getContext(), jsonObject, new OnRequestDataListener() {
                    @Override
                    public void requestSuccess(int code, JSONObject data) {
                        try {
                            Gson gson = new Gson();
                            Type type=new TypeToken<ArrayList<RedPacketUse>>(){}.getType();
                            redPacketUses=gson.fromJson(data.getJSONArray("data").toString(),type);
                            showChooseDialog(redPackets.get(position).getAmount(),position);
                        } catch (Exception e) {
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
            }
        });
    }

    private void initView() {
        Intent intent=getActivity().getIntent();
        try {
            money=intent.getStringExtra("money");
            lotid=intent.getStringExtra("lotid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getData(0,money,lotid);

        adapter = new RedPacketAdapter(redPackets,1);
        redPacketRv.setLayoutManager(new LinearLayoutManager(getContext()));
        redPacketRv.setAdapter(adapter);
    }

    private void getData(final int begin,String money,String lotid) {
        if (!DeviceUtil.IsNetWork(getContext())) {
            ToastUtils.showToast("网络异常，请检查网络连接");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("limit_begin", begin);
            jsonObject.put("limit_num", 20);
            jsonObject.put("type", 1);
            jsonObject.put("money", money);
            jsonObject.put("lotid", lotid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.RedPacket.envelopes, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    RedPacket redRecket = gson.fromJson(data.toString(),RedPacket.class);
                    //设置红包个数
                    RedPacketActivity redActivity = (RedPacketActivity) getActivity();
                    redActivity.resetRedpacketCount("可用("+redRecket.getData().getCount()+")");

                    if (null == redRecket.getData().getInfo() || redRecket.getData().getInfo().size() == 0) {
                        if (begin==0){
                            redPackets.clear();
                            adapter.notifyDataSetChanged();
                            redPacketRv.setVisibility(View.GONE);
                        }else {
                            ToastUtils.showToast("已经到底了");
                        }
                    } else {
                        if (0==begin){
                            redPackets.clear();
                        }
                        redPacketRv.setVisibility(View.VISIBLE);
                        redPackets.addAll(redRecket.getData().getInfo());
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //停止刷新、加载更多
                if (refreshLayout.isLoading()){
                    refreshLayout.finishLoadMore();
                }
                if (refreshLayout.isRefreshing()){
                    refreshLayout.finishRefresh();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                //停止刷新、加载更多
                if (refreshLayout.isLoading()){
                    refreshLayout.finishLoadMore();
                }
                if (refreshLayout.isRefreshing()){
                    refreshLayout.finishRefresh();
                }
            }
        });
    }

    /**
     * 合买选择
     */
    private void showChooseDialog(String count,int position) {
        final AlertDialog alertDialog1 = new AlertDialog.Builder(getContext(), R.style.CustomDialog).create();
        alertDialog1.setCancelable(false);
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.show();
        Window window1 = alertDialog1.getWindow();
        window1.setContentView(R.layout.red_packet_use_dialog);
        TextView moneyTv = window1.findViewById(R.id.money_tv);
        moneyTv.setText(count+"元红包");

        TextView cancleTv = window1.findViewById(R.id.cancle_tv);
        cancleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });

        RecyclerView recyclerView = window1.findViewById(R.id.choose_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RedPacketUseAdapter mAdapter = new RedPacketUseAdapter(redPacketUses);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (redPacketUses.get(position).getLotid()){
                    case "ssq":
                        ActivityUtils.startActivity(DoubleBallActivity.class);
                        break;
                    case "dlt":
                        ActivityUtils.startActivity(SuperLottoActivity.class);
                        break;
                    case "ftb":
                    case "FT001":
                    case "FT002":
                    case "FT003":
                    case "FT004":
                    case "FT005":
                    case "FT006":
                        ActivityUtils.startActivity(FootBallActivity.class);
                        break;
                    case "p3":
                        ActivityUtils.startActivity(Line3Activity.class);
                        break;
                    case "p5":
                        ActivityUtils.startActivity(Line5Activity.class);
                        break;
                    case "3d":
                        ActivityUtils.startActivity(Lottery3DActivity.class);
                        break;
                }
                alertDialog1.dismiss();
            }
        });
    }

    @OnClick({R.id.exchange_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exchange_btn:
                exchange();
                break;
        }
    }

    private void exchange() {
        if (!DeviceUtil.IsNetWork(getContext())) {
            ToastUtils.showToast("网络异常，请检查网络连接");
            return;
        }

        exchange=exchangeEt.getText().toString().trim();
        if (TextUtils.isEmpty(exchange)){
            ToastUtils.showToast("请输入邀请码");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", exchange);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中...")
                .setDimAmount(0.5f)
                .show();

        ApiService.GET_SERVICE(Api.RedPacket.code, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if (0==code){
                    ToastUtils.showToast("兑换成功");
                    getData(0,money,lotid);
                }
                hud.dismiss();
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                hud.dismiss();
            }
        });
    }

}
