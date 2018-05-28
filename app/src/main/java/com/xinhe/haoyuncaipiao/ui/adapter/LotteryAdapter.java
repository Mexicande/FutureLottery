package com.xinhe.haoyuncaipiao.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinhe.haoyuncaipiao.model.Lottery;

import java.util.List;

import com.xinhe.haoyuncaipiao.R;

/**
 *
 * @author apple
 * @date 2018/4/11
 */

public class LotteryAdapter extends BaseQuickAdapter<Lottery,BaseViewHolder> {

    public LotteryAdapter(@Nullable List<Lottery> data) {
        super(R.layout.product_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Lottery item) {
        helper.setText(R.id.tv_name,item.getTitle())
                .setText(R.id.tv_desc,"1".equals(item.getStatus())?item.getDesc():"暂停销售");

        Glide.with(mContext).load(item.getLogo())
                .apply(new RequestOptions())
                .into((ImageView) helper.getView(R.id.iv_product_logo));
    }
}
