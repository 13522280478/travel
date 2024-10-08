package com.app.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.demo.beans.OrderSceneryBean;
import com.app.demo.beans.SceneryBean;
import com.app.demo.utils.DateUtil;
import com.app.utils.ToastUtil;
import com.app.utils.UserManager;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 景点详情
 */
public class SceneryDetailActivity extends BaseActivity {
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

    @BindView(R.id.tv_gouwu)
    TextView tv_gouwu;

    SceneryBean bean;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenery_detail);
        ButterKnife.bind(this);

        tv_title.setText("详情信息");

        bean = (SceneryBean) getIntent().getSerializableExtra("bean");
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
        if (UserManager.getUserType(SceneryDetailActivity.this) == 1) {
            tv_gouwu.setVisibility(View.GONE);
        } else {
            tv_gouwu.setVisibility(View.VISIBLE);

        }





    }



    @OnClick({R.id.imgv_return, R.id.tv_add, R.id.tv_gouwu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                Intent intent = new Intent(SceneryDetailActivity.this, SceneryAddActivity.class);
                intent.putExtra("bean", bean);
                startActivity(intent);
                finish();
                break;
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_gouwu:
                new ZhifuDialog(SceneryDetailActivity.this, bean.price).showDialog();
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

        OrderSceneryBean ordersBean = new OrderSceneryBean();

        ordersBean.mTime = DateUtil.getTodayData_3();
        ordersBean.user_id = UserManager.getUserId(SceneryDetailActivity.this);
        ordersBean.user_name = UserManager.getUserName(SceneryDetailActivity.this);

        ordersBean.zhifu = zhifu;
        ordersBean.orderRemark = dizhi;

        ordersBean.ids = (bean.ids);
        ordersBean.name = (bean.name);
        ordersBean.pic = (bean.pic);
        ordersBean.content = (bean.content);
        ordersBean.like = bean.like;
        ordersBean.num = bean.num;
        ordersBean.price = bean.price;


        ordersBean.save();

        ToastUtil.showToast(this, "支付成功");
        onBackPressed();
    }

}
