package com.xinhe.haoyuncaipiao.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fengjh on 16/7/31.
 */
public class MenuDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int mSpanCount;
    public MenuDecoration(int space,int spanCount) {
        this.space = space;
        this.mSpanCount=spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
      /*  outRect.left = 3;
        outRect.top = 0;
        outRect.bottom = 20;
/*        outRect.right = 3;*//*
        outRect.left = space;
        outRect.top = 40;
        outRect.bottom = 40;
        outRect.right = space;*/
        if(mSpanCount==3){
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = space;
                outRect.bottom = 40;
                outRect.right = space;
            } else {
                outRect.left = space;
                outRect.bottom = 40;
                outRect.right = space;
            }

        }else {
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = space;
                outRect.bottom = 40;
                outRect.right = space;
            } else {
                outRect.left = space;
                outRect.bottom = 40;
                outRect.right = space;
            }
        }



    }
}