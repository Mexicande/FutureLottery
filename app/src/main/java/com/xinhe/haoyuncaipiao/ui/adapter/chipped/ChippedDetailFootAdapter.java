package com.xinhe.haoyuncaipiao.ui.adapter.chipped;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.ChippedDetail;

/**
 * Created by tantan on 2018/5/19.
 */

public class ChippedDetailFootAdapter extends BaseQuickAdapter<ChippedDetail.DataFootball,BaseViewHolder>{
    public ChippedDetailFootAdapter(@Nullable List<ChippedDetail.DataFootball> data) {
        super(R.layout.football_order_detail_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChippedDetail.DataFootball item) {

        helper.setText(R.id.session_tv,item.getWeek()+"\n"+item.getTe());
        String[] arr1 = item.getNa().split("[*]");
        String[] arr2 = arr1[0].split(":");
        helper.setText(R.id.vs_tv,arr2[0]+"\n"+arr1[1]+"\n"+arr2[1]);
        //赔率
        String select = item.getSelected();
        String rate = "";
        if (select.contains(",")){
            rate=select.replace(",","\n");
        }else {
            rate=select;
        }
        helper.setText(R.id.bet_tv,rate);
        String result = item.getResult();
        helper.setText(R.id.caiguo_tv,result);

    }
}
