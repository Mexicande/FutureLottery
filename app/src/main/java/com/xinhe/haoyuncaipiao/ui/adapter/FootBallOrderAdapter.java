package com.xinhe.haoyuncaipiao.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.model.FootBallOrder;

import java.util.List;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.ui.adapter.chipped.ChippInfoAdapte;

/**
 * Created by tantan on 2018/5/2.
 * 混合
 */

public class FootBallOrderAdapter extends BaseQuickAdapter<FootBallOrder.DataProduct.ArrProduct,BaseViewHolder>{
    private String mLotid;
    private String name;
    private ChippInfoAdapte mChippInfoAdapte;

    public FootBallOrderAdapter(@Nullable List<FootBallOrder.DataProduct.ArrProduct> data,String Lotid) {
        super(R.layout.football_order_detail_item,data);
        this.mLotid=Lotid;
    }

    @Override
    protected void convert(BaseViewHolder helper, FootBallOrder.DataProduct.ArrProduct item) {
            String week = "";
            switch (item.getWeek()){
                case "1":
                    week = "星期一";
                    break;
                case "2":
                    week = "星期二";
                    break;
                case "3":
                    week = "星期三";
                    break;
                case "4":
                    week = "星期四";
                    break;
                case "5":
                    week = "星期五";
                    break;
                case "6":
                    week = "星期六";
                    break;
                case "7":
                    week = "星期日";
                    break;
            }

            switch (mLotid){
                case "FT001":
                    name="胜平负";
                    break;
                case "FT006":
                    name="让球胜平负";
                    break;
                case "FT002":
                    name="比分";
                    break;
                case "FT003":
                    name="总进球";
                    break;
                case "FT004":
                    name="半全场";
                    break;
            }


            helper.setText(R.id.session_tv,week+"\n"+item.getTeam_id());
            String[] arr1 = item.getTeam().split("[*]");
            String[] arr2 = arr1[0].split(":");

            helper.setText(R.id.vs_tv,"("+item.getLetpoint()+")"+arr2[0]+"\n"+arr1[1]+"\n"+arr2[1]);


        RecyclerView recyclerView = helper.getView(R.id.id_info_recyler);
        mChippInfoAdapte=new ChippInfoAdapte(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setAdapter(mChippInfoAdapte);
        mChippInfoAdapte.addData(item.getInfo());


    }
}
