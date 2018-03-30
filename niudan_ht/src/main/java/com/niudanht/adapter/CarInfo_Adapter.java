package com.niudanht.adapter;

import java.util.ArrayList;
import niudanht.Consts;
import com.niudanht.admin.R;
import ktx.pojo.domain.EquipmentInfo;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarInfo_Adapter extends BaseAdapter implements OnClickListener {
	//
	private ArrayList<EquipmentInfo> datalist;
	private Context mContext;
	private Handler handler;

	public CarInfo_Adapter(Context mContext, ArrayList<EquipmentInfo> mqlist,
			Handler handler) {
		this.mContext = mContext;
		this.datalist = mqlist;
		this.handler = handler;
	}

	public void setNewListInfo(ArrayList<EquipmentInfo> mqlist) {
		this.datalist = mqlist;
	}

	@Override
	public int getCount() {
		return datalist.size();
	}

	@Override
	public EquipmentInfo getItem(int position) {
		return datalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;

		// 名称
		int id = datalist.get(position).id;
		String DeviceID = datalist.get(position).DeviceID;
		String CarName = datalist.get(position).Name;
		float Price = datalist.get(position).Price;
		String Ctime = datalist.get(position).CreateTime;
		String MallName = datalist.get(position).MallName;
		float SettledCash = datalist.get(position).SettledCash;
		float UnSettledCash = datalist.get(position).UnSettledCash;
		float Decibels = datalist.get(position).Decibels;
		//
		int Status = datalist.get(position).Status;

		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.lay_car_item, null);
			holder = new Holder();

			holder.lay_1 = (LinearLayout) convertView.findViewById(R.id.lay_1);
			holder.t_name = (TextView) convertView.findViewById(R.id.t_name);
			holder.t_status = (TextView) convertView
					.findViewById(R.id.t_status);
			holder.t_ctime = (TextView) convertView.findViewById(R.id.t_ctime);
			holder.t_price = (TextView) convertView.findViewById(R.id.t_price);
			holder.t_id = (TextView) convertView.findViewById(R.id.t_id);
			holder.t_todaynum = (TextView) convertView
					.findViewById(R.id.t_todaynum);
			holder.t_monuthnum = (TextView) convertView
					.findViewById(R.id.t_monuthnum);
			holder.t_monuthnum2 = (TextView) convertView
					.findViewById(R.id.t_monuthnum2);
			holder.btn_jb = (Button) convertView.findViewById(R.id.btn_jb);
			holder.btn_updateprice = (Button) convertView
					.findViewById(R.id.btn_updateprice);
			holder.lay_btn = (LinearLayout) convertView
					.findViewById(R.id.lay_btn);

			holder.btn_jb.setOnClickListener(this);
			holder.btn_updateprice.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.lay_btn.setVisibility(View.GONE);

		//
		holder.btn_jb.setTag(R.id.btn_jb, position);
		holder.btn_updateprice.setTag(R.id.btn_updateprice, position);

		//
		if (CarName != null || !"".equals(CarName)) {
			holder.t_name.setText(CarName);
		}
		holder.t_price.setText(String.format("%.1f元/次", Price));
		if (Ctime != null || !"".equals(Ctime)) {
			holder.t_ctime.setText(Ctime);
		}
		if (MallName != null || !"".equals(MallName)) {
			holder.t_status.setText(MallName);
			if (Status == 0) {
				holder.t_status.setTextColor(mContext.getResources().getColor(
						R.color.green1));
			} else {
				holder.t_status.setTextColor(mContext.getResources().getColor(
						R.color.red));
			}

		}
		if (DeviceID != null || !"".equals(DeviceID)) {
			holder.t_id.setText(String.format("编号：%s-%d", DeviceID, id));
		}
		holder.t_todaynum.setText(String.format("今日：%.0f", SettledCash));
		holder.t_monuthnum.setText(String.format("本月：%.0f", UnSettledCash));
		holder.t_monuthnum2.setText(String.format("上月：%.0f", Decibels));
		return convertView;
	}

	class Holder {
		private LinearLayout lay_1;
		private TextView t_name;
		private TextView t_price;
		private TextView t_ctime;
		private TextView t_status;
		private TextView t_id;
		private TextView t_todaynum;
		private TextView t_monuthnum;
		private TextView t_monuthnum2;
		private Button btn_jb;
		private Button btn_updateprice;
		private LinearLayout lay_btn;
	}

	public void onClick(View v) {
		Message msg = Message.obtain();
		int position;
		switch (v.getId()) {
		// 解绑
		case R.id.btn_jb:
			position = (Integer) v.getTag(R.id.btn_jb);
			msg.what = 6;
			msg.arg1 = position;
			handler.sendMessage(msg);
			break;
		// 修改价格
		case R.id.btn_updateprice:
			position = (Integer) v.getTag(R.id.btn_updateprice);
			msg.what = 3;
			msg.arg1 = position;
			handler.sendMessage(msg);
			break;

		default:
			break;
		}

	}

}
