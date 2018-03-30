package com.niudanht.admin;

import java.net.URLEncoder;
import ktx.pojo.domain.EquipmentInfo;
import niudanht.BaseActivity;
import niudanht.Consts;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niudanht.http.CFHttpClient;
import com.niudanht.http.CFHttpMsg;
import com.niudanht.util.ToastUtil;
import com.niudanht.util.Utils;
import com.niudanht.admin.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@ContentView(R.layout.layout_add_car)
public class AddCarActivity extends BaseActivity implements CFHttpMsg {

	// 返回按钮
	@ViewInject(R.id.btn_back)
	private ImageView btn_back;
	//
	@ViewInject(R.id.ed_code)
	private EditText ed_code;
	//
	@ViewInject(R.id.ed_price)
	private EditText ed_price;
	//
	@ViewInject(R.id.ed_proportion)
	private EditText ed_proportion;
	//
	@ViewInject(R.id.btn_region)
	private RelativeLayout btn_region;
	//
	@ViewInject(R.id.t_region)
	private TextView t_region;
	//
	@ViewInject(R.id.ed_address)
	private EditText ed_address;

	//
	@ViewInject(R.id.btn_default_address)
	private RelativeLayout btn_default_address;
	//
	@ViewInject(R.id.img_default_address)
	private ImageView img_default_address;
	//
	@ViewInject(R.id.btn_up)
	private RelativeLayout btn_up;

	//
	private String Name;
	private String DeviceID;
	private String Region;
	private String Address;
	private String Proportion;
	//
	private String Price;
	//
	private EquipmentInfo eqinfo;
	//
	private Intent intent;

	protected void onCreate(Bundle savedInstanceState) {
		ViewUtils.inject(this);
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		if (Consts.customer.id == 0) {
			ToastUtil.showMessages(AddCarActivity.this, "未获取到用户信息，请重新登录");
			finish();
		}
		intent = new Intent();
		Consts.addcar_tf = false;
		DeviceID = getIntent().getStringExtra("DeviceID");

		// if (Consts.customer.Address == null
		// || Consts.customer.Address.equals("")) {
		btn_default_address.setVisibility(View.GONE);
		// }
		ed_price.setText("8.8");
		ed_proportion.setText("1");
		setAddress();
	}

	// 下方控制栏的点击事件
	@OnClick({ R.id.btn_back, R.id.btn_up, R.id.btn_region,
			R.id.btn_default_address })
	public void viewonclick(View v) {
		switch (v.getId()) {
		//
		case R.id.btn_back:
			finish();
			break;
		//
		case R.id.btn_up:
			setData();
			break;
		//
		case R.id.btn_region:
			intent.setClass(AddCarActivity.this, ProvinceListActivity.class);
			startActivity(intent);
			break;
		//
		case R.id.btn_default_address:
			setAddress();
			break;
		default:
			break;
		}
	}

	private void setAddress() {
		if (eqinfo == null) {
			eqinfo = new EquipmentInfo();
		}
		Consts.Provinceinfo.id = Consts.customer.ProvinceId;
		Consts.Provinceinfo.Name = Consts.customer.ProvinceName;
		Consts.Cityinfo.id = Consts.customer.CityId;
		Consts.Cityinfo.Name = Consts.customer.CityName;
		Consts.Regioninfo.id = Consts.customer.RegionId;
		Consts.Regioninfo.Name = Consts.customer.RegionName;
		Address = Consts.customer.Address;
		Region = String.format("%s %s %s", Consts.Provinceinfo.Name,
				Consts.Cityinfo.Name, Consts.Regioninfo.Name);
		if (Region != null && !Region.equals("")) {
			t_region.setText(Region);
		}
		if (Address != null && !Address.equals("")) {
			ed_address.setText(Address);
		}

	}

	private void setData() {
		DeviceID = ed_code.getText().toString();
		Price = ed_price.getText().toString();
		Proportion = ed_proportion.getText().toString();
		Address = ed_address.getText().toString();
		//
		DeviceID = Utils.setSrule(DeviceID);
		Price = Utils.setSrule(Price);
		Proportion = Utils.setSrule(Proportion);
		Address = Utils.setSrule(Address);
		if (DeviceID == null || DeviceID.equals("")) {
			ToastUtil.showMessages(AddCarActivity.this, "设备编号不能为空~");
			return;
		}
		Name = String.format("扭蛋机%s", DeviceID);
		if (Price == null || Price.equals("")) {
			ToastUtil.showMessages(AddCarActivity.this, "价格不能为空~");
			return;
		}
		float f_price = Float.valueOf(Price);

		if (Proportion == null || Proportion.equals("")) {
			ToastUtil.showMessages(AddCarActivity.this, "分成金额不能为空~");
			return;
		}
		float f_proportion = Float.valueOf(Proportion);
		if (f_proportion >= 50) {
			ToastUtil.showMessages(AddCarActivity.this, "您输入的分成比超出设置区间。");
			return;
		}
		if (Region == null || Region.equals("")) {
			ToastUtil.showMessages(AddCarActivity.this, "安装地区不能为空~");
			return;
		}
		if (Address == null || Address.equals("")) {
			ToastUtil.showMessages(AddCarActivity.this, "详细地址不能为空~");
			return;
		}
		if (eqinfo == null) {
			eqinfo = new EquipmentInfo();
		}
		eqinfo.id = 0;
		eqinfo.DeviceID = DeviceID;
		eqinfo.Name = Name;
		eqinfo.Status = 0;
		eqinfo.Price = f_price;
		eqinfo.CustomerId = Consts.customer.id;
		eqinfo.ProvinceId = Consts.Provinceinfo.id;
		eqinfo.ProvinceName = Consts.Provinceinfo.Name;
		eqinfo.CityId = Consts.Cityinfo.id;
		eqinfo.CityName = Consts.Cityinfo.Name;
		eqinfo.RegionId = Consts.Regioninfo.id;
		eqinfo.RegionName = Consts.Regioninfo.Name;
		eqinfo.Address = Address;
		eqinfo.UnSettledCash = f_proportion;

		setAddCar();
	}

	// 添加设备
	private void setAddCar() {
		mProgressDialog.show();
		mProgressDialog.setTitle("提交中...");
		String str = URLEncoder.encode(JSON.toJSONString(eqinfo));
		Message m = new Message();
		m.what = 9006;
		CFHttpClient.s().get(
				"?MsgType=9006&mobileType=android&eqinfo=" + str + "&EId="
						+ Consts.user.id, this, m, true);

	}

	public void httpMsg(Message m) {
		mProgressDialog.cancel();
		if (m.arg1 == 1) {
			ToastUtil.showMessages(AddCarActivity.this, "增加设备成功~");
			Consts.addcar_tf = true;
			finish();
		} else if (m.arg1 == -2) {
			ToastUtil.showMessages(AddCarActivity.this, "此设备已被绑定，无法重复绑定.");
		} else if (m.arg1 == -3) {
			ToastUtil.showMessages(AddCarActivity.this, "此设备不存在");
		} else if (m.arg1 == -4) {
			ToastUtil.showMessages(AddCarActivity.this, "此设备已被其他商户绑定，请先解绑。");
		} else {
			ToastUtil.showMessages(AddCarActivity.this, "增加设备失败,请重试~");
		}
	}

	protected void onResume() {
		super.onResume();
		if (Consts.region_tf) {
			Consts.region_tf = false;
			Region = String.format("%s %s %s", Consts.Provinceinfo.Name,
					Consts.Cityinfo.Name, Consts.Regioninfo.Name);
			if (Region != null && !Region.equals("")) {
				t_region.setText(Region);
			}
		}
	}
}
