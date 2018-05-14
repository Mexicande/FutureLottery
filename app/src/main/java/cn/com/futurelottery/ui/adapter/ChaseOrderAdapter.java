package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.ChaseOrder;

/**
 * Created by tantan on 2018/5/14.
 */

public class ChaseOrderAdapter extends BaseQuickAdapter<ChaseOrder.DataProduct.InfoProduct,BaseViewHolder> {


public ChaseOrderAdapter(@Nullable ArrayList<ChaseOrder.DataProduct.InfoProduct> data) {
        super(R.layout.chase_order_item,data);
        }

@Override
protected void convert(BaseViewHolder helper, ChaseOrder.DataProduct.InfoProduct item) {

    helper.setText(R.id.phase_tv, item.getStart_periods() + "期");
    helper.setText(R.id.money_tv, item.getPay_money_total());
    TextView tv = helper.getView(R.id.status_tv);
    if ("2".equals(item.getStatus())) {
        tv.setTextColor(mContext.getResources().getColor(R.color.color_33));
        tv.setText("正在委托中");
    } else if ("3".equals(item.getStatus())) {
        tv.setTextColor(mContext.getResources().getColor(R.color.color_33));
        tv.setText("委托失败");
    } else {
        if ("0".equals(item.getOpenmatch())) {
            tv.setTextColor(mContext.getResources().getColor(R.color.color_33));
            tv.setText("等待开奖");
        } else if ("2".equals(item.getOpenmatch())) {
            tv.setTextColor(mContext.getResources().getColor(R.color.color_33));
            tv.setText("未中奖");
        } else if ("3".equals(item.getOpenmatch())) {
            tv.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            tv.setText("中奖" + item.getWinning_money());
        }
    }
}
}
