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
    private ArrayList<String> arrRandomRed;
    private int isShow;

    public DoubleBallRedAdapter(Context context, ArrayList<String> arrRandomRed) {
        this.context = context;
        // 33 个红球
        for (int i = 1; i < 34; i++) {
            if (i < 10) {
                arrRedBall.add("0" + i);
            } else {
                arrRedBall.add(i + "");
            }
        }
        this.arrRandomRed = arrRandomRed;
        for (int i = 0; i < 33; i++) {
            hisSelected.put(i, false);
        }
    }

    //是否显示遗漏，0不显示，1显示
    public void updateData(int isShow, ArrayList<String> arrRandomRed){
        for (int i = 0; i < 33; i++) {
            hisSelected.put(i, false);
        }
        this.isShow=isShow;
        this.arrRandomRed = arrRandomRed;
        notifyDataSetChanged();
    }

    public void clearData(){
        for (int i = 0; i < 33; i++) {
            hisSelected.put(i, false);
        }
        if (this.arrRandomRed != null)
            this.arrRandomRed.clear();
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
        if (1==isShow){
            holder.tv.setVisibility(View.VISIBLE);
        }else {
            holder.tv.setVisibility(View.GONE);
        }
        holder.chkRed.setText(this.getItem(position).toString());
        holder.chkRed.setTextColor(ContextCompat.getColor(context, R.color.red_ball));
        holder.chkRed.setChecked(false);
        if (arrRandomRed!= null && arrRandomRed.indexOf(String.valueOf(position)) != -1) {
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
