package cn.com.futurelottery.view.topRightMenu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;

/**
 *
 * @author apple
 * @date 2018/4/12
 * title下拉菜单
 */

public class TRMenuAdapter extends BaseQuickAdapter<MenuItem,BaseViewHolder> {

    public TRMenuAdapter(int layout,List<MenuItem> menuItemList) {
        super(layout, menuItemList);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MenuItem item) {
        helper.setText(R.id.text,item.getContent());
        TextView view = helper.getView(R.id.text);
        if(item.getIcon()==0){
            view.setTextColor(mContext.getResources().getColor(R.color.color_333));
            view.setBackgroundResource(R.drawable.uncheck_bg);
        }else if(item.getIcon()==3){
            view.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            view.setBackgroundResource(R.drawable.un_red_check_bg);
        } else {
            view.setTextColor(mContext.getResources().getColor(R.color.white));
            view.setBackgroundResource(R.drawable.check_bg);
        }

    }

}
