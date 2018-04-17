package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.AwardPeriod;

/**
 * Created by apple on 2018/4/16.
 */

public class AwardPeriodAdapter extends BaseQuickAdapter<AwardPeriod,BaseViewHolder> {

    public AwardPeriodAdapter( @Nullable List<AwardPeriod> data) {
        super(R.layout.award_period_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AwardPeriod item) {
        helper.setText(R.id.tv_date,item.getPeriod());
        int adapterPosition = helper.getAdapterPosition();
        RelativeLayout view = helper.getView(R.id.award_layout);
        if(adapterPosition%2!=0){
            view.setBackgroundColor(mContext.getResources().getColor(R.color.white_bg));
        }else {
            view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        String period = item.getWinning();
        String replace = period.replace(",", " ");
        //改变篮球的颜色
        SpannableStringBuilder builder = new SpannableStringBuilder(replace);
        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_ball));
        if(item.getType()==1){
            builder.setSpan(yellowSpan, replace.length()-5,replace.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else {
            builder.setSpan(yellowSpan, replace.length()-2,replace.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        helper.setText(R.id.tv_number,builder);

    }
}
