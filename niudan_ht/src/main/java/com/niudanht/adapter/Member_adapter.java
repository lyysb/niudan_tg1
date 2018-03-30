package com.niudanht.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.niudanht.admin.MemberActivity;
import com.niudanht.admin.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ktx.pojo.domain.SMCustomerReward;
import ktx.pojo.domain.SMUserReward;
import ktx.pojo.domain.StatisticsInfo;
import niudanht.Application;

/**
 * Created by LYY on 2018/3/28.
 */

public class Member_adapter extends BaseAdapter {


    private Context context;
    private ArrayList<SMUserReward> smulist;

    public Member_adapter(Context context, ArrayList<SMUserReward> smulist) {
        this.context = context;
        this.smulist = smulist;
    }

    @Override
    public int getCount() {
        return smulist.size();
    }

    @Override
    public Object getItem(int position) {
        return smulist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder = null;


        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lay_member_list_item, null);
            holder = new ViewHolder();


            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.mem_list_name = (TextView) convertView.findViewById(R.id.mem_list_name);
            holder.mem_list_date = (TextView) convertView.findViewById(R.id.mem_list_date);
            holder.mem_list_city = (TextView) convertView.findViewById(R.id.mem_list_city);
            holder.men_list_price = (TextView) convertView.findViewById(R.id.men_list_price);

            convertView.setTag(holder);


        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        String userImage = smulist.get(position).UserImage;
        String userName = smulist.get(position).UserName;
        String CreateTime = smulist.get(position).CreateTime;
        String cityName = smulist.get(position).CityName;
        //// 剩余免费次数
        float cash = smulist.get(position).Cash;
//赋值
        //头像
        if (userImage == null || "".equals(userImage)) {

            holder.img.setImageBitmap(BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.empty_photo));
        } else {
            Application.mBU_Head.display(holder.img, userImage);
        }
        //名字
        if (userName != null || !"".equals(userName)) {
            holder.mem_list_name.setText(userName);
        }
//时间
        holder.mem_list_date.setText(CreateTime);
        holder.mem_list_city.setText(cityName);
        holder.men_list_price.setText(String.format("%.1f元30次",cash));





        return convertView;
    }

    public void setNewListInfo(ArrayList<SMUserReward> smulist) {
        this.smulist=smulist;
    }

    class ViewHolder {
        private ImageView img;
        private TextView mem_list_name;
        private TextView mem_list_date;
        private TextView mem_list_city;
        private TextView mem_list_super;
        private TextView men_list_price;
    }


}
