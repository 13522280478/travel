package com.app.demo.activitys;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.app.base.BaseActivity;
import com.app.beans.EventMessage;
import com.app.beans.UserBean;
import com.app.demo.MyApplication;
import com.app.demo.R;
import com.app.interfaces.I_itemSelectedListener;
import com.app.utils.GlideUtils;
import com.app.utils.ItemChooseUtil;
import com.app.utils.StringUtils;
import com.app.utils.ToastUtil;
import com.app.utils.UserManager;
import com.app.widgts.LoadingDialog;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.engine.GlideEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.edt_phone)
    EditText edt_phone;

    UserBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        ButterKnife.bind(this);

        tvTitle.setText("个人资料");

        bean = UserManager.getUser(UserManager.getUserId(this));
        if (bean != null) {
            edtName.setText(bean.name);
            tv_num.setText(bean.user_id);
            edt_phone.setText(bean.mobile);
        }
    }

    private void save() {

        String name = edtName.getText().toString();
        String phone = edt_phone.getText().toString();
        if (StringUtils.isEmpty(name)  || StringUtils.isEmpty(phone)) {
            ToastUtil.showToast(this, "请完善信息");
            return;
        }

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("mobile", phone);

        DataSupport.updateAll(UserBean.class, values, "user_id=?", bean.user_id);
        ToastUtil.showToast(this, "修改成功");

        EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH));
        onBackPressed();
    }

    @OnClick({R.id.imgv_return,  R.id.tv_ok })
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.imgv_return:
                onBackPressed();
                break;



            case R.id.tv_ok:
                save();
                break;
        }
    }


}
