package cn.com.futurelottery.ui.adapter.run;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.FootRun;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;

/**
 * Created by apple on 2018/4/18.
 * football 胜平负
 */

public class FootRunAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private OnTopRightMenuItemClickListener mItemClickListener;

    private int nu=0;

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public FootRunAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.football_item);
        addItemType(1, R.layout.run_foot_item);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_LEVEL_0:
                final FootRun.DataBean bean= (FootRun.DataBean) item;
                final int pos = helper.getAdapterPosition();
                helper.setImageResource(R.id.iv_arrow, bean.isExpanded() ? R.mipmap.iv_up_arrow : R.mipmap.iv_down_arrow);
                    helper.setText(R.id.tv_title,bean.getDate()+" "+bean.getWeek()+" "+bean.getCount()+"场比赛已开奖");
                if(helper.getAdapterPosition()==0){
                    helper.setVisible(R.id.tv_hot,true);
                }else {
                    helper.setVisible(R.id.tv_hot,false);
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }});
                break;
            case TYPE_LEVEL_1:
                final FootRun.DataBean.MatchResultBean matchBean= (FootRun.DataBean.MatchResultBean) item;
                List<FootRun.DataBean.MatchResultBean.ResultMsgBean> resultMsg = matchBean.getResultMsg();
                helper.setText(R.id.tv_league,matchBean.getLeague())
                        .setText(R.id.date,matchBean.getWeek()+" "+matchBean.getTeam_id())
                        .setText(R.id.team_home,matchBean.getHomeTeam())
                        .setText(R.id.team_away,matchBean.getAwayTeam())
                        .setText(R.id.tv_result,matchBean.getResult())
                        .setText(R.id.tv_firsthalf_result,matchBean.getHalf_result())
                        .setText(R.id.tv_msg1,resultMsg.get(0).getMsg())
                        .setText(R.id.tv_msg2,resultMsg.get(1).getMsg())
                        .setText(R.id.tv_msg3,resultMsg.get(2).getMsg())
                        .setText(R.id.tv_msg4,resultMsg.get(3).getMsg())
                        .setText(R.id.tv_msg5,resultMsg.get(4).getMsg())
                        .setText(R.id.tv_odds1,resultMsg.get(0).getOdds())
                        .setText(R.id.tv_odds2,resultMsg.get(1).getOdds())
                        .setText(R.id.tv_odds3,resultMsg.get(2).getOdds())
                        .setText(R.id.tv_odds4,resultMsg.get(3).getOdds())
                        .setText(R.id.tv_odds5,resultMsg.get(4).getOdds());


                break;
            default:
                break;
        }

    }

    public void setOnTopRightMenuItemClickListener(OnTopRightMenuItemClickListener listener) {
        mItemClickListener = listener;
    }

}
