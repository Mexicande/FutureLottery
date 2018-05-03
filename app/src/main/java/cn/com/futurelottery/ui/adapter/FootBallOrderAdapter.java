package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.FootBallOrder;

/**
 * Created by tantan on 2018/5/2.
 */

public class FootBallOrderAdapter extends BaseQuickAdapter<FootBallOrder.ArrProduct,BaseViewHolder>{

    public FootBallOrderAdapter(@Nullable List<FootBallOrder.ArrProduct> data) {
        super(R.layout.football_order_detail_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootBallOrder.ArrProduct item) {

        helper.setText(R.id.session_tv,item.getWeek()+"\n"+item.getTeam_id());
        String[] arr1 = item.getTeam().split(" *");
        String[] arr2 = arr1[0].split(":");
        helper.setText(R.id.vs_tv,arr2[0]+"\n"+arr1[1]+"\n"+arr2[1]);
        String select = item.getSelected();
        helper.setText(R.id.bet_tv,select);
        String result = item.getResult();
        helper.setText(R.id.bet_tv,result);

    }

}
