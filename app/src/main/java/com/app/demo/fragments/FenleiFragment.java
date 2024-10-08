package com.app.demo.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.app.base.BaseFragment;
import com.app.base.CommonAdapter;
import com.app.base.ViewHolder;
import com.app.beans.EventMessage;
import com.app.demo.R;
import com.app.demo.activitys.ZhoubianAddActivity;
import com.app.demo.activitys.GoodsDetailActivity;
import com.app.demo.beans.GoodsBean;
import com.app.utils.UserManager;
import com.app.widgts.ScrollListView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FenleiFragment extends BaseFragment {

    @BindView(R.id.lv)
    ScrollListView lv;
    @BindView(R.id.tv_0)
    TextView tv_0;
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_3)
    TextView tv_3;
    @BindView(R.id.tv_4)
    TextView tv_4;
    @BindView(R.id.tv_right_bt)
    TextView tv_right_bt;

    private List<GoodsBean> list = new ArrayList<>();
    private MyAdapter adapter;
    private String selTyle = "全部";

    private String[] str_type = new String[]{"全部", "交通", "餐饮", "酒店", "其他"};

    public FenleiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fenlei, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        lv.setFocusable(false);
        lv.setFocusableInTouchMode(false);
        if (UserManager.getUserType(getActivity()) == 0) {
            tv_right_bt.setVisibility(View.GONE);
        } else {
            tv_right_bt.setVisibility(View.VISIBLE);
            tv_right_bt.setText("发布");
            tv_right_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skipActivity(getActivity(), ZhoubianAddActivity.class);

                }
            });
        }

        initList();
        return view;
    }

    public void initData() {
        int sum = 18;
        for (int i = 0; i < sum; i++) {
            GoodsBean bean = new GoodsBean();

            String name = "";
            String remark = "";
            double goods_price = 0;
            int pic = 0;
            if (i % sum == 0) {
                pic = R.mipmap.a0;
                bean.setGoods_type("交通");
                name = "飞机";
                remark = "在这57天里，唐山机场对候机楼和航站楼的设备设施，以及机坪、驻场灯光等多项进行了改造，提升了机场的安全等级，为更好地服务旅客出行，主要分为下面几个部分:\n" +
                        "\n" +
                        "1、对候机楼航显广播系统进行了全面的升级改造工作，对候机楼内所有航显广播设备进行了升级；\n" +
                        "\n" +
                        "2、对整个机场的对讲机系统进行了升级改造，将原有模拟信号的对讲机系统全部升级为数字信号系统；\n" +
                        "\n" +
                        "3、对气象自动观测系统进行了升级改造，从之前的一套自动观测站升级为两套自动观测站加一套自动风站，增加了天气现场、云高等传感器，能够更好的提供航空气象服务，保证运行安全；\n" +
                        "\n" +
                        "4、机坪嵌缝料已到失效年限，进行整体更换；\n" +
                        "\n" +
                        "5、场区车辆通行道路年久失修，存在FOD风险，进行浅层修补，部分位置加盖沥青；\n" +
                        "\n" +
                        "6、保障设备整体排查，并进行维修，排除安全隐患；\n" +
                        "\n" +
                        "7、对助航灯光进行整体保养维护。";
                goods_price = 100;
            } else if (i % sum == 1) {
                pic = R.mipmap.a1;
                bean.setGoods_type("交通");
                name = "火车";
                remark = "河北省发改委称，目前，石家庄至雄安新区可经京广高铁、津保铁路直达。为进一步加强两地便捷高效联系，在雄安新区规划阶段，我们谋划了包括雄石城际铁路在内的“四纵两横”区域高速铁路交通网络，并经党中央、国务院同意，纳入了雄安新区规划纲要及总体规划。+" +
                        "\n" +
                        "经协调，雄石城际铁路已启动项目前期工作，并已编制完成可行性研究报告。河北省国民经济和社会发展第十四个五年规划纲要草案，正在研究起草阶段。我们将建议将雄石城际铁路纳入该草案，并进一步加快推进项目前期工作，力争早日具备开工建设条件，助力河北省经济社会高质量发展。";
                goods_price = 59;
            } else if (i % sum == 2) {
                pic = R.mipmap.a2;
                bean.setGoods_type("交通");
                name = "长途客运";
                remark = "中国网2月8日讯 据河北日报官方微博消息，石家庄市疫情防控指挥部办公室发布通知，决定自2021年2月8日起，调整石家庄市市域交通管控，有序恢复高速公路通行。经国家有关部门批准后，恢复铁路客运服务。民航客运和省际、市际长途客运暂不恢复。\n" +
                        "\n" +
                        "此外，石家庄市藁城区全域调整为中风险地区。增村镇、西关镇、廉州镇等重点管控区域，按照高风险管理，严格执行居家防控，加强人员健康监测。其他区域有序恢复正常生产生活，居民在落实各类常态化防控措施的前提下，原则上在藁城区域内活动，不扎堆、不聚集，严防交叉感染和疫情反弹。";
                goods_price = 80;
            } else if (i % sum == 3) {
                pic = R.mipmap.a3;
                bean.setGoods_type("餐饮");
                name = "林溪晚亭，专心只做一道菜 ";
                remark = "品牌名称：\n" +
                        "林溪晚亭\n" +
                        "\n" +
                        "公司名称：\n" +
                        "石家庄林溪晚亭餐饮服务有限公司\n" +
                        "\n" +
                        "品牌发源：石家庄\n" +
                        "\n" +
                        "主营产品：林溪晚亭古法烤鱼 林溪晚亭古法烤鱼\n" +
                        "\n" +
                        "行业分类： 烤鱼 特色餐饮\n" +


                        "\n" +
                        "公司地址：裕华区育才街怀特青少年儿童城1-108";
                goods_price = 30;
            } else if (i % sum == 4) {
                pic = R.mipmap.a4;
                bean.setGoods_type("餐饮");
                name = "梅梅家的烤肉";
                remark = "品牌名称：\n" +
                        "梅梅家的烤肉\n" +
                        "\n" +
                        "公司名称：\n" +
                        "梅梅家的烤肉品牌连锁中心\n" +
                        "\n" +
                        "品牌发源：石家庄\n" +
                        "\n" +
                        "主营产品：梅梅家的烤肉\n" +
                        "\n" +
                        "行业分类： 烧烤 烤肉\n" +

                        "\n" +
                        "公司地址：河北石家庄长安区光华路与长征街交叉口南行80米路";
                goods_price = 35;
            } else if (i % sum == 5) {
                pic = R.mipmap.a5;
                bean.setGoods_type("酒店");
                name = "涞源云璟白石山舍";
                remark = "涞源云璟白石山舍，由河北省专业化旅游产业集团河北旅投集团投资兴建。酒店位于河北省涞源县白石山镇，距离白石山景区7公里，毗邻景区游客中心。酒店占地130余亩，院落165套，客房总数605间，总建筑面积3.73万平方米。酒店设有接待中心、餐厅、咖啡茶吧、会议室、停车场等完善的配套设施，集特色住宿业态、餐饮休闲业态、商业休闲业态于一体。白石山舍融合了“简约、自然、个性、隐逸”的理念，让您感受乡居的住宿之美、田园之趣、健康之道、人情之暖和分享之乐，享受专属于您的独特的乡野田园式居住体验。";
                goods_price = 550;
            } else if (i % sum == 6) {
                pic = R.mipmap.a6;
                bean.setGoods_type("酒店");

                name = "北戴河安澜酒店";
                remark = "北戴河安澜酒店位于阿那亚社区内，毗邻阿那亚社区中心，自然条件优渥，美景天成，交通便利。酒店拥有各类温馨客房，客房面积较大。酒店大堂内设有休息区，室外聚会空间等配套设施，满足休闲度假需要。安澜酒店，竭尽全力创造放松、自在的度假氛围";
                goods_price = 580;
            } else if (i % sum == 7) {
                pic = R.mipmap.a7;
                bean.setGoods_type("酒店");
                name = "丰宁呆呆民宿";
                remark = "丰宁呆呆民宿，坝上精品民宿，座落于京北草原，丰宁坝上草原核心地区。四周草原广袤，白云缭绕，远离了城市的喧嚣，伴着马儿的嘶叫，让我们静静发呆。";
                goods_price = 730;
            } else if (i % sum == 8) {
                pic = R.mipmap.a8;
                bean.setGoods_type("酒店");

                name = " 麗枫酒店(承德避暑山庄店) ";
                remark = "麗枫酒店(承德避暑山庄店)位于承德市旅游桥东大老虎沟口，依山傍水，交通便捷，直行向北约3公里进入棒槌山，蛤蟆石旅游景区；步行向西约300米即可到达皇家园林——避暑山庄；驱车向北，约7分钟到达木雕千手千眼观世音像——普宁寺，并环绕在皇家寺庙群中，是您吃、住、行、玩的理想选择。酒店以现代中式风格为主，设施高档并具品味，设有各式客房，提供中央空调、24小时热水，让入住者感受大自然的抚爱，尽享浪漫奢华。中、西餐厅装饰典雅，注重养生，另设大小会议室。与酒店相配套的还有洗浴中心及时尚岩盘养生会馆，会馆的岩盘浴是承德经营的养生项目，是真正打造VIP的养生会馆，是各年龄层所青睐的一种绝佳的养生方式，是当今流行的SPA形式。";
                goods_price = 1388;
            } else if (i % sum == 9) {
                pic = R.mipmap.a9;
                bean.setGoods_type("其他");

                name = "看不够的坝上，走一趟北京-乌兰布统-红山军马场！";
                remark = "坝上是一个地理名词，位于华北平原和内蒙古高原交接地带，贯穿河北和内蒙古境内，从东到西依次分为张北坝上，沽源坝上，丰宁坝上，围场坝上。我这篇说的是围场坝上。\n" +
                        "为什么选择围场坝上？相对其他坝上，围场坝上位于内蒙古境内，北面接壤浑善达克沙地、锡林郭勒盟，植被较多，草原景观更加丰富，在高低起伏的草原上遍布着白桦林、山丁子树、蒙古栎等树木，并且以乌兰布统为中心形成了较为完善的草原旅游区。\n" +
                        "乌兰布统位于北京向北400多公里的内蒙古境内，四周草原、沙地、湖泊、峡谷、湿地、林地等景观丰富。在经过高速、国道以及几十公里的草原公路之后，到达乌兰布统有一种“柳暗花明又一村”的感觉。小镇虽然只有方圆不到2平方公里，但是酒店、餐馆、超市等一应俱全。\n" +
                        "六十年代，著名的“红山军马场”位于此地，因此名称延续至今。有个有趣的现象，从承德过来的班车，一般以“红山军马场”为抵达站，而从内蒙古的克什克腾旗始发的班车，通常以“乌兰布统”为抵达站，其实两个方向来的班车抵达的是同一个对方。 ";
                goods_price = 3380;
            } else if (i % sum == 10) {
                pic = R.mipmap.a10;
                bean.setGoods_type("其他");

                name = "承德避暑山庄详细攻略，看了如临其境";
                remark = "承德避暑山庄是清朝皇帝为了实现安抚、团结中国边疆少数民族，巩固国家统一的政治目的而修建的一座夏宫。始建于1703年，历经清康熙、雍正、乾隆三朝，耗时89年建成。其规模宏大，相当于颐和园的两倍，相当于北海公园的八倍，是中国现存最大的古典皇家园林，与北京颐和园、 苏州拙政园、留园并称为中国四大名园。\n" +
                        "承德避暑山庄分为宫殿区、平原区和山区，其中最大的看点就是星罗于山庄各个角落的康熙三十六景和乾隆三十六景。 ";
                goods_price = 3588;
            } else if (i % sum == 11) {
                pic = R.mipmap.a11;
                bean.setGoods_type("交通");

                name = "私家车";
                remark = "所谓私家车，是来自香港的概念，主要指家用的车辆私人自己买的，拥有使用支配权的，在不违法的情况下可以自由的使用支配。一辆汽车属于私人汽车还是公车，是以在车管所登记时车主的身份确定的。如果以机关、团体、单位名义登记，就是公车，如果是以个人名义登记，就是私车，所有权属于个人，私人拥有。\n" +
                        "私车中既有卡车一类商用车，也有小轿车一类乘用车。前者主要是生产资料，后者则属于生活资料。国内不少城市的出租车是私车，是以个人名义登记购买，这些小轿车也属于生产资料。\n" +
                        "车型以轿车为主，还有一些越野车和小面包。由此可见，私家车仅仅是私人汽车的一部分，其数量要大大小于私人汽车。";
                goods_price = 120;
            } else if (i % sum == 12) {
                pic = R.mipmap.a12;
                bean.setGoods_type("交通");

                name = "共享单车";
                remark = "骑车点停的全是青桔共享单车，手机APP地图上也标的是还车点，可怎么停都不行，提示不是停车点，无法还车。”许先生说，本来1.5元就能解决问题，结果他这次骑车用80分钟，花了4.5元，还被扣了10元的调度费，一共14.5元。所幸的是，他及时在骑车点进行投诉，10元的调度费才没有被扣，但还是多花几块钱。";
                goods_price = 5;
            } else if (i % sum == 13) {
                pic = R.mipmap.a13;
                bean.setGoods_type("餐饮");

                name = "沧州羊肠子";
                remark = "沧州地处华北平原东部，也是华北地区回族人口最多的城市之一，辖区内的孟村回族自治县。回族是这里的一个主要民族，那么有以牛羊肉为主的小吃就不奇怪了。所以沧州小吃羊肠子便是代表了，一方水土一方味。翻开中华饮食典故，能遗古留芳的风味小吃几乎皆有民间货真价实、廉价实惠的百姓味道。沧州羊肠子就是一种廉价物美的民间小吃，至今深受大众喜爱，印证了那句老话：“自古美味出民间。”";
                goods_price = 120;
            } else if (i % sum == 14) {
                pic = R.mipmap.a14;
                bean.setGoods_type("餐饮");

                name = "白洋淀松花蛋";
                remark = "松花蛋，伟大民族的智慧结晶之一，这可比发酵类腌制类的要微妙多了。松花蛋那种残存的石灰味、阵阵的氨气，都给我一种奇怪的蛊惑力。那时候我家老腌咸鸡蛋腌臭了，老人巨爱吃臭鸡蛋。可能是这种衬托，我觉得松花蛋在变质蛋阵营里委实亲切多了。更别说还有Q弹有力的口感，晶体剔透的外观和美如画卷的松花纹样。不过都与外地食客，估计很多人都接受不了这股氨水的味道。";
                goods_price = 21;
            } else if (i % sum == 15) {
                pic = R.mipmap.a15;
                bean.setGoods_type("餐饮");

                name = "石家庄鹿泉香椿";
                remark = "鹿泉区香椿在当地非常有名，是国家地理标志产品。在当地香椿有好多种吃法，油炸，凉拌，摊鸡蛋，做汤等等。香椿就跟鱼腥草、香菜一样，具有特殊的气味，喜欢的人顿顿都爱吃，不喜欢的人打死都吃不下去，但和香菜、鱼腥草相比，个人觉得香椿还是可以接受的。";
                goods_price = 32;
            } else if (i % sum == 16) {
                pic = R.mipmap.a16;
                bean.setGoods_type("餐饮");

                name = "邯郸邱县臭豆腐";
                remark = "邱县臭豆腐是河北省邯郸市邱县特产，当地人形容特点是臭味之余却蕴藏着一股浓郁的香气，虽非美味佳肴，却也耐人寻味。但是经过尝试后，我觉得没有一条对上的。没有氨基酸的鲜味，只有单纯的臭味，我和我哥一人吃了一块，那一份就没再动过。第三天嗓子眼里的臭味都没散，还不敢打嗝。";
                goods_price = 12;
            } else if (i % sum == 17) {
                pic = R.mipmap.a17;
                bean.setGoods_type("酒店");

                name = "石家庄富力洲际酒店";
                remark = "酒店位于槐安东路，毗邻新城市商业中心万达广场，是富力旗下投资开设的洲际酒店，前身是万达洲际；酒店以特有的设计风格和建筑品质及尽善的对客服务，成为城市商贸服务业当之无愧的新地标， 酒店拥有287间设计典雅的豪华客房，凭窗远眺，世纪公园的湖光掠影和高尔夫球场的绿草茵茵，尽收眼底。";
                goods_price = 4500;
            }

            bean.setGoods_id(System.currentTimeMillis() + "");

            bean.setGoods_price(goods_price);
            bean.setGoods_name(name);
            bean.setGoods_pic(pic);
            bean.remark = remark;

            bean.save();
            list.add(bean);
        }


    }

    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.REFRESH7) {
            initList();
        }
    }

    //列表
    private void initList() {
        list = DataSupport.findAll(GoodsBean.class);
        if (list != null && list.size() > 0) {
            Logger.e("-----------数据库取数据--list：" + list.size());
            list = DataSupport.findAll(GoodsBean.class);
        } else {
            initData();
        }
        adapter = new MyAdapter(getContext(), (ArrayList) list, R.layout.item_goods);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (UserManager.getUserType(getActivity()) == 0) {
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("bean", list.get(i));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), ZhoubianAddActivity.class);
                    intent.putExtra("bean", list.get(i));
                    startActivity(intent);
                }
            }
        });
        setSelPosit(0);
        tv_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelPosit(0);
            }
        });

        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelPosit(1);
            }
        });

        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelPosit(2);
            }
        });

        tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelPosit(3);
            }
        });

        tv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelPosit(4);
            }
        });


    }


    private void setSelPosit(int pos) {
        switch (pos) {
            case 0:
                tv_0.setTextColor(getResources().getColor(R.color.color_3853e8));
                tv_1.setTextColor(getResources().getColor(R.color.color_black));
                tv_2.setTextColor(getResources().getColor(R.color.color_black));
                tv_3.setTextColor(getResources().getColor(R.color.color_black));
                tv_4.setTextColor(getResources().getColor(R.color.color_black));

                break;
            case 1:
                tv_0.setTextColor(getResources().getColor(R.color.color_black));
                tv_1.setTextColor(getResources().getColor(R.color.color_3853e8));
                tv_2.setTextColor(getResources().getColor(R.color.color_black));
                tv_3.setTextColor(getResources().getColor(R.color.color_black));
                tv_4.setTextColor(getResources().getColor(R.color.color_black));

                break;
            case 2:
                tv_0.setTextColor(getResources().getColor(R.color.color_black));
                tv_1.setTextColor(getResources().getColor(R.color.color_black));
                tv_2.setTextColor(getResources().getColor(R.color.color_3853e8));
                tv_3.setTextColor(getResources().getColor(R.color.color_black));
                tv_4.setTextColor(getResources().getColor(R.color.color_black));

                break;
            case 3:
                tv_0.setTextColor(getResources().getColor(R.color.color_black));
                tv_1.setTextColor(getResources().getColor(R.color.color_black));
                tv_2.setTextColor(getResources().getColor(R.color.color_black));
                tv_3.setTextColor(getResources().getColor(R.color.color_3853e8));
                tv_4.setTextColor(getResources().getColor(R.color.color_black));

                break;
            case 4:
                tv_0.setTextColor(getResources().getColor(R.color.color_black));
                tv_1.setTextColor(getResources().getColor(R.color.color_black));
                tv_2.setTextColor(getResources().getColor(R.color.color_black));
                tv_3.setTextColor(getResources().getColor(R.color.color_black));
                tv_4.setTextColor(getResources().getColor(R.color.color_3853e8));

                break;


        }

        selTyle = str_type[pos];

        List<GoodsBean> temp = DataSupport.findAll(GoodsBean.class);
        list.clear();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getGoods_type().equals(selTyle) || selTyle.equals("全部")) {
                list.add(temp.get(i));
            }
        }
        adapter.notifyDataSetChanged();


    }


    class MyAdapter extends CommonAdapter {
        public MyAdapter(Context context, ArrayList datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(ViewHolder holder, Object o, int position) {
            GoodsBean bean = (GoodsBean) o;
            SimpleDraweeView simpleDraweeView = holder.getView(R.id.imgv_list);
            TextView tv = holder.getView(R.id.tv_list_item);
            TextView tv_price = holder.getView(R.id.tv_price);
            TextView tv_stype = holder.getView(R.id.tv_stype);

            tv.setText(bean.getGoods_name());
            tv_price.setText("¥  " + bean.getGoods_price() + "");
            tv_stype.setText(bean.getGoods_type());
            simpleDraweeView.setActualImageResource(bean.getGoods_pic());


        }
    }
}
