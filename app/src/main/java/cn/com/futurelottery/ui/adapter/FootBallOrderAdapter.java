package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.FootBallOrder;

/**
 * Created by tantan on 2018/5/2.
 */

public class FootBallOrderAdapter extends BaseQuickAdapter<FootBallOrder.DataProduct.ArrProduct,BaseViewHolder>{

    public FootBallOrderAdapter(@Nullable List<FootBallOrder.DataProduct.ArrProduct> data) {
        super(R.layout.football_order_detail_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootBallOrder.DataProduct.ArrProduct item) {
        try {
            String week = "";
            switch (item.getWeek()){
                case "1":
                    week = "星期一";
                    break;
                case "2":
                    week = "星期二";
                    break;
                case "3":
                    week = "星期三";
                    break;
                case "4":
                    week = "星期四";
                    break;
                case "5":
                    week = "星期五";
                    break;
                case "6":
                    week = "星期六";
                    break;
                case "7":
                    week = "星期日";
                    break;
            }
            helper.setText(R.id.session_tv,week+"\n"+item.getTeam_id());
            String[] arr1 = item.getTeam().split("[*]");
            String[] arr2 = arr1[0].split(":");
            helper.setText(R.id.vs_tv,arr2[0]+"\n"+arr1[1]+"\n"+arr2[1]);
            //赔率
            String select = item.getSelected();
            String rate = "";
            if (select.contains(".")){
                rate=select.replace(".","\n");
            }else {
                rate=select;
            }
            helper.setText(R.id.bet_tv,rate);
            String result = item.getResult();
            helper.setText(R.id.caiguo_tv,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
