package com.app.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.demo.beans.GoodsBean;
import com.app.demo.beans.OrdersBean;
import com.app.demo.utils.DateUtil;
import com.app.utils.ToastUtil;
import com.app.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_stype)
    TextView tv_stype;

    @BindView(R.id.tv_content1)
    TextView tv_content1;
    @BindView(R.id.tv_content2)
    TextView tv_content2;
    @BindView(R.id.tv_content3)
    TextView tv_content3;

    OrdersBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        tv_title.setText("订单详情");
        tv_add.setVisibility(View.GONE);

        bean = (OrdersBean) getIntent().getSerializableExtra("bean");
        imv_pic.setImageResource(bean.getGoods_pic());
        tv_name.setText(bean.getGoods_name());
        tv_stype.setText(bean.getGoods_type() + "/" + bean.getGoods_price() + "元");
        tv_content.setText(bean.remark);
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
