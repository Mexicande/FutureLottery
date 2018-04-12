package cn.com.futurelottery.view.topRightMenu;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.view.supertextview.SuperButton;

/**
 * Created by apple on 2018/4/11.
 */

public class TopMenuAdapter extends BaseQuickAdapter<MenuItem,BaseViewHolder> {
    private OnTopRightMenuItemClickListener mItemClickListener;
    private boolean isShowIcon;
    private TopRightMenu mTopRightMenu;
    public TopMenuAdapter(int layoutResId, List<MenuItem> items, boolean isShowIcon, TopRightMenu topRightMenu) {
        super(layoutResId, items);
        this.isShowIcon = isShowIcon;
        mTopRightMenu = topRightMenu;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MenuItem item) {

        if (isShowIcon) {
            helper.getView(R.id.iv_menu_item_icon).setVisibility(View.VISIBLE);
            int resId = item.getIcon();
            helper.setImageResource(R.id.iv_menu_item_icon,resId < 0 ? 0:resId);
        }else {
            helper.getView(R.id.iv_menu_item_icon).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_menu_item_text,item.getContent());

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mItemClickListener) {
                    mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());
                    mTopRightMenu.dismiss();
                }
            }
        });
    }

    void setShowIcon(boolean isShowIcon) {
        this.isShowIcon = isShowIcon;
        notifyDataSetChanged();
    }
    void setOnTopRightMenuItemClickListener(OnTopRightMenuItemClickListener listener) {
        mItemClickListener = listener;
    }
}
