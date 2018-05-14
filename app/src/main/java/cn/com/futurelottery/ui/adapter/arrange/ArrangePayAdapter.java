package cn.com.futurelottery.ui.adapter.arrange;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.model.Arrange;

/**
 * Created by tantan on 2018/5/7.
 */

public class ArrangePayAdapter extends BaseQuickAdapter<Arrange,BaseViewHolder> {
    private String[] typeLine3=new String[]{"直选","直选","组三复式","组六"};
    private String[] type3D=new String[]{"直选","直选","组三单式","组三复式","组六"};
    private ArrayList<Arrange> data;
    private String kind;

    public ArrangePayAdapter( @Nullable ArrayList<Arrange> data,String kind) {
        super(R.layout.choose_ball_payment_item, data);
        this.data=data;
        this.kind=kind;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Arrange item) {
        String balls="";
        if (Contacts.Lottery.P5name.equals(kind)){
            balls=spit(item.getAbsolutely())+"|"+spit(item.getThousand())+"|"+spit(item.getHundred())+"|"+spit(item.getTen())+"|"+spit(item.getIndividual());
            helper.setText(R.id.payment_item_balls,balls)
                    .setText(R.id.payment_item_balls_type,"单式"+"  "+item.getNotes()+"注 "+item.getMoney()+"元");
        }else if (Contacts.Lottery.P3name.equals(kind)){
            if (item.getType()==1){
                //直选
                balls=spit(item.getHundred())+"|"+spit(item.getTen())+"|"+spit(item.getIndividual());
            }else {
                //组三组六
                balls=spit(item.getIndividual());
            }

            helper.setText(R.id.payment_item_balls,balls)
                    .setText(R.id.payment_item_balls_type,typeLine3[item.getType()]+"  "+item.getNotes()+"注 "+item.getMoney()+"元");
        }else if (Contacts.Lottery.D3name.equals(kind)){
            if (item.getType()==1){
                //直选
                String[] split = item.getZhi().split(",");
                for (int i=0;i<split.length;i++){
                    if (i==0){
                        balls=spit(split[i]);
                    }else {
                        balls=balls+"|"+spit(split[i]);
                    }
                }
            }else if (item.getType()==2){
                //组三单式
            }else if (item.getType()==3){
                //组三复式
                balls=item.getThree_fu();
            }else if (item.getType()==4){
                //组六
                balls=item.getSix();
            }

            helper.setText(R.id.payment_item_balls,balls)
                    .setText(R.id.payment_item_balls_type,type3D[item.getType()]+"  "+item.getNotes()+"注 "+item.getMoney()+"元");
        }

        helper.addOnClickListener(R.id.payment_item_delet_iv);
    }

    //给字符串添加逗号
    private String spit(String s) {
        String result = "";
        char[] chars = s.toCharArray();
        for (int i=0;i<chars.length;i++){
            if (i==0){
                result=result+chars[i];
            }else {
                result=result+","+chars[i];
            }
        }
        return result;
    }


}
