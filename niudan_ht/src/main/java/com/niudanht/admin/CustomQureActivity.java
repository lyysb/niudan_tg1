package com.niudanht.admin;

import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niudanht.adapter.CustomerType_Adapter;
import com.niudanht.http.CFHttpClient;
import com.niudanht.http.CFHttpMsg;
import com.niudanht.util.SMStatistics;
import com.niudanht.util.ToastUtil;

import java.util.ArrayList;
import java.util.Map;

import ktx.pojo.domain.CustomerType;
import ktx.pojo.domain.SMCustomerReward;
import niudanht.BaseActivity;
import niudanht.Consts;

@ContentView(R.layout.activity_custom_qure)
public class CustomQureActivity extends BaseActivity implements CFHttpMsg {
    // 返回按钮
    @ViewInject(R.id.btn_back)
    private ImageView btn_back;
    //sou_phone
    @ViewInject(R.id.sou_phone)
    private EditText sou_phone;


    @ViewInject(R.id.search_btn)
    private ImageView search_btn;
    private SMCustomerReward data= new SMCustomerReward();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewUtils.inject(this);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        getData();
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
        sou_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void getData() {
        mProgressDialog.show();
        Message message = new Message();
        message.what = 9023;
        CFHttpClient.s().get(
                "?MsgType=9023&mobileType=android&UserId=" + Consts.user.id
                        + "&Phone=" + Consts.user.Phone, this, message, false);
    }
    @Override
    public void httpMsg(Message m) {
        mProgressDialog.cancel();
        if (m.arg1 == 1) {
            Map<String, Object> map = (Map<String, Object>) m.obj;
            data = (SMCustomerReward) map.get("data");
            init();
        } else {
            ToastUtil.showMessages(CustomQureActivity.this, "获取信息失败~");
        }

    }

}
