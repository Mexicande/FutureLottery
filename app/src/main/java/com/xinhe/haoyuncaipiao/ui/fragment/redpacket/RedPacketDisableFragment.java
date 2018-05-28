package com.xinhe.haoyuncaipiao.ui.fragment.redpacket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseFragment;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.RedPacket;
import com.xinhe.haoyuncaipiao.ui.adapter.RedPacketAdapter;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import com.xinhe.haoyuncaipiao.R;

/**
 * 红包不可用
 */
public class RedPacketDisableFragment extends BaseFragment {

    @BindView(R.id.red_packet_rv)
    RecyclerView redPacketRv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ArrayList<RedPacket.DataProduct.InfoProduct> redPackets = new ArrayList<>();
    private RedPacketAdapter adapter;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_red_packet_disable;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData(0);
        initView();
        setListener();
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (!DeviceUtil.IsNetWork(getContext())) {
                    ToastUtils.showToast("网络异常");
                    refreshLayout.finishRefresh();
                    return;
                }
                getData(0);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (!DeviceUtil.IsNetWork(getContext())) {
                    ToastUtils.showToast("网络异常");
                    refreshLayout.finishLoadMore();
                    return;
                }
                getData(redPackets.size());
            }
        });
    }

    private void initView() {
        adapter = new RedPacketAdapter(redPackets, 2);
        redPacketRv.setLayoutManager(new LinearLayoutManager(getContext()));
        redPacketRv.setAdapter(adapter);
    }

    private void getData(final int begin) {
        if (!DeviceUtil.IsNetWork(getContext())) {
            ToastUtils.showToast("网络异常，请检查网络连接");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("limit_begin", begin);
            jsonObject.put("limit_num", 20);
            jsonObject.put("type", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.RedPacket.envelopes, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    RedPacket redRecket = gson.fromJson(data.toString(), RedPacket.class);

                    if (null == redRecket.getData().getInfo() || redRecket.getData().getInfo().size() == 0) {
                        if (begin == 0) {
                            redPackets.clear();
                            adapter.notifyDataSetChanged();
                            redPacketRv.setVisibility(View.GONE);
                        } else {
                            ToastUtils.showToast("已经到底了");
                        }
                    } else {
                        if (0 == begin) {
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
                if (refreshLayout.isLoading()) {
                    refreshLayout.finishLoadMore();
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.finishRefresh();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                //停止刷新、加载更多
                if (refreshLayout.isLoading()) {
                    refreshLayout.finishLoadMore();
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.finishRefresh();
                }
            }
        });
    }

}
