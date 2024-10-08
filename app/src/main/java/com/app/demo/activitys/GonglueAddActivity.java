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
import com.app.demo.beans.GongluoBean;
import com.app.demo.beans.GongluoBean;
import com.app.demo.utils.StrUtil;
import com.app.interfaces.I_itemSelectedListener;
import com.app.utils.ItemChooseUtil;
import com.app.utils.ToastUtil;
import com.app.utils.UserManager;

import org.litepal.crud.DataSupport;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class GonglueAddActivity extends BaseActivity {

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
    @BindView(R.id.edt_num)
    EditText edt_num;
    @BindView(R.id.edt_like)
    EditText edt_like;
    @BindView(R.id.edt_price)
    EditText edt_price;

    private int pic;
    GongluoBean bean;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gonglue);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initData();
    }

    private void initData() {
        bean = (GongluoBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            tvTitle.setText("编辑信息");
            edtGoodsName.setText(bean.name);
            edtDescribe.setText(bean.content);
            pic = bean.pic;
            imgv.setImageResource(pic);
            tv_del.setVisibility(View.VISIBLE);
            edt_like.setText(bean.like);edt_num.setText(bean.num+"");edt_price.setText(bean.price+"");
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
                || StrUtil.isEmpty(edt_num.getText().toString())
                || StrUtil.isEmpty(edt_price.getText().toString())

        ) {
            ToastUtil.showToast(this, "请完善信息");
            return;
        }
        if (bean == null) {
            GongluoBean bean = new GongluoBean();
            bean.ids=(System.currentTimeMillis() + "");
            bean.name=(name);
            bean.pic=(pic);
            bean.user_id=(UserManager.getUserId(this));
            bean.content=(remark);
            bean.like=edt_like.getText().toString();
            bean.num= Integer.parseInt(edt_num.getText().toString());
            bean.price= Double.parseDouble(edt_price.getText().toString());


            bean.save();
            ToastUtil.showToast(this, "添加成功");
        } else {
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("pic", pic);
            values.put("content", remark);
            values.put("like", edt_like.getText().toString());
            values.put("num", Integer.parseInt(edt_num.getText().toString()));
            values.put("price", Double.parseDouble(edt_price.getText().toString()));

            DataSupport.updateAll(GongluoBean.class, values, "ids=?", bean.ids);
            ToastUtil.showToast(this, "编辑成功");

        }
        EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH6));
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

    @OnClick({R.id.imgv_return, R.id.imgv,   R.id.tv_ok,R.id.tv_del })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.imgv:
                Intent intent=new Intent(GonglueAddActivity.this,ChoicePicActivity.class);
                intent.putExtra("name","gl");
                startActivity(intent);
                break;

            case R.id.tv_ok:
                save();
                break;
            case R.id.tv_del:
                //从数据库删除
                DataSupport.deleteAll(GongluoBean.class, "ids=?", bean.ids);
                EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH6));
                onBackPressed();
                break;

        }
    }

}
