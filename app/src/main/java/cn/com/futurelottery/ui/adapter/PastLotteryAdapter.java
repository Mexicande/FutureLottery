package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.AwardPeriod;

/**
 * Created by tantan on 2018/5/7.
 * 往期开奖
 */

public class PastLotteryAdapter extends BaseQuickAdapter<AwardPeriod,BaseViewHolder> {
    public PastLotteryAdapter( @Nullable List<AwardPeriod> data) {
        super(R.layout.past_lottery_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AwardPeriod item) {
        helper.setText(R.id.tv_date,item.getPeriod());
        int adapterPosition = helper.getAdapterPosition();
        LinearLayout view = helper.getView(R.id.award_layout);
        if(adapterPosition%2!=0){
            view.setBackgroundColor(mContext.getResources().getColor(R.color.white_bg));
        }else {
            view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        String period = item.getWinning();
        String replace = period.replace(",", " ");
        if(item.getType()==2){
            //排5
        }else if (item.getType()==3){
            //排3
            helper.setVisible(R.id.form_tv,true)
            .setText(R.id.form_tv,item.getMemo());
        }else if (item.getType()==4){
            //3D
            helper.setVisible(R.id.form_tv,true)
                    .setText(R.id.form_tv,item.getMemo());
        }
        helper.setText(R.id.tv_number,replace);

    }
}
