package com.niudanht.admin;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niudanht.adapter.Merchant_Adapter;
import com.niudanht.adapter.Order_Adapter;
import com.niudanht.http.CFHttpClient;
import com.niudanht.http.CFHttpMsg;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import ktx.pojo.domain.FoundPayment;
import ktx.pojo.domain.OrderInfo3;
import ktx.pojo.domain.SMCustomerReward;
import niudanht.BaseActivity;
import niudanht.Consts;
import order.listview.XListView;

/*
商户列表
 */
@ContentView(R.layout.activity_merchant)
public class MerchantActivity extends BaseActivity implements CFHttpMsg, XListView.IXListViewListener {

    // 返回按钮
    @ViewInject(R.id.btn_back)
    private ImageView btn_back;
    //商户列表
    @ViewInject(R.id.merchant_list)
    private XListView merchant_list;
//search_btn
@ViewInject(R.id.btn_search)
private XListView btn_search;
    private int CityId = -1;
    private String CityName = "全国";

    // 记录信息条数
    private int order_num = 0;
    // 记录拖拉状态
    private int tl_status = XListView.REFRESH;
    // 检索控制
    private boolean tf = true;
    //商户列表数据
    private ArrayList<SMCustomerReward>smCustomerRewards=new ArrayList<>();
    private Merchant_Adapter adapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ViewUtils.inject(this);
        super.onCreate(savedInstanceState);
        intent = new Intent();
        CityId = Consts.user.CityId;
        CityName = Consts.user.CityName;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
    }
    private void init() {
        // 订单listview
        merchant_list.setPullLoadEnable(true);
        merchant_list.setPullRefreshEnable(true);
        //刷新的点击事件
        merchant_list.setXListViewListener(this);
        merchant_list.mHeaderView.setHeader(60);

        merchant_list.mFooterView.setTextNull("还没有记录~");
        merchant_list.mFooterView.setTextEnd("已是最后的记录了~");

        //绑定适配器
        adapter = new Merchant_Adapter(this,smCustomerRewards);
        merchant_list.setAdapter(adapter);

    }

    // 标题栏的点击事件
    @OnClick({R.id.btn_back,R.id.btn_search})
    public void viewonclick(View v) {
        switch (v.getId()) {
            //
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                intent.setClass(MerchantActivity.this, CustomQureActivity.class);
                startActivity(intent);
                break;


            default:
                break;
        }
    }
    // 获取订单list
    private void getOrderList() {

        mProgressDialog.setTitle("加载中");
        mProgressDialog.show();
        Message m = new Message();
        m.what = 9021;
        CFHttpClient.s().get(
                "?MsgType=9021&mobileType=android&UserId=" + Consts.user.id
                        + "&CityId=" + CityId+"&Index="+order_num, this, m, false);

    }
    private void loadData(int what) {
        tl_status = what;
        getOrderList();
    }

    @Override
    public void onRefresh() {
        if (tf) {
            order_num = 0;
            loadData(XListView.REFRESH);
            tf = false;
        }
    }



    @Override
    public void onLoadMore() {
        if (tf) {
            loadData(XListView.LOAD);
            tf = false;
        }
    }

    @Override
    public void httpMsg(Message m) {
        mProgressDialog.cancel();
        tf = true;
        if (m.arg1 == 1) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) m.obj;
            @SuppressWarnings("unchecked")
            ArrayList<SMCustomerReward> result = (ArrayList<SMCustomerReward>) map
                    .get("datalist");
            if (adapter == null) {
                adapter = new Merchant_Adapter(this,smCustomerRewards);
                merchant_list.setAdapter(adapter);
            }
            if (tl_status == XListView.REFRESH) {
                smCustomerRewards.clear();
                smCustomerRewards.addAll(result);
            } else {
                smCustomerRewards.addAll(result);
            }
            adapter.setNewListInfo(smCustomerRewards);
            adapter.notifyDataSetChanged();
            order_num = smCustomerRewards.size();
            merchant_list.stopRefresh();
            merchant_list.stopLoadMore();
            merchant_list.setResultSize(result.size(), smCustomerRewards.size());
        } else {
            Toast.makeText(MerchantActivity.this, "检索失败~", Toast.LENGTH_SHORT)
                    .show();
            merchant_list.stopRefresh();
            merchant_list.stopLoadMore();
        }
    }
}
