package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.RedPacket;

/**
 * Created by tantan on 2018/5/21.
 */

public class RedPacketAdapter extends BaseQuickAdapter<RedPacket.DataProduct.InfoProduct,BaseViewHolder>{
    //红包是否可用1可用2不可用
    private final int isUse;

    public RedPacketAdapter(@Nullable List<RedPacket.DataProduct.InfoProduct> data, int isUse) {
        super(R.layout.red_packet_item,data);
        this.isUse=isUse;
    }

    @Override
    protected void convert(BaseViewHolder helper, RedPacket.DataProduct.InfoProduct item) {

        helper.addOnClickListener(R.id.use_tv);
        RelativeLayout logoRl = helper.getView(R.id.logo_iv);
        TextView mongeyTv = helper.getView(R.id.money_tv);
        TextView timeTv = helper.getView(R.id.time_tv);
        TextView useTv = helper.getView(R.id.use_tv);
        if (1==isUse){
            logoRl.setBackgroundResource(R.mipmap.red_packet_usable);
            mongeyTv.setTextColor(mContext.getResources().getColor(R.color.text_color_32));
            timeTv.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            useTv.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            useTv.setBackgroundResource(R.drawable.un_red_check_bg);
        }else {
            logoRl.setBackgroundResource(R.mipmap.red_packet_disable);
            mongeyTv.setTextColor(mContext.getResources().getColor(R.color.color_a9));
            timeTv.setTextColor(mContext.getResources().getColor(R.color.color_a9));
            useTv.setTextColor(mContext.getResources().getColor(R.color.color_a9));
            useTv.setBackgroundResource(R.drawable.uncheck_bg);
        }

        mongeyTv.setText("余额 "+item.getAmount()+"元");
        timeTv.setText("有效期 "+item.getEffective()+"天");
        helper.setText(R.id.logo_tv,item.getAmount());
        helper.setText(R.id.type_tv,item.getTitle());
        helper.setText(R.id.use_type_tv,item.getWhole()==1?"部分彩种使用":"全部彩种可用");
    }
}
