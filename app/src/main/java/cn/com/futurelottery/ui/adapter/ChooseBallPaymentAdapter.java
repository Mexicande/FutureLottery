package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.DoubleBall;

/**
 * Created by tantan on 2018/4/13.
 */

public class ChooseBallPaymentAdapter extends BaseQuickAdapter<DoubleBall,BaseViewHolder>{

    private String[] type=new String[]{"单式","复式","胆拖"};
    private ArrayList<DoubleBall> data;

    public ChooseBallPaymentAdapter( @Nullable ArrayList<DoubleBall> data) {
        super(R.layout.choose_ball_payment_item, data);
        this.data=data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DoubleBall item) {
        String balls="";
        if (TextUtils.isEmpty(item.getDan())){
            balls=item.getRed().replace(","," ")+" "+item.getBlu().replace(","," ");
        }else {
            balls="("+item.getDan().replace(","," ")+")"+item.getRed().replace(","," ")+" "+item.getBlu().replace(","," ");
        }
        //改变篮球的颜色
        SpannableStringBuilder builder = new SpannableStringBuilder(balls);
        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_ball));
        builder.setSpan(yellowSpan, balls.length()-item.getBlu().length(),balls.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.payment_item_balls,builder)
        .setText(R.id.payment_item_balls_type,type[item.getType()]+"  "+item.getZhushu()+"注 "+item.getMoney()+"元")
        .addOnClickListener(R.id.payment_item_delet_iv);
    }

}
