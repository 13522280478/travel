package com.app.demo.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.BaseFragment;
import com.app.base.CommonAdapter;
import com.app.base.ViewHolder;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.demo.activitys.GonglueAddActivity;
import com.app.demo.activitys.GonglueDetailActivity;
import com.app.demo.activitys.LoginActivity;
import com.app.demo.activitys.SceneryAddActivity;
import com.app.demo.activitys.SceneryDetailActivity;
import com.app.demo.activitys.SearchActivity;
import com.app.demo.beans.GongluoBean;
import com.app.utils.UserManager;
import com.app.widgts.ScrollListView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class GonglueFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.lv)
    ScrollListView lv;
    @BindView(R.id.tv_right_bt)
    TextView tv_right_bt;

    private List<GongluoBean> list = new ArrayList<>();
    private MyAdapter adapter;

    public GonglueFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gonglue, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        lv.setFocusable(false);
        lv.setFocusableInTouchMode(false);
        if (UserManager.getUserType(getActivity())==0){
            tv_right_bt.setVisibility(View.GONE);
        }else {
            tv_right_bt.setVisibility(View.VISIBLE);
            tv_right_bt.setText("发布");
        }


        initBanner();
        initList();
        return view;
    }

    @OnClick({R.id.tv_right_bt, R.id.tv_search})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_right_bt:
                skipActivity(getActivity(), GonglueAddActivity.class);
                break;

            case R.id.tv_search:
                skipActivity(getActivity(), SearchActivity.class);
                break;
        }
    }


    //列表
    private void initList() {
        if (UserManager.getUserType(getActivity()) == 0) {
            tv_right_bt.setVisibility(View.GONE);
        } else {
            tv_right_bt.setVisibility(View.VISIBLE);
        }


        adapter = new MyAdapter(getActivity(), (ArrayList) list, R.layout.item_gonglue);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GonglueDetailActivity.class);
                intent.putExtra("bean", list.get(position));
                startActivity(intent);
            }
        });
        EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH6));

    }

    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.REFRESH6) {
            List<GongluoBean> temp = DataSupport.findAll(GongluoBean.class);
            if (temp != null && temp.size() > 0) {
                list.clear();
                list.addAll(temp);
            } else {
                initListData();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void initListData() {
        list.clear();
        int sum = 7;
        for (int i = 0; i < sum; i++) {
            GongluoBean bean = new GongluoBean();

            String name = "";
            String cont = "";
            String like;
            int num;
            double price;
            int pic = 0;
            if (i % sum == 0) {
                pic = R.mipmap.g0;
                name = "北京·天安门广场";
                cont = "\n" +
                        "·世界上最大的广场之一，位于长安街南侧，北京城的传统中轴线上。\n" +
                        "·广场中心为人民英雄纪念碑，西侧是人民大会堂，东侧是国家博物馆，广场的对面，是天安门城楼。\n" +
                        "·每天的清晨和傍晚，会举行升降旗仪式，对于初到北京的人来说，观看一场升降旗仪式是必做的事。\n" +
                        "·清晨来看升旗仪式的人员众多，务必提早出门或错峰出游。\n" +
                        "\n" +
                        "    用时参考\n" +
                        "    1-3小时\n" +
                        "\n" +
                        "交通\n" +
                        "    公交：\n" +
                        "    乘坐2/120/观光2线公交车至天安门广场西公交站，步行约168米即可到达；\n" +
                        "    乘坐2/5/5路区间/120/观光2线公交车天安门广场东公交站，步行约243米即可到达\n" +
                        "    地铁：\n" +
                        "    乘坐地铁2号线至前门地铁站，步行约333米即可到达\n" +
                        "\n" +
                        "门票\n" +
                        "    免费\n" +
                        "    tips:\n" +
                        "    需通过官方小程序【天安门广场预约参观】提前预约。\n" +
                        "\n" +
                        "开放时间\n" +
                        "    06:00-18:35 (1月1日-12月31日 周一-周日)\n" +
                        "    tips:\n" +
                        "    每天升旗前一小时开放广场，降旗后封闭广场。\n" +
                        "\n";
                like = "适合夏季游玩";
                num = 1500;
                price = 35.00;
            } else if (i % sum == 1) {
                pic = R.mipmap.g1;
                name = "北京·什刹海";
                cont = " ·什刹海也叫“十刹海”，因四周原有十座佛寺而得名，静谧湖景、名人故居、王府等古迹散落其中。\n" +
                        "·古典与现代相容，传统与前卫契合，自然景观与人文胜迹辉映的老北京景区\n" +
                        "·其中最著名的当属恭王府，这里曾是清朝贪官和珅的府邸，府中的“镇宅之宝”是康熙御笔福字碑。\n" +
                        "·什刹海酒吧街相当热闹，很多明星成名前都曾在这里驻唱，比较出名的酒吧有胡同写意、甲丁坊等。\n" +
                        "·若有兴趣，蜂蜂们可以拐到烟袋斜街寻访大清邮局，在邮局敲几个精美的邮戳作纪念。\n" +
                        "\n" +
                        "    电话\n" +
                        "    010-66162868\n" +
                        "    用时参考\n" +
                        "    1-3小时\n" +
                        "\n" +
                        "交通\n" +
                        "    公交：\n" +
                        "    乘坐5/135路公交车至德内甘水桥公交站，步行约369米即可到达\n" +
                        "    地铁：\n" +
                        "    乘坐地铁8号线至什刹海地铁站，步行约1.2公里即可到达\n" +
                        "    展开全部>\n" +
                        "\n" +
                        "门票\n" +
                        "    免费\n" +
                        "    tips:\n" +
                        "    具体详情请咨询景区\n" +
                        "\n" +
                        "开放时间\n" +
                        "    08:00-24:00(全天)；停止入场时间:24:00 (1月1日-12月31日 周一-周日)";
                like = "适合夏季游玩";
                num = 1500;
                price = 35.00;
            } else if (i % sum == 2) {
                pic = R.mipmap.g2;
                name = "超详细上海迪士尼攻略，从下车开始教你抢先每一秒";
                cont = "迪士尼乐园总共有两道入园关卡。一个就是安检通道，第二个便是购票通道。\n" +
                        "地铁站到安检通道，安检通道最好走最外边的，为你劲直可以来到最右边的购票通道，一般来说两侧的购票通道都要比中间的那个放行的快，所以过了安检以后，直接去往最右边的购票通道。\n" +
                        "进入购票通道之前，如果是一证多票的买法，必须同行的人到达以后才能进入！必须同行的人到达以后才能进入！必须同行的人到达以后才能进入！如果实在不行的话建议分开买票。\n" +
                        "\n" +
                        "第一班地铁大概7:15左右到达迪士尼，然后排队通过安检通道，若是第一班地铁到达，通过安检通道的时间大概为7:35左右，此后进入排队购票区，如果到的时间太早，购票区是不放行的，需要在外边排队先等候，大概接近8:00左右，购票区会开放，然后会经过两次验票，第一次是刚刚购票完以后，二次是真正进入园区。所以真正进入园区的时间大概为8:25左右。";
                like = "适合春夏季游玩";
                num = 3500;
                price = 55.00;
            } else if (i % sum == 3) {
                pic = R.mipmap.g3;
                name = "三亚八大人气景点";
                cont = "\n" +
                        "三亚地处海南岛最南端，是“足不出国”欣赏热带海岛风光的不二选择。对于首次到访的游客，建议安排4-5天游玩；若有计划再前往附近离岛，则需要多花费1-2天。\n" +
                        "由于主要景点自西至东散布于三亚湾、大东海、亚龙湾和海棠湾这四个区域内，加上交通的不太便捷，如何在有限的时间内玩转三亚，需要合理的规划。\n" +
                        "因此，我们根据目的地在马蜂窝用户间的热度，为大家整理出了以下这8个人气景点，包含了海湾、岛屿、森林公园、地标和文化体验。\n";
                like = "适合春夏秋冬季游玩";
                num = 1000;
                price = 25.00;
            } else if (i % sum == 4) {
                pic = R.mipmap.g4;
                name = "超实用的三亚5天自由行攻略（含行程推荐交通住宿指南）";
                cont = "一． 行前必备\n" +
                        "七大必备品，每一样都非常有必要——\n" +
                        "防晒霜！雨伞！泳衣！防蚊用品！偏光太阳镜！防水手机套！浮潜鞋！\n" +
                        "\n" +
                        "泳衣：建议携带两套\n" +
                        "浮潜鞋：如果在三亚玩水上项目或者去各种惊险刺激的水上乐园，一定要带一双软软的浮潜鞋。\n\n" +
                        "1.飞机到达——两个机场如何选（海口美兰＆三亚凤凰）\n" +
                        "乘坐飞机达到三亚有两个方案：\n" +
                        "1.先到达海口美兰机场，随后倒车环岛动车；\n" +
                        "\n" +
                        "2.直飞到三亚凤凰机场\n" +
                        "a:飞海口美兰机场，再换乘环岛动车到达三亚；\n" +
                        "往返三亚从美兰机场走有几个优点：\n" +
                        "1.机票价格较直飞三亚便宜100-300元左右，尤其是节假日旺季，价格差别就更为明显；\n" +
                        "2.是海口美兰机场航班次数多，可选余地大；\n" +
                        "3.海口美兰机场每天有30多趟列车发往三亚，换乘非常方便。\n";
                like = "适合春夏秋冬季游玩";
                num = 5800;
                price = 58.00;
            } else if (i % sum == 5) {
                pic = R.mipmap.g5;
                name = "成都“穷”玩攻略｜吃遍美食，看遍美景！";
                cont = "行程安排（吃玩攻略）\n" +
                        "经典推荐路线：\n" +
                        "Day1：锦里+武侯祠+春熙路\n" +
                        "Day2：熊猫基地+东郊记忆\n" +
                        "Day3：成都都江堰+青城山（或者人民公园+九眼桥）\n" +
                        "上面这个路线是游玩成都最最经典的路线之一啦，如果你是第一次去成都，可以按照这个路线走哦。如果你想按照自己的想法来走，我这里就把各个景点介绍下，去的时候挑选自己喜欢的就好啦！\n" +
                        "（一）锦里\n" +
                        "锦里位于成都市青羊区，据说是从三国时代就存在的老商业街了，1000多年过去，仍然掩盖不住锦里曾经的繁华。这里是成都最受外地游客喜爱的景点之一，也是著名的美食街，晚上去更是有另一番风景。";
                like = "适合春季游玩";
                num = 6000;
                price = 15.00;
            } else {
                pic = R.mipmap.g6;
                name = "如果你只有3-4天，我会这样带你玩遍成都经典";
                cont = "一年四季：成都属亚热带季风气候，四季分明，年平均气温16摄氏度，因此一年四季都能前来。由于独特的地势结构，让这里春可看花，夏能玩水，秋赏银杏，冬滑雪。\n" +
                        "  \n" +
                        "旅行灵感：成都的深厚历史文化，让我们有很多机会可以提前了解它，出行前看些相关的书籍、影视、纪录片等，都能帮我们在旅行中有更深刻的感受。推荐书籍《成都街巷志》《一双筷子吃成都》；纪录片《从成都出发》《航拍中国-四川》《城市24h》；音乐《成都》《春熙路旁》《变脸》……\n" +
                        " \n" +
                        "学几句成都方言：成都话属于西南官话，学几句成都话，也会让我们在成都的旅行更有意思：“巴适得板”——太好啦；“摆龙门阵”——聊天；“哦豁”——完蛋了。 ";
                like = "适合春秋季游玩";
                num = 2000;
                price = 10.00;
            }
            bean.ids = System.currentTimeMillis() + "";
            bean.user_id = LoginActivity.admin[0][0] + "";
            bean.setPic(pic);
            bean.setName(name);
            bean.setContent(cont);
            bean.like = like;
            bean.num = num;
            bean.price = price;
            bean.save();

            list.add(bean);

        }
    }


    /**
     * 轮播图
     */
    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new FresscoImageLoader());
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置允许手势滑动
        banner.setViewPagerIsScroll(true);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

        List list_banner = new ArrayList();
        list_banner.add(R.mipmap.g0);
        list_banner.add(R.mipmap.g1);
        banner.setImages(list_banner);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public class FresscoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            int url = (int) path;
            imageView.setImageResource(url);
        }

        //提供createImageView 方法，方便fresco自定义ImageView
        @Override
        public ImageView createImageView(Context context) {

            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) getLayoutInflater().inflate(R.layout.layout_banner_imageview, null);
//            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
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
