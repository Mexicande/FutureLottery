package cn.com.futurelottery.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import cn.com.futurelottery.R;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.TopRightMenu;

/**
 *
 * @author apple
 * @date 2018/4/12
 * 菜单更多
 */

public class TopRightMenuUtils {

    /**
     *
     * @param mList
     * @param view 双色球
     * @param mContext
     * @return
     */
    public static TopRightMenu  setDoubleBall(ArrayList<MenuItem> mList, View view, Context mContext){
        TopRightMenu  mtopRightMenu = new TopRightMenu(mContext);
        mtopRightMenu.addMenuItems(mList);
        mtopRightMenu.setWidth(230)
                .setHeight(350)
                .setShowIcon(false)
                .setShowAnimationStyle(true)
                .setAnimationStyle(R.style.TopRightMenu_Anim)
                .setShowBackground(true)
                .setArrowPosition(CommonUtil.dip2px(55f));
        mtopRightMenu.showAsDropDown(view,-50,0);
        return mtopRightMenu;
    }



    public static void initActionBar(Toolbar mToolbar, final Activity mContext) {
        mToolbar.setNavigationIcon(R.drawable.iv_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
            }
        });
    }

}
