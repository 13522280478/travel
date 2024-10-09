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
import com.app.demo.activitys.LoginActivity;
import com.app.demo.activitys.SceneryAddActivity;
import com.app.demo.activitys.SceneryDetailActivity;
import com.app.demo.activitys.SearchActivity;
import com.app.demo.beans.SceneryBean;
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

/**
 * 景点页面
 */
public class SceneryFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.lv)
    ScrollListView lv;
    @BindView(R.id.tv_right_bt)
    TextView tv_right_bt;

    private List<SceneryBean> list = new ArrayList<>();
    private MyAdapter adapter;


    public SceneryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scenery, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        lv.setFocusable(false);
        lv.setFocusableInTouchMode(false);
        tv_right_bt.setVisibility(View.GONE);
        initBanner();
        initList();
        return view;
    }

    @OnClick({R.id.tv_right_bt })
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_right_bt:
                skipActivity(getActivity(), SceneryAddActivity.class);
                break;
        }
    }


    //列表
    private void initList() {


        if (UserManager.getUserType(getActivity()) == 0) {
            tv_right_bt.setVisibility(View.GONE);
        } else {
            tv_right_bt.setVisibility(View.VISIBLE);
            tv_right_bt.setText("发布");
        }

        EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH5));

        adapter = new MyAdapter(getActivity(), (ArrayList) list, R.layout.item_seenery);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SceneryDetailActivity.class);
                intent.putExtra("bean", list.get(position));
                startActivity(intent);
            }
        });

    }


    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.REFRESH5) {
            List<SceneryBean> temp = DataSupport.findAll(SceneryBean.class);
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
            SceneryBean bean = new SceneryBean();

            String name = "";

            String cont = "";
            String like;
            int num;
            double price;
            int pic = 0;
            if (i % sum == 0) {
                pic = R.mipmap.z0;
                name = "北国冰城";
                cont = "【食】据说，在 哈尔滨 ，能激怒一个厨师最好的办法，就是跟他说没吃饱。这里有红肠、铁锅炖、锅包肉、炖粉条、地三鲜、大拉皮……随便一点都是一大盆一大碟的上。 东北 菜多以炖煮为主，食材丰富，有咸有甜，不嗜辣，还蛮不错。此行唯一的遗憾是选在了过年期间去，极大部分好吃的店都放假休息，难得还在营业的也大排长龙或涨价， 成功 吃上的大餐不多，有些老字号也没能吃上，建议尽可能避开过年这段时间，毕竟过年对大家都是蛮重要的。\n" +
                        "\n" +
                        "【住】我们选择的是位于 哈尔滨 站附近的大洋宾馆，离 尚志 大街地铁站只有几百米，往前走就是道里菜市场和索菲亚大教堂，离中央大街也不远，出行、吃喝都方便。\n" +
                        "\n" +
                        "【行】 哈尔滨 的景点总体比较集中，如果不去 雪乡 或滑雪场（这些需要打车或坐高铁动车前往），基本可以通过地铁/步行到处跑，在市区多逛逛也是别有一番风味的。\n" +
                        "p.s. 哈尔滨 的冬天约下午3-4点就开始日落天黑，白天时间短，需早睡早起才能尽可能把行程排在白天。";
                like = "适合夏季游玩";
                num = 5500;
                price = 15.00;

            } else if (i % sum == 1) {
                pic = R.mipmap.z1;
                name = "日照 金山";
                cont = "我要开上一辆车，\n" +
                        "不用豪华漂亮，只要可靠粗犷，\n" +
                        "也不用很大，只要能带上三两好友。\n" +
                        "就从春天开始流浪吧，\n" +
                        "先走川、滇、藏、疆，\n" +
                        "从一个地方，到另一个地方，\n" +
                        "从陌上满桃花，到山川共白首。\n" +
                        "时间可长可短，全凭心意；\n" +
                        "路程可远可近，全看心情。\n" +
                        "在路上，便翻山过河、披风破雨，\n" +
                        "笃行前路如读书，怎能不欢喜？！\n" +
                        "停下来，就饮酒微醺、登高望远，\n" +
                        "闲晒太阳看流云，如何不欢喜？！\n" +
                        "晨起看日出，临夜赏晚霞；\n" +
                        "白日游山川，夜来观星河。\n" +
                        "见谁都可亲，万般皆可爱。\n" +
                        "访雪山神湖当燃烟，是敬香神灵；\n" +
                        "宿山野湖畔当饮酒，要礼敬山河。\n" +
                        "心有敬畏，诸邪不扰。\n" +
                        "\n" +
                        "叔本华说：远航归来总有故事可说！\n" +
                        "确实不得不说。不得不说，三年疫情防控，一朝解封，恍然如梦；又不得不说，三年逐梦神山，一朝得见，了了心愿却又起心念！曾经不只一次问过自己，抱怨过吗？！内心的答案很坚定：没有。过程中有执着，但从没有过偏执的情绪。\n" +
                        "自驾八年，大部分都在藏区，回头细数，看过的雪山已经不少了。从初始的雅拉、青饶，到后来的亚丁三神山、 贡嘎 、南迦巴瓦、 新疆 的木扎尔特峰、白马，玉龙算上吧，都各有特色，但无一不是俊美雄伟。但梅里真的很特别，她是唯一不需专程进景区，在国道边就能近距离欣赏的雪山。当然，季节、天气也很重要。\n" +
                        "今年，我们就很幸运！得以观赏完美的 日照 金山。";
                like = "适合夏季游玩";
                num = 15500;
                price = 25.00;

            } else if (i % sum == 2) {
                pic = R.mipmap.z2;
                name = "318川藏";
                cont = "318川藏南线/林芝桃花节/稻城亚丁/色达/然乌湖/南迦巴瓦峰/林芝/拉萨10日游（318自驾川藏线穿藏装旅拍+望高原星空+赠送24小时接机服务+随车配备制氧机+四川成都出发藏拉萨+川藏线）";
                like = "适合春夏季游玩";
                num = 60000;
                price = 45.00;

            } else if (i % sum == 3) {
                pic = R.mipmap.z3;
                name = "九寨沟";
                cont = "【好评过千】四川全景定制4日沉浸式深度游（仙境九寨黄龙+达古冰川+毕棚沟鹧鸪山滑雪温泉+探秘三星堆+成都网红打卡+拜水都江堰问道青城山+听峨眉佛音船游乐山大佛+特色民宿等一起来凹）";
                like = "适合春夏秋冬季游玩";
                num = 19000;
                price = 65.00;

            } else if (i % sum == 4) {
                pic = R.mipmap.z4;
                name = "甘孜";
                cont = "【甘孜全景定制】色达·稻城亚丁·墨石公园·格萨尔王城·理塘·鱼子西7日全景定制游·更多甘孜秘境乡城·四姑娘山·牛背山·冷嘎措·新路海·措卡湖·格聂之眼·德格印经院·月亮湖·亚青寺～无人机航拍·白塔寺旅拍·高原雪域下午茶·骑马射箭草原狂奔";
                like = "适合春夏秋冬季游玩";
                num = 58000;
                price = 58.00;

            } else if (i % sum == 5) {
                pic = R.mipmap.z5;
                name = "稻城亚丁";
                cont = "【退改无忧+千人好评】四川稻城亚丁/理塘/甘孜/鱼子西/冷嘎措5日定制游（丁真的世界+川西草原骑马旅拍体验+雪山草原冰川白塔+亚丁村悠闲下午茶+赠送藏餐特色体验+送成都住宿加接机服务）";
                like = "适合春季游玩";
                num = 60000;
                price = 75.00;

            } else {
                pic = R.mipmap.z6;
                name = "峨眉山";
                cont = "·峨眉山是佛教名山，也是普贤菩萨的道场，山中众多的寺庙，让这里充满了佛陀的气息。\n" +
                        "·山中的景色极美，四季各有美景可看。随海拔不同，分为低中高三个区。\n" +
                        "·登顶金顶远眺，视野宽阔，景色壮丽，有一览众山小的气势。选择观看日出和云海都是人气项目。\n" +
                        "·有时还会举办一些民俗活动。登顶后温差很大，建议上山的朋友做好衣物保暖。";
                like = "适合春秋季游玩";
                num = 20000;
                price = 60.00;

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
        list_banner.add(R.mipmap.z0);
        list_banner.add(R.mipmap.z1);
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
            SceneryBean bean = (SceneryBean) o;
            SimpleDraweeView simpleDraweeView = holder.getView(R.id.imgv_list);
            TextView tv_list_item = holder.getView(R.id.tv_list_item);
            TextView tv_content = holder.getView(R.id.tv_content);

            simpleDraweeView.setImageResource(bean.getPic());
            tv_list_item.setText(bean.getName());
            tv_content.setText(bean.getContent());

        }
    }
}
