package cn.com.futurelottery.inter;

import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.ScoreList;

/**
 * Created by apple on 2018/4/13.
 */

public interface SizeDialogListener {
    void onDefeateComplete(int index, FootBallList.DataBean.MatchBean matchBean);
}
