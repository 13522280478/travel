package com.app.demo.activitys;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.beans.EventMessage;
import com.app.beans.UserBean;
import com.app.demo.R;
import com.app.utils.StringUtils;
import com.app.utils.ToastUtil;
import com.app.utils.UserManager;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.inputpwd)
    EditText inputpwd;
    @BindView(R.id.inputId)
    EditText inputId;
    @BindView(R.id.inputName)
    EditText inputName;

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_del)
    TextView tv_del;


    UserBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);




        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bean = (UserBean) bundle.getSerializable("bean");
        }

        if (bean != null) {//修改
            tvTitle.setText("个人资料");
            inputId.setText(bean.user_id);

            tv_del.setVisibility(View.VISIBLE);
            inputpwd.setText(bean.password);
            et_phone.setText(bean.mobile);
            inputName.setText(bean.name);

        } else { //注册
            tvTitle.setText("注册");
            tv_del.setVisibility(View.GONE);
        }


    }

    @OnClick({R.id.imgv_return, R.id.tv_register , R.id.tv_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_del:
                DataSupport.deleteAll(UserBean.class, "user_id=?", bean.user_id);
                EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH4 ));
                onBackPressed();
                break;


            case R.id.imgv_return:
                onBackPressed();
                break;

            case R.id.tv_register:

                String id = inputId.getText().toString();
                String pwd = inputpwd.getText().toString();
                String name = inputName.getText().toString();
                String phone = et_phone.getText().toString();
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone)  ) {
                    ToastUtil.showToast(this, "请完善信息");
                    return;
                }

                if (StringUtils.isEmpty(id) || StringUtils.isEmpty(pwd)) {
                    ToastUtil.showToast(this, "请完善信息");
                    return;
                }


                if (bean == null) {
                    if (UserManager.isHaveUser(id)) {
                        ToastUtil.showToast(this, "已存在该用户");
                        return;
                    }
//                    if (pwd.length() != 6) {
//                        ToastUtil.showToast(this, "密码必须为6位");
//                        return;
//                    }
//                    if (StringUtil.isNumeric(pwd)) {
//                        ToastUtil.showToast(this, "密码不能为纯数字");
//                        return;
//                    }


                    UserBean userBean = new UserBean();

                    userBean.setUser_id(id); //登录名
                    userBean.setPassword(pwd);
                    userBean.name = name;
                    userBean.mobile = phone;
                    userBean.setType(0);
                    userBean.save();
                    ToastUtil.showToast(this, "注册成功");

                } else {

                    ContentValues values = new ContentValues();
//                    values.put("user_id", id);
                    values.put("password", pwd);
                    values.put("name", name);
                    values.put("mobile", phone);

                    DataSupport.updateAll(UserBean.class, values, "user_id=?", bean.user_id);
                    ToastUtil.showToast(this, "修改成功");
                }
                EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH4 ));
                onBackPressed();

                break;
        }
    }

}
