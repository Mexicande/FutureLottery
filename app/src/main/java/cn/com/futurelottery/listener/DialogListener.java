package cn.com.futurelottery.listener;

import cn.com.futurelottery.model.ScoreList;

/**
 * Created by apple on 2018/4/13.
 */

public interface DialogListener {
    void onDefeateComplete(int index,ScoreList.DataBean.MatchBean matchBean);
}
