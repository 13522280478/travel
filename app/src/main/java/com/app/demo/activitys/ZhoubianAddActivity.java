package com.app.demo.activitys;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.app.base.BaseActivity;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.demo.beans.GoodsBean;
import com.app.demo.utils.StrUtil;
import com.app.interfaces.I_itemSelectedListener;
import com.app.utils.ItemChooseUtil;
import com.app.utils.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ZhoubianAddActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imgv)
    ImageView imgv;
    @BindView(R.id.edt_goods_name)
    EditText edtGoodsName;
    @BindView(R.id.edt_describ)
    EditText edtDescribe;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.tv_del)
    TextView tv_del;

    @BindView(R.id.edt_like)
    TextView edt_like;
    @BindView(R.id.edt_price)
    EditText edt_price;


    private int pic;
    GoodsBean bean;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zhoubian);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initData();
    }

    private void initData() {
        bean = (GoodsBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            tvTitle.setText("编辑信息");
            edtGoodsName.setText(bean.goods_name);
            edtDescribe.setText(bean.remark);
            pic = bean.goods_pic;
            imgv.setImageResource(pic);
            tv_del.setVisibility(View.VISIBLE);
            edt_like.setText(bean.goods_type);

            edt_price.setText(bean.goods_price + "");
        } else {
            tvTitle.setText("添加信息");
            tv_del.setVisibility(View.GONE);
        }
    }

    private void save() {

        String name = edtGoodsName.getText().toString();
        String remark = edtDescribe.getText().toString();
        if (StrUtil.isEmpty(name) || pic == 0 || StrUtil.isEmpty(remark)
                || StrUtil.isEmpty(edt_like.getText().toString())

                || StrUtil.isEmpty(edt_price.getText().toString())

        ) {
            ToastUtil.showToast(this, "请完善信息");
            return;
        }
        if (bean == null) {
            GoodsBean bean = new GoodsBean();
            bean.goods_id = (System.currentTimeMillis() + "");
            bean.goods_name = (name);
            bean.goods_pic = (pic);
//            bean.gi=(UserManager.getUserId(this));
            bean.remark = (edtDescribe.getText().toString());
            bean.goods_type = edt_like.getText().toString();
            bean.goods_price = Double.parseDouble(edt_price.getText().toString());


            bean.save();
            ToastUtil.showToast(this, "添加成功");
        } else {
            ContentValues values = new ContentValues();
            values.put("goods_name", name);
            values.put("goods_pic", pic);
            values.put("remark", edtDescribe.getText().toString());
            values.put("goods_type", edt_like.getText().toString());
             values.put("goods_price", Double.parseDouble(edt_price.getText().toString()));

            DataSupport.updateAll(GoodsBean.class, values, "goods_id=?", bean.goods_id);
            ToastUtil.showToast(this, "编辑成功");

        }
        EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH7));
        onBackPressed();
    }

    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.CHOICE_PIC) {
            pic = (int) msg.getmObject();
            imgv.setImageResource(pic);
        }
    }

    private String[] str_type = new String[]{"交通", "餐饮", "酒店", "其他"};

    @OnClick({R.id.imgv_return, R.id.imgv, R.id.edt_like, R.id.tv_ok, R.id.tv_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.edt_like:
                ItemChooseUtil.showItemWheel(this, Arrays.asList(str_type), "类型", 0, new I_itemSelectedListener() {
                    @Override
                    public void onItemSelected(int currentPosition) {
                        edt_like.setText(str_type[(currentPosition)]);
                    }
                });


                break;
            case R.id.imgv:
                Intent intent = new Intent(ZhoubianAddActivity.this, ChoicePicActivity.class);
                intent.putExtra("name", "zb");
                startActivity(intent);
                break;

            case R.id.tv_ok:
                save();
                break;
            case R.id.tv_del:
                //从数据库删除
                DataSupport.deleteAll(GoodsBean.class, "goods_id=?", bean.goods_id);
                EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH7));
                onBackPressed();
                break;

        }
    }


}
