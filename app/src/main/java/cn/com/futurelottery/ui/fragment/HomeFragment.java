package cn.com.futurelottery.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.model.Product;
import cn.com.futurelottery.ui.activity.DoubleBallActivity;
import cn.com.futurelottery.ui.adapter.ProductAdapter;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.ProductItemDecoration;
import cn.com.futurelottery.view.marqueeview.MarqueeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.main_Recycler)
    RecyclerView mMainRecycler;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    BGABanner mConvenientBanner;
    MarqueeView marqueeView;
    ArrayList<String> mList = new ArrayList<>();
    List<String> info = new ArrayList<>();
    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    private ProductAdapter mProductAdapter;
    private ArrayList<Product> mProductList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutTopBack.setVisibility(View.GONE);
        initView();
        initDate();
        setListener();
    }


    private void initView() {
        LinearLayout temp = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.head_layout, null);
        marqueeView = temp.findViewById(R.id.marqueeView);
        mConvenientBanner = temp.findViewById(R.id.banner_fresco_demo_content);
        mConvenientBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(getActivity())
                        .load(model)
                        .into(itemView);
            }
        });
        mConvenientBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {

            }
        });
        mProductAdapter = new ProductAdapter(null);
        mMainRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mMainRecycler.addItemDecoration(new ProductItemDecoration(CommonUtil.dip2px(10)));
        mMainRecycler.setAdapter(mProductAdapter);
        mProductAdapter.addHeaderView(temp);
    }

    private void initDate() {
        mList.clear();
        info.clear();
        mProductList.clear();
        mList.add("http://doll.anwenqianbao.com/data/upload/20180408/5ac9d5621f04f.png");
        mList.add("http://doll.anwenqianbao.com/data/upload/20180408/5ac9d5235553d.png");
        mList.add("http://doll.anwenqianbao.com/data/upload/20180408/5ac9903910141.png");
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName("双色球");
            product.setDesc("奖池超7亿");
            product.setImg("http://orqk6filp.bkt.clouddn.com/double_ball.png");
            mProductList.add(product);

        }
        info.add("恭喜！9410.00元竞彩足球奖金已被**02收入囊中");
        info.add("恭喜！10000.00元双色球奖金已被**05收入囊中");
        mConvenientBanner.setData(mList, null);
        marqueeView.startWithList(info);
        mProductAdapter.setNewData(mProductList);
    }

    private void setListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initDate();
                        mRefreshLayout.finishRefresh();
                    }
                }, 500);

            }
        });

        mProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ActivityUtils.startActivity(DoubleBallActivity.class);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

}
