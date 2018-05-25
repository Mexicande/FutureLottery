package cn.com.futurelottery.ui.adapter.chipped;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.ChippedDetail;

/**
 * Created by tantan on 2018/5/18.
 */

public class FollowPeopleAdapter extends BaseQuickAdapter<ChippedDetail.InfoProduct,BaseViewHolder>{

    private String openmuch="0";

    public FollowPeopleAdapter(ArrayList<ChippedDetail.InfoProduct> data){
        super(R.layout.follow_people_item,data);
    }

    public void refresh(String openmuch){
        this.openmuch=openmuch;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, ChippedDetail.InfoProduct item) {
        helper.setText(R.id.name_tv,item.getUser_name());
        helper.setText(R.id.money_tv,item.getPay_money_total()+"元");
        helper.setText(R.id.time_tv,item.getCreated_at());
        if ("0".equals(openmuch)){
            helper.setText(R.id.lottery_tv,"等待开奖");
        }else {
            if ("0".equals(item.getWinning_money())||"0.00".equals(item.getWinning_money())){
                helper.setText(R.id.lottery_tv,"未中奖");
            }else {
                helper.setTextColor(R.id.lottery_tv,mContext.getResources().getColor(R.color.red_ball));
                helper.setText(R.id.lottery_tv,item.getWinning_money());
            }
        }
    }
}
