package com.xinhe.haoyuncaipiao.ui.adapter.chipped;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.BaseApplication;
import com.xinhe.haoyuncaipiao.model.ChippedDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author apple
 * @date 2018/5/19
 * 混合之外
 */

public class ChippedOtherDetailFootAdapter extends BaseQuickAdapter<ChippedDetail.DataFootball,BaseViewHolder>{
    private FormChippedAdapter mFormChippedAdapter;


    public ChippedOtherDetailFootAdapter(@Nullable List<ChippedDetail.DataFootball> data) {
        super(R.layout.football_order_other_detail_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChippedDetail.DataFootball item) {

        helper.setText(R.id.session_tv,item.getWeek()+"\n"+item.getTe());
        String[] arr1 = item.getNa().split("[*]");
        String[] arr2 = arr1[0].split(":");

        if ("让球胜平负".equals(item.getPlay())) {
            helper.setText(R.id.vs_tv,"("+item.getLetpoint()+")"+arr2[0]+"\n"+arr1[1]+"\n"+arr2[1]);
        }else {
            helper.setText(R.id.vs_tv,arr2[0]+"\n"+arr1[1]+"\n"+arr2[1]);
        }
        //赔率
        String select = item.getSelected();
        String[] split = select.split("[*]");
        List<String> list= Arrays.asList(split);

        String result = item.getResult();
        helper.setText(R.id.caiguo_tv,result)
                .setText(R.id.play,item.getPlay());

        RecyclerView recyclerView = helper.getView(R.id.form_recycler);
        mFormChippedAdapter=new FormChippedAdapter(null);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mFormChippedAdapter);
        mFormChippedAdapter.addData(list);

    }
}
