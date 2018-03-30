package com.niudanht.admin;

import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niudanht.adapter.Member_adapter;
import com.niudanht.adapter.Merchant_Adapter;
import com.niudanht.http.CFHttpClient;
import com.niudanht.http.CFHttpMsg;

import java.util.ArrayList;
import java.util.Map;

import ktx.pojo.domain.SMCustomerReward;
import ktx.pojo.domain.SMUserReward;
import niudanht.BaseActivity;
import niudanht.Consts;
import order.listview.XListView;

/*
会员列表
 */
@ContentView(R.layout.activity_member_list)
public class MemberListActivity extends BaseActivity implements CFHttpMsg, XListView.IXListViewListener {

    //商户列表
    @ViewInject(R.id.list_order)
    private XListView list_order;

    private int CityId = -1;
    private String CityName = "全国";

    // 记录信息条数
    private int order_num = 0;
    // 记录拖拉状态
    private int tl_status = XListView.REFRESH;
    // 检索控制
    private boolean tf = true;
    //会员列表数据
    private ArrayList<SMUserReward>smulist=new ArrayList<>();
    private Member_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewUtils.inject(this);
        super.onCreate(savedInstanceState);
        CityId = Consts.user.CityId;
        CityName = Consts.user.CityName;
        init();
    }
    // 标题栏的点击事件
    @OnClick({R.id.btn_back})
    public void viewonclick(View v) {
        switch (v.getId()) {
            //
            case R.id.btn_back:
                finish();
                break;

            default:
                break;
        }
    }

    private void init() {
        // 订单listview
        list_order.setPullLoadEnable(true);
        list_order.setPullRefreshEnable(true);
        //刷新的点击事件
        list_order.setXListViewListener(this);
        list_order.mHeaderView.setHeader(60);

        list_order.mFooterView.setTextNull("还没有记录~");
        list_order.mFooterView.setTextEnd("已是最后的记录了~");

        //绑定适配器
        adapter = new Member_adapter(this, smulist);
        list_order.setAdapter(adapter);
    }



    // 获取订单list
    private void getOrderList() {

        mProgressDialog.setTitle("加载中");
        mProgressDialog.show();
        Message m = new Message();
        m.what = 9022;
        CFHttpClient.s().get(
                "?MsgType=9022&mobileType=android&UserId=" + Consts.user.id
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
            ArrayList<SMUserReward> result = (ArrayList<SMUserReward>) map
                    .get("datalist");
            if (adapter == null) {
                adapter = new Member_adapter(this,smulist);
                list_order.setAdapter(adapter);
            }
            if (tl_status == XListView.REFRESH) {
                smulist.clear();
                smulist.addAll(result);
            } else {
                smulist.addAll(result);
            }
            adapter.setNewListInfo(smulist);
            adapter.notifyDataSetChanged();
            order_num = smulist.size();
            list_order.stopRefresh();
            list_order.stopLoadMore();
            list_order.setResultSize(result.size(), smulist.size());
        } else {
            Toast.makeText(MemberListActivity.this, "检索失败~", Toast.LENGTH_SHORT)
                    .show();
            list_order.stopRefresh();
            list_order.stopLoadMore();
        }
    }

}
