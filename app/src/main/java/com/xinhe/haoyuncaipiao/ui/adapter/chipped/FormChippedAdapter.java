package com.xinhe.haoyuncaipiao.ui.adapter.chipped;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.Chipped;

import java.util.List;

/**
 * Created by apple on 2018/6/11.
 */

public class FormChippedAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public FormChippedAdapter( @Nullable List<String> data) {
        super(R.layout.form_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_play,item);
    }
}
