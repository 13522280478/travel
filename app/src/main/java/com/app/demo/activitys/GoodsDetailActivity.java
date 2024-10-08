package com.app.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.demo.beans.GoodsBean;
import com.app.demo.beans.OrdersBean;
import com.app.demo.beans.SceneryBean;
import com.app.demo.utils.DateUtil;
import com.app.utils.ToastUtil;
import com.app.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_gouwu)
    TextView tv_gouwu;

    GoodsBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);

        tv_title.setText("详情信息");
        tv_add.setVisibility(View.GONE);

        bean = (GoodsBean) getIntent().getSerializableExtra("bean");
        imv_pic.setImageResource(bean.getGoods_pic());
        tv_name.setText(bean.getGoods_name());
        tv_stype.setText(bean.getGoods_type() + "/" + bean.getGoods_price() + "元");
        tv_content.setText(bean.remark);


        if (UserManager.getUserType(GoodsDetailActivity.this) == 0) {
            tv_gouwu.setVisibility(View.VISIBLE);
        } else {
            tv_gouwu.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.imgv_return, R.id.tv_gouwu})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_gouwu:
                new ZhifuDialog(GoodsDetailActivity.this, bean.getGoods_price()).showDialog();
                break;
        }
    }

    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.REFRESH2) {
            comfirm(msg.mObject + "", msg.mObject2 + "");
        }
    }

    private void comfirm(String zhifu, String dizhi) {


        OrdersBean ordersBean = new OrdersBean();

        ordersBean.mTime = DateUtil.getTodayData_3();
        ordersBean.user_id = UserManager.getUserId(GoodsDetailActivity.this);
        ordersBean.user_name = UserManager.getUserName(GoodsDetailActivity.this);

        ordersBean.zhifu = zhifu;
        ordersBean.orderRemark = dizhi;

        ordersBean.setGoods_id(bean.getGoods_id());
        ordersBean.setGoods_price(bean.getGoods_price());
        ordersBean.setGoods_name(bean.getGoods_name());
        ordersBean.setGoods_pic(bean.getGoods_pic());
        ordersBean.remark = bean.remark;
        ordersBean.setGoods_type(bean.getGoods_type());

        ordersBean.save();

        ToastUtil.showToast(this, "支付成功");
        onBackPressed();
    }


}
