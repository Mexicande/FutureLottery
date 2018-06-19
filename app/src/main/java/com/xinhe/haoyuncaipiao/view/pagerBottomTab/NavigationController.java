package com.xinhe.haoyuncaipiao.view.pagerBottomTab;


import android.support.v4.view.ViewPager;

import com.xinhe.haoyuncaipiao.model.PaySucessEvent;
import com.xinhe.haoyuncaipiao.view.pagerBottomTab.listener.OnTabItemSelectedListener;

import org.greenrobot.eventbus.EventBus;


public class NavigationController implements ItemController,BottomLayoutController{

    private BottomLayoutController mBottomLayoutController;
    private ItemController mItemController;

    public NavigationController(BottomLayoutController bottomLayoutController, ItemController itemController) {
        mBottomLayoutController = bottomLayoutController;
        mItemController = itemController;
    }

    @Override
    public void setSelect(int index) {

        mItemController.setSelect(index);
    }


    @Override
    public void addTabItemSelectedListener(OnTabItemSelectedListener listener) {
        mItemController.addTabItemSelectedListener(listener);
    }

    @Override
    public int getSelected() {
        return mItemController.getSelected();
    }

    @Override
    public int getItemCount() {
        return mItemController.getItemCount();
    }

    @Override
    public String getItemTitle(int index) {
        return mItemController.getItemTitle(index);
    }

    @Override
    public void setupWithViewPager(ViewPager viewPager) {
        mBottomLayoutController.setupWithViewPager(viewPager);
    }

    @Override
    public void hideBottomLayout() {
        mBottomLayoutController.hideBottomLayout();
    }

    @Override
    public void showBottomLayout() {
        mBottomLayoutController.showBottomLayout();
    }
}
