package com.xinhe.haoyuncaipiao.listener;

import com.xinhe.haoyuncaipiao.model.ScoreList;

/**
 * Created by apple on 2018/4/13.
 */

public interface DialogListener {
    void onDefeateComplete(int index,ScoreList.DataBean.MatchBean matchBean);
}
