package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.Product;

/**
 * Created by apple on 2018/4/11.
 */

public class ProductAdapter extends BaseQuickAdapter<Product,BaseViewHolder> {

    public ProductAdapter( @Nullable List<Product> data) {
        super(R.layout.product_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_desc,item.getDesc());
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.iv_product_logo));

    }
}
