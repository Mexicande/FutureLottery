package cn.com.futurelottery.ui.adapter;


import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.RedPacketUse;

/**
 * Created by tantan on 2018/5/22.
 */

public class RedPacketUseAdapter extends BaseQuickAdapter<RedPacketUse,BaseViewHolder> {
    public RedPacketUseAdapter(@Nullable List<RedPacketUse> data) {
        super(R.layout.red_packet_use_dialog_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedPacketUse item) {
        Glide.with(mContext).load(item.getLogo())
                .apply(new RequestOptions())
                .into((ImageView) helper.getView(R.id.logo_iv));
        helper.setText(R.id.name_tv,item.getTitle());
    }
}
