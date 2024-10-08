package com.app.demo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.base.CommonAdapter;
import com.app.base.ViewHolder;
import com.app.demo.R;
import com.app.demo.beans.OrdersBean;
import com.app.utils.UserManager;
import com.app.widgts.ScrollListView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderListActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.lv)
    ScrollListView lv;

    private MyAdapter adapter;
    List<OrdersBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        tv_title.setText("周边订单");

        List<OrdersBean> tmep = DataSupport.findAll(OrdersBean.class);
        for (int i = 0; i < tmep.size(); i++) {

            if (UserManager.getUserType(OrderListActivity.this) == 0) {
                if (tmep.get(i).user_id.equals(UserManager.getUserId(OrderListActivity.this))) {
                    list.add(tmep.get(i));
                }
            } else {
                list.add(tmep.get(i));
            }
        }
        adapter = new MyAdapter(OrderListActivity.this, (ArrayList) list, R.layout.item_order);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                intent.putExtra("bean", list.get(i));
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends CommonAdapter {
        public MyAdapter(Context context, ArrayList datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(ViewHolder holder, Object o, int position) {
            OrdersBean bean = (OrdersBean) o;
            SimpleDraweeView simpleDraweeView = holder.getView(R.id.imgv_list);
            TextView tv = holder.getView(R.id.tv_list_item);
            TextView tv_price = holder.getView(R.id.tv_price);
            TextView tv_stype = holder.getView(R.id.tv_stype);

            tv.setText(bean.getGoods_name());
            tv_stype.setText("下单用户：" + bean.user_name + "\n下单时间：" + bean.mTime);
            tv_price.setText("¥  " + bean.getGoods_price() + "");

            simpleDraweeView.setActualImageResource(bean.getGoods_pic());


        }
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
