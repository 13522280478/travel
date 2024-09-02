package com.app.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.demo.R;
import com.app.demo.beans.OrderSceneryBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderSceneryDetailActivity extends BaseActivity {
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


    @BindView(R.id.tv_content1)
    TextView tv_content1;
    @BindView(R.id.tv_content2)
    TextView tv_content2;
    @BindView(R.id.tv_content3)
    TextView tv_content3;

    OrderSceneryBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_scenery_detail);
        ButterKnife.bind(this);

        tv_title.setText("订单详情");
        tv_add.setVisibility(View.GONE);

        bean = (OrderSceneryBean) getIntent().getSerializableExtra("bean");


        imv_pic.setImageResource(bean.pic);
        tv_name.setText(bean.name);
        tv_content.setText(bean.content);
        tv_content2.setText(bean.num + "人/" + bean.price + "元");
        tv_content3.setText(bean.like);


        tv_content1.setText("支付方式：" + bean.zhifu);
        tv_content2.setText("订单备注：" + bean.orderRemark);
        tv_content3.setText("下单时间：" + bean.mTime);
    }


    @OnClick({R.id.imgv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.imgv_return:
                onBackPressed();
                break;

        }
    }


}
