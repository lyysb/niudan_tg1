package com.niudanht.admin;


import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niudanht.adapter.Member_adapter;
import com.niudanht.http.CFHttpClient;
import com.niudanht.http.CFHttpMsg;
import com.niudanht.util.SMStatistics;
import com.niudanht.util.ToastUtil;

import java.util.ArrayList;
import java.util.Map;

import ktx.pojo.domain.StatisticsInfo;
import niudanht.BaseActivity;
import niudanht.Consts;
import order.listview.XListView;

@ContentView(R.layout.activity_member)
public class MemberActivity extends BaseActivity implements CFHttpMsg{


    @ViewInject(R.id.btn_city)
    private TextView btn_city;


    //两日会员数量id
    @ViewInject(R.id.member_list)
    private TextView member_list;
    @ViewInject(R.id.online_text1)
    private TextView online_text1;
    @ViewInject(R.id.online_text2)
    private TextView online_text2;
    @ViewInject(R.id.online_text3)
    private TextView online_text3;
    @ViewInject(R.id.online_text4)
    private TextView online_text4;

    //两日线下会员数量id
    @ViewInject(R.id.cus_list)
    private TextView cus_list;
    @ViewInject(R.id.line_text1)
    private TextView line_text1;
    @ViewInject(R.id.line_text2)
    private TextView line_text2;
    @ViewInject(R.id.online_text3)
    private TextView line_text3;
    @ViewInject(R.id.line_text4)
    private TextView line_text4;

    //会员统计数id
    @ViewInject(R.id.member_number1)
    private TextView member_number1;
    @ViewInject(R.id.member_number2)
    private TextView member_number2;
    @ViewInject(R.id.member_number3)
    private TextView member_number3;
    @ViewInject(R.id.member_number4)
    private TextView member_number4;

    //扭蛋统计数id
    @ViewInject(R.id.nd_number1)
    private TextView nd_number1;
    @ViewInject(R.id.nd_number2)
    private TextView nd_number2;
    @ViewInject(R.id.nd_number3)
    private TextView nd_number3;
    //商户统计数id
    @ViewInject(R.id.cus_number1)
    private TextView cus_number1;
    @ViewInject(R.id.cus_number2)
    private TextView cus_number2;

    //
    private Intent intent;
    //
    private SMStatistics smStatistics= new SMStatistics();

    private int CityId = -1;
    private String CityName = "全国";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ViewUtils.inject(this);
        super.onCreate(savedInstanceState);
        CityId = Consts.user.CityId;
        CityName = Consts.user.CityName;
        getData();
       member_list.setOnClickListener(
               new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          startActivity(new Intent(MemberActivity.this,MemberListActivity.class));
      }

    });
        member_list.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MemberActivity.this,MemberListActivity.class));
                    }

                });
        cus_list.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MemberActivity.this,MerchantActivity.class));
                    }

                });


    }


    private void init() {

        btn_city.setText(CityName);
        intent = new Intent();
//	text_1.setText(String.format("今日 %d 单", statisticsInfo.ATOrderNum));


        online_text3.setText(String.format("%d", smStatistics.TodayMemberNum));
        online_text4.setText(String.format("%d", smStatistics.YesterdayMemberNum));
        line_text3.setText(String.format("%d", smStatistics.TodayCustomerNum));
        line_text4.setText(String.format("%d", smStatistics.YesterdayCustomerNum));

        member_number1.setText(String.format("%d", smStatistics.AllMemberNum));
        member_number2.setText(String.format("%d", smStatistics.NowMemberNum));
        member_number3.setText(String.format("%d", smStatistics.SurplusMemberNum));
        member_number4.setText(String.format("%.1f", smStatistics.NowMemberCash));

        nd_number1.setText(String.format("%d", smStatistics.NowEggNum));
        nd_number2.setText(String.format("%d", smStatistics.NowOutEggNum));
        nd_number3.setText(String.format("%d", smStatistics.NowSurplusEggNum));


        cus_number1.setText(String.format("%d", smStatistics.AllCustomerNum));
        cus_number2.setText(String.format("%.1f", smStatistics.AllCustomerCash));

        if (Consts.user.Type1 == 3 || Consts.user.Type1 == 5) {
            btn_city.setEnabled(true);
        } else {
            btn_city.setEnabled(false);
        }
    }

    // 标题栏的点击事件
    @OnClick({R.id.btn_back, R.id.btn_city, R.id.member_list, R.id.cus_list})
    public void viewonclick(View v) {
        switch (v.getId()) {
            //
            case R.id.btn_back:
                finish();
                break;
            //
            case R.id.btn_city:
                intent.setClass(MemberActivity.this, ProvinceListActivity.class);
                startActivity(intent);
                break;
      /*      case R.id.member_list:
                intent.setClass(MemberActivity.this, MemberListActivity.class);
                startActivity(intent);

                break;*/
            case R.id.cus_list:
                intent.setClass(MemberActivity.this, MerchantActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void getData() {
        mProgressDialog.show();
        Message message = new Message();
        message.what = 9020;
        CFHttpClient.s().get(
                "?MsgType=9020&mobileType=android&UserId=" + Consts.user.id
                        + "&CityId=" + CityId, this, message, false);
    }


    //实现网络里的接口
    @Override
    public void httpMsg(Message m) {
        mProgressDialog.cancel();
        if (m.arg1 == 1) {
            Map<String, Object> map = (Map<String, Object>) m.obj;
            smStatistics = (SMStatistics) map.get("smstatistics");
            init();
        } else {
            ToastUtil.showMessages(MemberActivity.this, "获取信息失败~");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Consts.region_tf) {
            Consts.region_tf = false;
            CityName = Consts.Cityinfo.Name;
            CityId = Consts.Cityinfo.id;
            if (CityName != null && !CityName.equals("")) {
                btn_city.setText(CityName);
                getData();
            }
        }
    }


}
