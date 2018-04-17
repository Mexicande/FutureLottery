package cn.com.futurelottery.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.com.futurelottery.R;

/**
 * 自定义红球适配器
 * @author Tobin
 *
 */
public class DoubleBallRedAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> arrRedBall = new ArrayList<>();
    public Map<Integer, Boolean> hisSelected  = new HashMap<>();
    ArrayList<String> omits=new ArrayList<>();
    private ArrayList<String> chooseBall;
    private int isShow;
    private int ballCount;

    public DoubleBallRedAdapter(Context context, ArrayList<String> chooseBall, int isShow, int ballCount) {
        this.context = context;
        this.chooseBall = chooseBall;
        this.isShow = isShow;
        this.ballCount = ballCount;
        // 总球数
        for (int i = 1; i <= ballCount; i++) {
            if (i < 10) {
                arrRedBall.add("0" + i);
            } else {
                arrRedBall.add(i + "");
            }
        }
        // 选择球数
        for (int i = 0; i < ballCount; i++) {
            hisSelected.put(i, false);
        }
    }

    //是否显示遗漏，0不显示，1显示
    public void updateData(int isShow, ArrayList<String> arrRandomRed,ArrayList<String> omits){
        for (int i = 0; i < ballCount; i++) {
            hisSelected.put(i, false);
        }
        this.omits=omits;
        this.isShow=isShow;
        this.chooseBall = arrRandomRed;
        notifyDataSetChanged();
    }

    public void clearData(){
        for (int i = 0; i < ballCount; i++) {
            hisSelected.put(i, false);
        }
        if (this.chooseBall != null)
            this.chooseBall.clear();
        notifyDataSetChanged();
    }

    public Map<Integer, Boolean> getSelected(){
        return hisSelected;
    }

    @Override
    public int getCount() {
        return arrRedBall.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRedBall.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        redGridViewHolder holder;
        if (convertView == null) {
            holder = new redGridViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.doubleball_red_item, parent, false);
            holder.chkRed = (CheckBox) convertView.findViewById(R.id.check_red);
            holder.tv = (TextView) convertView.findViewById(R.id.omit_tv);
            convertView.setTag(holder);
        }else{
            holder = (redGridViewHolder) convertView.getTag();
        }

        //遗漏
        if (1==isShow){
            holder.tv.setVisibility(View.VISIBLE);
        }else {
            holder.tv.setVisibility(View.GONE);
        }
        if (position<omits.size()){
            holder.tv.setText(omits.get(position));
        }


        holder.chkRed.setText(this.getItem(position).toString());
        holder.chkRed.setTextColor(ContextCompat.getColor(context, R.color.red_ball));
        holder.chkRed.setChecked(false);
        if (chooseBall!= null && chooseBall.indexOf(String.valueOf(position)) != -1) {
            hisSelected.put(position, true);
            holder.chkRed.setChecked(hisSelected.get(position));
            holder.chkRed.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        }

        holder.chkRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        return convertView;
    }

    public final class redGridViewHolder {
        public CheckBox chkRed;
        public TextView tv;
    }
}
