package com.app.demo.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.base.BaseDialog;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.utils.StringUtils;
import com.app.utils.ToastUtil;
import com.yalantis.ucrop.util.ScreenUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * @Author: Paper
 * desc:
 */
public class ZhifuDialog extends BaseDialog {


    String sel;
    double price;
    ImageView iv_wx, iv_zfb;
    TextView tv_commit;
    TextView tv_title;
    EditText et_remark;

    public ZhifuDialog(Activity activity, double price) {
        super(activity);
        this.price = price;
    }

    @Override
    public void initDialogEvent(Window window) {
        window.setContentView(R.layout.dialog_zhifu);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //init view
        et_remark = window.findViewById(R.id.et_remark);

        iv_wx = window.findViewById(R.id.iv_wx);
        iv_zfb = window.findViewById(R.id.iv_zfb);
        tv_title = window.findViewById(R.id.tv_title);
        tv_commit = window.findViewById(R.id.tv_commit);

        //set view
        //获取地址管理


        tv_title.setText("支付金额￥" + price);
        selTag(0);


        iv_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selTag(0);
            }
        });
        iv_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selTag(2);
            }
        });
        //取消
        window.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //确定
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isEmpty(et_remark.getText().toString())) {
                    ToastUtil.showToast(activity, "请完善信息，再继续");
                    return;
                }

                dialog.dismiss();
                EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH2, sel, et_remark.getText().toString()));

            }
        });

    }

    private void selTag(int tag) {
        if (tag == 0) {
            sel = "微信";
            iv_wx.setImageResource(R.mipmap.icon_sel1);
            iv_zfb.setImageResource(R.mipmap.icon_sel0);
        } else if (tag == 2) {
            sel = "支付宝";
            iv_wx.setImageResource(R.mipmap.icon_sel0);
            iv_zfb.setImageResource(R.mipmap.icon_sel1);
        }
    }


    @Override
    public void showDialog() {
        dialog = new AlertDialog.Builder(activity).create();
        //点击外部区域取消dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(null);
        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout((int) (ScreenUtils.getScreenWidth(activity) * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        //解决棱角问题
        window.setBackgroundDrawable(new BitmapDrawable());
        initDialogEvent(window);
    }
}

