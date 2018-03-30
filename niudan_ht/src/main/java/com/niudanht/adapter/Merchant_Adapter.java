package com.niudanht.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.niudanht.admin.MerchantActivity;
import com.niudanht.admin.R;

import java.util.ArrayList;
import java.util.List;

import ktx.pojo.domain.SMCustomerReward;
import order.listview.XListView;

/**
 * Created by LYY on 2018/3/29.
 */

public class Merchant_Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<SMCustomerReward> datalist;


    public Merchant_Adapter(Context context, ArrayList<SMCustomerReward> datalist) {
        this.context = context;
        this.datalist = datalist;
      //  Toast.makeText(context,""+datalist,Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
 ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lay_merchant_item, null);
        holder = new ViewHolder();

            holder.merchant_shop = (TextView) convertView.findViewById(R.id.merchant_shop);
            holder.merchant_name = (TextView) convertView.findViewById(R.id.merchant_name);
            holder.merchant_phone = (TextView) convertView.findViewById(R.id.merchant_phone);
            holder.text2 = (TextView) convertView.findViewById(R.id.text2);
            holder.text3 = (TextView) convertView.findViewById(R.id.text3);
            holder.text5 = (TextView) convertView.findViewById(R.id.text5);
            holder.text6 = (TextView) convertView.findViewById(R.id.text6);

            holder.text9 = (TextView) convertView.findViewById(R.id.text9);
            holder.text12 = (TextView) convertView.findViewById(R.id.text12);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();


        }
       //用户姓名

        String openBankName = datalist.get(position).OpenBankName;
        String contactName = datalist.get(position).ContactName;
        //手机号
        String phone = datalist.get(position).Phone;
        //99元会员卡数量
        int cardNum1 = datalist.get(position).CardNum1;
        float cardCash1 = datalist.get(position).CardCash1;
        //150次会员数量
        int cardNum2 = datalist.get(position).CardNum2;
        float cardCash2 = datalist.get(position).CardCash2;
        //累计体成款
        float cardAwardCash = datalist.get(position).CardAwardCash;
        //支付提成款
        float cardAwardSetCash = datalist.get(position).CardAwardSetCash;
        //名字
        if (contactName != null || !"".equals(contactName)) {
            holder.merchant_name.setText(contactName);
        }
        //电话
        if (phone != null || !"".equals(phone)) {
            holder.merchant_phone.setText(phone);
        }

        if (openBankName != null || !"".equals(openBankName)) {
            holder.merchant_shop.setText(openBankName);
        }

        holder.text2.setText(String.format("%d位", cardNum1));
        holder.text3.setText(String.format("%.1f元", cardCash1));
        holder.text5.setText(String.format("%d位", cardNum2));
        holder.text6.setText(String.format("%.1f元", cardCash2));
        holder.text9.setText(String.format("%.1f元", cardAwardCash));
        holder.text12.setText(String.format("%.1f元", cardAwardSetCash));

        return convertView;
    }

    public void setNewListInfo(ArrayList<SMCustomerReward> smCustomerRewards) {
        this.datalist = smCustomerRewards;
    }


    class ViewHolder {

        private TextView merchant_shop;
        private TextView merchant_name;
        private TextView merchant_phone;
        private TextView text2;
        private TextView text3;
        private TextView text5;
        private TextView text6;
        private TextView text9;
        private TextView text12;

    }
}
