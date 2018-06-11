package com.xinhe.haoyuncaipiao.ui.adapter.chipped;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.ChippedDetail;

import java.util.List;

/**
 * Created by apple on 2018/6/8.
 */

public class ChippInfoAdapte extends BaseQuickAdapter<ChippedDetail.DataFootball.InfoBean,BaseViewHolder> {

    public ChippInfoAdapte(@Nullable List<ChippedDetail.DataFootball.InfoBean> data) {
        super(R.layout.chiped_info_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChippedDetail.DataFootball.InfoBean item) {
        String play = item.getPlay();
        if(play!=null){
            helper.setText(R.id.tv_play,item.getPlay());
        }else {
            helper.setText(R.id.tv_play,item.getPlay_type());
        }
        helper.setText(R.id.tv_result,item.getResult())
                .setText(R.id.tv_selected,item.getSelected());

    }

}
