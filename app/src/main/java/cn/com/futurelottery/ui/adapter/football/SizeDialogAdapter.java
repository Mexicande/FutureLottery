package cn.com.futurelottery.ui.adapter.football;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.ScoreList;

/**
 * Created by apple on 2018/4/19.
 */

public class SizeDialogAdapter extends BaseQuickAdapter<FootBallList.DataBean.MatchBean.OddsBean,BaseViewHolder> {
    List<FootBallList.DataBean.MatchBean.OddsBean> mList;
    public SizeDialogAdapter(@Nullable List<FootBallList.DataBean.MatchBean.OddsBean> data) {
        super(R.layout.size_bet_item, data);
        this.mList=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, FootBallList.DataBean.MatchBean.OddsBean item) {

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
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
                flexboxLp.setFlexGrow(1f);
        }

    }
}
