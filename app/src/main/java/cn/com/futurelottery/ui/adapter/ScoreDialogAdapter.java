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
import cn.com.futurelottery.utils.AppUtils;

/**
 * Created by apple on 2018/4/19.
 */

public class ScoreDialogAdapter extends BaseQuickAdapter<Integer,BaseViewHolder> {
    private int  mWidth;
    public ScoreDialogAdapter(@Nullable List<Integer> data,int width) {
        super(R.layout.score_item, data);
        this.mWidth=width;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        RelativeLayout view = helper.getView(R.id.layout);
        //helper.setText(R.id.layout,item+"");


        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            if(item==1){
                flexboxLp.setFlexBasisPercent(5);
            }
            flexboxLp.setFlexGrow(1f);
        }

    }
}
