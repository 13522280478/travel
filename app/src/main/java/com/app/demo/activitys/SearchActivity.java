package com.app.demo.activitys;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.app.base.BaseActivity;
import com.app.base.CommonAdapter;
import com.app.base.ViewHolder;
import com.app.demo.R;
import com.app.demo.beans.GongluoBean;
import com.app.utils.StringUtils;
import com.app.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.imgv_return)
    ImageView imgvReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.bt_search)
    Button btSearch;
    @BindView(R.id.listview_search)
    ListView listviewSearch;

    private List<GongluoBean> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        tvTitle.setText("搜索");
        initAdapter();

    }

    private void initAdapter() {
        adapter = new MyAdapter(this, (ArrayList) list, R.layout.item_seenery);
        listviewSearch.setAdapter(adapter);
        listviewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent( SearchActivity.this,GonglueDetailActivity.class);
                intent.putExtra("bean", list.get(position));
                startActivity(intent);
            }
        });
    }


    private void onSearch(String content) {
        list.clear();
        List<GongluoBean> list_all = DataSupport.findAll(GongluoBean.class);
        for (int i = 0; i < list_all.size(); i++) {
            if (list_all.get(i).name.contains(content)||
                    list_all.get(i).content.contains(content)||
                    list_all.get(i).like.contains(content)||
                    (list_all.get(i).num+"").contains(content)
            ) {
                list.add(list_all.get(i));
            }
        }

        if (list.size() == 0) {
            list.addAll(list_all);
            ToastUtil.showToast(this, "没有结果");
        }

        adapter.notifyDataSetChanged();
    }


    @OnClick({R.id.imgv_return, R.id.bt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.bt_search:
                if (StringUtils.isEmpty(edtSearch.getText().toString())) {
                    ToastUtil.showToast(this, "请输入搜索内容");
                    return;
                }
                onSearch(edtSearch.getText().toString());
                break;
        }
    }

    class MyAdapter extends CommonAdapter {
        public MyAdapter(Context context, ArrayList datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(ViewHolder holder, Object o, int position) {
            GongluoBean bean = (GongluoBean) o;
            SimpleDraweeView simpleDraweeView = holder.getView(R.id.imgv_list);
            TextView tv_list_item = holder.getView(R.id.tv_list_item);
            TextView tv_content = holder.getView(R.id.tv_content);

            simpleDraweeView.setImageResource(bean.getPic());
            tv_list_item.setText(bean.getName());
            tv_content.setText(bean.getContent());
        }
    }
}
