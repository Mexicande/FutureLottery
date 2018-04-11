package cn.com.futurelottery.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fengjh on 16/7/31.
 */
public class ProductItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public ProductItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
      /*  outRect.left = 3;
        outRect.top = 0;
        outRect.bottom = 20;
        outRect.right = 3;*/
        if(parent.getChildLayoutPosition(view)%2 == 0){
            outRect.right = 20;
            outRect.top = 40;
            outRect.bottom = 0;
            outRect.left = 30;
        }else{
            outRect.left = 30;
            outRect.top = 40;
            outRect.bottom = 0;
            outRect.right = 10;
        }
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = 0;
            outRect.bottom = 0;
            outRect.right = 0;
            outRect.top = 0;
        }
       /* if(parent.getChildLayoutPosition(view)<4){
            outRect.top = 20;
        }*/



      /*  if(space!=0){
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.left = 0;
                outRect.bottom = 20;
                outRect.right = 0;
                outRect.top = 0;
            }
        }*/
    }
}