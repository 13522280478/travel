package com.app.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.beans.UserBean;
import com.app.demo.MainActivity;
import com.app.demo.R;
import com.app.utils.SharedPreferencesUtil;
import com.app.utils.StringUtils;
import com.app.utils.ToastUtil;
import com.app.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.inputName)
    EditText inputName;
    @BindView(R.id.inputpwd)
    EditText inputpwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindView(R.id.rg)
    RadioGroup rg;

    int type;


    //N组管理员账号信息
    public final static String[][] admin = {{"123", "123"},};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);





        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_1) {
                    type = 0;
                    tvRegister.setVisibility(View.VISIBLE);
                } else {
                    type = 1;
                    tvRegister.setVisibility(View.GONE);
                }
            }
        });
    }


    @OnClick({R.id.toLogin, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.toLogin:
                Login();
                break;


            case R.id.tv_register:
                showActivity(this, RegisterActivity.class);
                break;
        }
    }


    private void Login() {
        String name = inputName.getText().toString();
        String pwd = inputpwd.getText().toString();
        if (StringUtils.isEmpty(name)) {
            ToastUtil.showToast(this, "请输入用户名");
            return;
        }

        if (StringUtils.isEmpty(pwd)) {
            ToastUtil.showToast(this, "请输入密码");
            return;
        }
        if (type == 0) {//普通用户
            boolean isHaveUser = UserManager.isHaveUser(name);
            if (isHaveUser) {//有该用户
                if (UserManager.isOk(name, pwd)) { //密码正确
                    UserBean userBean = UserManager.getUser(name);

                    SharedPreferencesUtil.saveDataBean(this, userBean, "user");
                    showActivity(this, MainActivity.class);
                    finish();

                } else {
                    ToastUtil.showToast(this, "密码不匹配");
                }
            } else {
                ToastUtil.showToast(this, "该身份无此用户，请先注册");
            }
        } else {//管理员
            for (int i = 0; i < admin.length; i++) {
                if ((admin[i][0].equals(name) && admin[i][1].equals(pwd))) {
                    UserBean userBean = new UserBean();
                    userBean.setType(1);
                    userBean.setName("管理员");
                    userBean.setUser_id(name);
                    SharedPreferencesUtil.saveDataBean(this, userBean, "user");
                    showActivity(this, MainActivity.class);
                    return;
                }
            }
            ToastUtil.showToast(this, "账号或密码不对");
        }




    }
}
