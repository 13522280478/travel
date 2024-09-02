package com.app.demo.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.base.BaseFragment;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.demo.activitys.LoginActivity;
import com.app.demo.activitys.MyInfoActivity;
import com.app.demo.activitys.OrderListActivity;
import com.app.demo.activitys.OrderSceneryListActivity;
import com.app.demo.activitys.PassWordResetActivity;
import com.app.utils.DialogUtil;
import com.app.utils.SharedPreferencesUtil;

import com.app.utils.UserManager;
import com.app.widgts.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.tv_my_info)
    TextView tv_my_info;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_order)
    TextView tv_order;



    @BindView(R.id.tv_pwd)
    TextView tv_pwd;
    @BindView(R.id.tv_scenery_order)
    TextView tv_scenery_order;


    private String dialog_title = "退出登录";
    private String dialog_content = "是否确定退出登录？";
    private CustomDialog customDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        setView();

        return view;
    }

    private void setView() {



        if (UserManager.getUserType(getActivity())== 0) {
            tv_name.setText("用户");


            tv_my_info.setVisibility(View.VISIBLE);


            tv_pwd.setVisibility(View.VISIBLE);
            tv_scenery_order.setText("我的景点订单");
            tv_order.setText("我的周边订单");
        } else {
            tv_name.setText("管理员");


            tv_my_info.setVisibility(View.GONE);


            tv_pwd.setVisibility(View.GONE);

            tv_scenery_order.setText("用户景点订单");
            tv_order.setText("用户周边订单");
        }

    }


    @OnClick({R.id.tv_scenery_order,    R.id.tv_my_info, R.id.tv_pwd, R.id.tv_logout, R.id.tv_order})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {

            case R.id.tv_my_info:
            case R.id.layout_info:
                skipActivity(getActivity(), MyInfoActivity.class);
                break;
            case R.id.tv_order:
                skipActivity(getActivity(), OrderListActivity.class);
                break;
            case R.id.tv_scenery_order:
                skipActivity(getActivity(), OrderSceneryListActivity.class);
                break;


            case R.id.tv_pwd:
                skipActivity(getActivity(), PassWordResetActivity.class);
                break;

            case R.id.tv_logout:
                Logout();
                break;
            


        }
    }


    private void Logout() {
        customDialog = DialogUtil.showDialog(getActivity(), customDialog, 2, dialog_title, dialog_content, "取消", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPreferencesUtil.removeAll(getActivity(), "user");
                skipActivity(getActivity(), LoginActivity.class);
                getActivity().finish();
            }
        });

        if (customDialog != null && !customDialog.isShowing()) {
            customDialog.show();
        }


        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                customDialog = null;
            }
        });
    }

    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.REFRESH) {
            setView();
        }
    }
}
