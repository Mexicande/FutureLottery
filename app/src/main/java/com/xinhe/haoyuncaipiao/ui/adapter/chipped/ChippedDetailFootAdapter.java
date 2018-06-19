package com.xinhe.haoyuncaipiao.ui.adapter.chipped;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.ChippedDetail;
import com.xinhe.haoyuncaipiao.model.Lesson;

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

        helper.setText(R.id.session_tv,"周"+item.getWeek()+"\n"+item.getTe());
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
     /*   SmartTable table= helper.getView(R.id.table);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowYSequence(false);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(mContext,11)); //设置全局字体大小
        final List<Lesson> students  = new ArrayList<>();
        for(int i=0;i<item.getInfo().size();i++)


        Column<String> studentNameColumn = new Column<>("姓名","name");
        Column<Integer> studentAgeColumn = new Column<>("年龄","age");
        final TableData<Lesson> tableData = new TableData<>("课程表",students);
        table.setTableData(tableData);
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int screenWith = wm.getDefaultDisplay().getWidth();
        table.getConfig().setMinTableWidth(screenWith);*/



    }
}
