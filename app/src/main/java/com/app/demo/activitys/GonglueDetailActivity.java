package com.app.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.demo.beans.GongluoBean;
import com.app.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GonglueDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.imv_pic)
    ImageView imv_pic;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_content2)
    TextView tv_content2;
    @BindView(R.id.tv_content3)
    TextView tv_content3;




    GongluoBean bean;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonglue_detail);
        ButterKnife.bind(this);

        tv_title.setText("详情信息");


        bean = (GongluoBean) getIntent().getSerializableExtra("bean");
        imv_pic.setImageResource(bean.pic);
        tv_name.setText(bean.name);
        tv_content.setText(bean.content);
        tv_content2.setText(bean.num + "人/" + bean.price + "元");
        tv_content3.setText(bean.like);


        user_id = UserManager.getUserId(this);
        if (bean.user_id.equals(user_id)) {
            tv_add.setVisibility(View.VISIBLE);
        } else {
            tv_add.setVisibility(View.GONE);
        }






    }



    @OnClick({R.id.imgv_return, R.id.tv_add })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                Intent intent = new Intent(GonglueDetailActivity.this, GonglueAddActivity.class);
                intent.putExtra("bean", bean);
                startActivity(intent);
                finish();
                break;
            case R.id.imgv_return:
                onBackPressed();
                break;

        }
    }
    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.REFRESH2) {
         }
    }



}
