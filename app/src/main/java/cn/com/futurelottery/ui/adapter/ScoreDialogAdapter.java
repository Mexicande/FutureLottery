package cn.com.futurelottery.ui.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.DoubleBall;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.utils.AppUtils;

/**
 *
 * @author apple
 * @date 2018/4/19
 * 比分下单
 */

public class ScoreDialogAdapter extends BaseQuickAdapter<ScoreList.DataBean.MatchBean.OddsBean,BaseViewHolder> {
    List<ScoreList.DataBean.MatchBean.OddsBean> mList;
    public ScoreDialogAdapter(@Nullable List<ScoreList.DataBean.MatchBean.OddsBean> data) {
        super(R.layout.score_item, data);
        this.mList=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreList.DataBean.MatchBean.OddsBean item) {
        RelativeLayout view = helper.getView(R.id.layout);
        helper.setText(R.id.tv_odds,item.getOdds())
        .setText(R.id.tv_name,item.getName());
        if(item.getType()==1){
            helper.setBackgroundColor(R.id.layout,mContext.getResources().getColor(R.color.red_ball));
            helper.setTextColor(R.id.tv_odds,mContext.getResources().getColor(R.color.white));
            helper.setTextColor(R.id.tv_name,mContext.getResources().getColor(R.color.white));
        }else {
            helper.setBackgroundColor(R.id.layout,mContext.getResources().getColor(R.color.white));
            helper.setTextColor(R.id.tv_odds,mContext.getResources().getColor(R.color.black));
            helper.setTextColor(R.id.tv_name,mContext.getResources().getColor(R.color.color_333));
        }

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams fireboxLp = (FlexboxLayoutManager.LayoutParams) lp;
                fireboxLp.setFlexGrow(1f);
        }

    }
}
