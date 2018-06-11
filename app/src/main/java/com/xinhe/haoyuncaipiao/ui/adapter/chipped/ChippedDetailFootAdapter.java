package com.xinhe.haoyuncaipiao.ui.adapter.chipped;

import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.ChippedDetail;

/**
 *
 * @author apple
 * @date 2018/5/19
 * 混合
 */

public class ChippedDetailFootAdapter extends BaseQuickAdapter<ChippedDetail.DataFootball,BaseViewHolder>{
    private ChippInfoAdapte mChippInfoAdapte;
    public ChippedDetailFootAdapter(@Nullable List<ChippedDetail.DataFootball> data) {
        super(R.layout.football_order_detail_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChippedDetail.DataFootball item) {

        helper.setText(R.id.session_tv,item.getWeek()+"\n"+item.getTe());
        String[] arr1 = item.getNa().split("[*]");
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
