package cn.com.futurelottery.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.com.futurelottery.R;

/**
 * 自定义蓝球的适配器
 * @author Tobin
 *
 */
public class DoubleBallBlueAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> arrBlueBall = new ArrayList<>();
    private ArrayList<String> arrRandomBlue;
    //记录Ckeckbox的选中状态
    public static Map<Integer, Boolean> lisSelected;
    private int isShow;

    public DoubleBallBlueAdapter(Context context, ArrayList<String> arrRandomBlue) {
        this.context = context;
        this.arrRandomBlue = arrRandomBlue;
        for (int i = 1; i < 17; i++) {
            if (i < 10) {
                arrBlueBall.add("0" + i);
            } else {
                arrBlueBall.add(i + "");
            }
        }
        lisSelected = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            lisSelected.put(i, false);
        }
    }
    //是否显示遗漏，0不显示，1显示
    public void updateData(int isShow,ArrayList<String> arrRandomBlue){
        for (int i = 0; i < 16; i++) {
            lisSelected.put(i, false);
        }
        this.isShow=isShow;
        this.arrRandomBlue = arrRandomBlue;
        notifyDataSetChanged();
    }

    public void clearData(){
        for (int i = 0; i < 16; i++) {
            lisSelected.put(i, false);
        }
        if (this.arrRandomBlue != null)
            this.arrRandomBlue.clear();
        notifyDataSetChanged();
    }

    public Map<Integer, Boolean> getSelected(){
        return lisSelected;
    }

    @Override
    public int getCount() {
        return arrBlueBall.size();
    }

    @Override
    public Object getItem(int position) {
        return arrBlueBall.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LanGridViewHolder holder;
        if (convertView == null) {
            holder = new LanGridViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.doubleball_blue_item, parent, false);
            holder.chkBlue = (CheckBox) convertView.findViewById(R.id.check_blue);
            holder.tv = (TextView) convertView.findViewById(R.id.omit_tv);
            convertView.setTag(holder);
        }else{
            holder = (LanGridViewHolder) convertView.getTag();
        }
        if (1==isShow){
            holder.tv.setVisibility(View.VISIBLE);
        }else {
            holder.tv.setVisibility(View.GONE);
        }
        holder.chkBlue.setText(this.getItem(position).toString());
        holder.chkBlue.setTextColor(ContextCompat.getColor(context, R.color.blue_ball));
        holder.chkBlue.setChecked(false);
        if (arrRandomBlue != null && arrRandomBlue.indexOf(String.valueOf(position)) != -1) {
            lisSelected.put(position, true);
            holder.chkBlue.setChecked(lisSelected.get(position));
            holder.chkBlue.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        }

        return convertView;
    }

    public final class LanGridViewHolder {
        public CheckBox chkBlue;
        public TextView tv;
    }

}
