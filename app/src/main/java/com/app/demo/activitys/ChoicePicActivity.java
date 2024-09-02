package com.app.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.demo.adapters.GridAdapter;
import com.app.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public  class ChoicePicActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.gridview)
    GridView gridview;

    GridAdapter adapter;
    List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_pic);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        String name=getIntent().getStringExtra("name");
        if (StringUtils.isEmpty(name))return;
        switch (name){
            case "gl":
                list.add(R.mipmap.g0);
                list.add(R.mipmap.g1);
                list.add(R.mipmap.g2);
                list.add(R.mipmap.g3);
                list.add(R.mipmap.g8);
                list.add(R.mipmap.g5);
                list.add(R.mipmap.g9);
                list.add(R.mipmap.g7);
                list.add(R.mipmap.g8);
                list.add(R.mipmap.g9);

                list.add(R.mipmap.g0);
                list.add(R.mipmap.g1);
                list.add(R.mipmap.g2);
                list.add(R.mipmap.g3);
                list.add(R.mipmap.g8);
                list.add(R.mipmap.g5);
                list.add(R.mipmap.g9);
                list.add(R.mipmap.g7);
                list.add(R.mipmap.g8);
                list.add(R.mipmap.g9);

                break;
            case "Scenery":
                list.add(R.mipmap.z0);
                list.add(R.mipmap.z1);
                list.add(R.mipmap.z2);
                list.add(R.mipmap.z3);
                list.add(R.mipmap.z8);
                list.add(R.mipmap.z5);
                list.add(R.mipmap.z9);
                list.add(R.mipmap.z7);
                list.add(R.mipmap.z8);
                list.add(R.mipmap.z11);
                list.add(R.mipmap.z10);

                list.add(R.mipmap.z11);
                list.add(R.mipmap.z12);
                list.add(R.mipmap.z13);
                list.add(R.mipmap.z14);
                list.add(R.mipmap.z15);
                list.add(R.mipmap.z16);
                list.add(R.mipmap.z17);
                list.add(R.mipmap.z18);
                list.add(R.mipmap.z19);
                list.add(R.mipmap.z20);

                list.add(R.mipmap.z21);
                list.add(R.mipmap.z22);
                list.add(R.mipmap.z23);
                list.add(R.mipmap.z24);
                list.add(R.mipmap.z25);
                list.add(R.mipmap.z26);
                list.add(R.mipmap.z27);
                list.add(R.mipmap.z28);
                list.add(R.mipmap.z29);
                list.add(R.mipmap.z30);
                break;
            case "zb":
                list.add(R.mipmap.a0);
                list.add(R.mipmap.a1);
                list.add(R.mipmap.a2);
                list.add(R.mipmap.a3);
                list.add(R.mipmap.a4);
                list.add(R.mipmap.a5);
                list.add(R.mipmap.a6);
                list.add(R.mipmap.a7);
                list.add(R.mipmap.a8);
                list.add(R.mipmap.a9);
                list.add(R.mipmap.a10);
                list.add(R.mipmap.a11);
                list.add(R.mipmap.a12);
                list.add(R.mipmap.a13);
                list.add(R.mipmap.a14);
                list.add(R.mipmap.a15);
                list.add(R.mipmap.a16);
                list.add(R.mipmap.a17);

                list.add(R.mipmap.a0);
                list.add(R.mipmap.a1);
                list.add(R.mipmap.a2);
                list.add(R.mipmap.a3);
                list.add(R.mipmap.a4);
                list.add(R.mipmap.a5);
                list.add(R.mipmap.a6);
                list.add(R.mipmap.a7);
                list.add(R.mipmap.a8);
                list.add(R.mipmap.a9);
                list.add(R.mipmap.a10);
                list.add(R.mipmap.a11);
                list.add(R.mipmap.a12);
                list.add(R.mipmap.a13);
                list.add(R.mipmap.a14);
                list.add(R.mipmap.a15);
                list.add(R.mipmap.a16);
                list.add(R.mipmap.a17);
                break;
        }



        adapter = new GridAdapter(this, (ArrayList) list, R.layout.item_gridview);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new EventMessage(EventMessage.CHOICE_PIC, list.get(position)));
                finish();
            }
        });

    }

    @OnClick(R.id.imgv_return)
    public void onViewClicked() {
        onBackPressed();
    }
}
