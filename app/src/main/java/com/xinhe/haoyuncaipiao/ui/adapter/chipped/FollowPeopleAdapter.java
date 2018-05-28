package com.xinhe.haoyuncaipiao.ui.adapter.chipped;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.ChippedDetail;

/**
 * Created by tantan on 2018/5/18.
 */

public class FollowPeopleAdapter extends BaseQuickAdapter<ChippedDetail.InfoProduct,BaseViewHolder>{

    private String openmuch="0";
    private String status="";

    public FollowPeopleAdapter(ArrayList<ChippedDetail.InfoProduct> data){
        super(R.layout.follow_people_item,data);
    }

    public void refresh(String openmuch,String status){
        this.openmuch=openmuch;
        this.status=status;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, ChippedDetail.InfoProduct item) {
        helper.setText(R.id.name_tv,item.getUser_name());
        helper.setText(R.id.money_tv,item.getPay_money_total()+"元");
        helper.setText(R.id.time_tv,item.getCreated_at());
        if ("2".equals(status)) {
            helper.setText(R.id.lottery_tv,"正在委托中");
        } else if ("3".equals(status)) {
            helper.setText(R.id.lottery_tv,"委托失败");
        } else {
            if ("0".equals(openmuch)){
                helper.setText(R.id.lottery_tv,"等待开奖");
            }else if ("3".equals(openmuch)){
                helper.setTextColor(R.id.lottery_tv,mContext.getResources().getColor(R.color.red_ball));
                helper.setText(R.id.lottery_tv,item.getWinning_money());
            }else {
                helper.setText(R.id.lottery_tv,"未中奖");
            }
        }
    }
}
