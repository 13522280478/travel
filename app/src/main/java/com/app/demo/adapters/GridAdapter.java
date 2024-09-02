package com.app.demo.adapters;

import android.content.Context;
import android.widget.ImageView;


import com.app.base.CommonAdapter;
import com.app.base.ViewHolder;
import com.app.demo.R;

import java.util.ArrayList;

/**
 * @anthor : 大海
 * 每天进步一点点
 * @description : 图片预览布局适配器
 */


public class GridAdapter extends CommonAdapter {

    public GridAdapter(Context context, ArrayList datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void setView(ViewHolder holder, Object o, int position) {
        int img = (int) o;
        ImageView imgv_item = holder.getView(R.id.imgv_item);
        imgv_item.setImageResource(img);
    }
}
