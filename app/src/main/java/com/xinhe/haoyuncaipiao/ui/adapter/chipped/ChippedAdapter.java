package com.xinhe.haoyuncaipiao.ui.adapter.chipped;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import com.itheima.roundedimageview.RoundedImageView;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.Chipped;

/**
 * Created by tantan on 2018/5/16.
 */

public class ChippedAdapter extends BaseQuickAdapter<Chipped.DataProduct.InfoProduct,BaseViewHolder>{

    public ChippedAdapter(ArrayList<Chipped.DataProduct.InfoProduct> data){
        super(R.layout.chipped_item,data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Chipped.DataProduct.InfoProduct item) {
        helper.setText(R.id.nickname_tv,item.getUser_name());
        helper.setText(R.id.name_tv,item.getName());
        helper.setText(R.id.progress_tv,item.getProgress()+"%");
        helper.setText(R.id.total_mongey_tv,item.getPay_money_total());
        helper.setText(R.id.balance_tv,item.getRemaining_money()+"");
        helper.setText(R.id.count_tv,item.getNumber()+"");
        if (!TextUtils.isEmpty(item.getAvatar())){
            Glide.with(mContext).load(item.getAvatar())
                    .apply(new RequestOptions())
                    .into((RoundedImageView) helper.getView(R.id.head_iv));
        }
    }
}
