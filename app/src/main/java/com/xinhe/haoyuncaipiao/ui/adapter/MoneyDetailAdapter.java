package com.xinhe.haoyuncaipiao.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.AwardPeriod;
import com.xinhe.haoyuncaipiao.model.MoneyDetail;

import java.util.List;

/**
 * Created by apple on 2018/6/15.
 */

public class MoneyDetailAdapter extends BaseQuickAdapter<MoneyDetail.InfoBean,BaseViewHolder> {

    public MoneyDetailAdapter( @Nullable List<MoneyDetail.InfoBean> data) {
        super(R.layout.money_detail_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MoneyDetail.InfoBean item) {

        helper.setText(R.id.desc,item.getDesc())
                .setText(R.id.created_at,item.getCreated_at())
                .setText(R.id.amount,item.getType()==1?"-"+item.getAmount()+"元":"+"+item.getAmount()+"元")
                .setTextColor(R.id.amount,item.getType()==1?
                        mContext.getResources().getColor(R.color.green_1A):mContext.getResources().getColor(R.color.red_ball));
    }
}
