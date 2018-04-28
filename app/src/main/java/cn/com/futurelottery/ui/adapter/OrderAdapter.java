package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.Order;

/**
 * Created by tantan on 2018/4/25.
 */

public class OrderAdapter extends BaseQuickAdapter<Order,BaseViewHolder>{

    private ArrayList<Order> data;


    public OrderAdapter(@Nullable ArrayList<Order> data) {
        super(R.layout.order_item,data);
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, Order item) {

        String[] time = item.getCreated_at().split("-");
        helper.setText(R.id.month_tv,time[0]+"月");
        helper.setText(R.id.day_tv,time[1]);
        helper.setText(R.id.name_tv,item.getName());
        helper.setText(R.id.money_tv,item.getPay_money()+"元  "+("0".equals(item.getIs_chasing())?"普通订单":"追号订单"));
        TextView tv=helper.getView(R.id.lottery_tv);
        if ("2".equals(item.getStatus())){
            tv.setTextColor(mContext.getResources().getColor(R.color.color_33));
            tv.setText("正在委托中");
        }else {
            if ("1".equals(item.getIs_chasing())){
                tv.setTextColor(mContext.getResources().getColor(R.color.color_33));
                tv.setText("追号进行时");
            }else {
                if ("0".equals(item.getOpenmatch())){
                    tv.setTextColor(mContext.getResources().getColor(R.color.color_33));
                    tv.setText("等待开奖");
                }else if ("2".equals(item.getOpenmatch())){
                    tv.setTextColor(mContext.getResources().getColor(R.color.color_33));
                    tv.setText("未中奖");
                }else if ("3".equals(item.getOpenmatch())){
                    tv.setTextColor(mContext.getResources().getColor(R.color.red_ball));
                    tv.setText("中奖"+item.getWinning_money());
                }
            }
        }
    }
}
