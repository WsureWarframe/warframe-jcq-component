package com.example;

import com.example.Cache.*;
import com.example.core.*;
import com.example.dao.GoodsMapper;
import com.example.dao.RenameMapper;
import com.example.dao.SubscribeMapper;
import com.example.entity.Rename;
import com.example.entity.Riven;
import com.example.entity.RivenProperty;
import com.example.entity.Sale;
import com.example.myLinster.AlertListener;
import com.example.tool.*;
import com.sobte.cqp.jcq.entity.*;
import com.sobte.cqp.jcq.event.JcqAppAbstract;
import com.sobte.cqp.jcq.message.CQCode;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.core.RivenMarketMsg.createRivenMarketMsg;
import static com.example.tool.PropertiesTool.fileReader;
import static com.example.tool.RivenMarketTool.nameToProperty;
import static com.example.tool.RivenTool.getVeiledRivenMsg;
import static com.example.tool.RivenTool.zhRivenType;
import static com.example.tool.TimeTool.getTomorrowZeroSeconds;

/**
 * 本文件是JCQ插件的主类<br>
 * <br>
 * <p>
 * 注意修改json中的class来加载主类，如不设置则利用appid加载，最后一个单词自动大写查找<br>
 * 例：appid(com.example.demo) 则加载类 com.example.Demo<br>
 * 文档地址： https://gitee.com/Sobte/JCQ-CoolQ <br>
 * 帖子：https://cqp.cc/t/37318 <br>
 * 辅助开发变量: {@link JcqAppAbstract#CQ CQ}({@link com.sobte.cqp.jcq.entity.CoolQ 酷Q核心操作类}),
 * {@link JcqAppAbstract#CC CC}({@link com.sobte.cqp.jcq.message.CQCode 酷Q码操作类}),
 * 具体功能可以查看文档
 */
public class Demo extends JcqAppAbstract implements ICQVer, IMsg, IRequest {

    String AppID = "com.example.demo";// 记住编译后的文件和json也要使用appid做文件名
    /**
     * 用main方法调试可以最大化的加快开发效率，检测和定位错误位置<br/>
     * 以下就是使用Main方法进行测试的一个简易案例
     *
     * @param args 系统参数
     */
    Map customSettings ;
    Map dict;
    Map customKeys;
    ArrayList<Map<String,String>> rewards;
    Map<String,RivenProperty> rivenPro;
    public static Map rmProperty;
    public static String settingFile="setting.json";
    public static String dictFile="Dict.json";
    public static String keysFile="keys.json";
    public static String rewardsFile="rewards.json";
    public static String rivenFile="riven.json";
    public static String rmFile="rm.json";

    CacheManagerImpl cacheManagerImpl ;
    DictManagerImpl dictManagerImpl;
    CacheListener cacheListener ;
    AlertListener alertListener ;
    SaleManagerImpl saleManagerImpl;
    RivenManagerImpl rivenManagerImpl;
    SubManageImpl subManageImpl;
    RmManagerImpl rmManagerImpl;

    SqlSession sqlSession;
    GoodsMapper goodsMapper;
    SubscribeMapper subscribeMapper;
    RenameMapper renameMapper;
    public static void main(String[] args) throws Exception {
        // CQ此变量为特殊变量，在JCQ启动时实例化赋值给每个插件，而在测试中可以用CQDebug类来代替他
        CQ = new CQDebug();//new CQDebug("应用目录","应用名称") 可以用此构造器初始化应用的目录
        CQ.logInfo("`" +
                "[JCQ] TEST Demo", "测试启动");// 现在就可以用CQ变量来执行任何想要的操作了
        // 要测试主类就先实例化一个主类对象
        Demo demo = new Demo();
        // 下面对主类进行各方法测试,按照JCQ运行过程，模拟实际情况
        demo.startup();// 程序运行开始 调用应用初始化方法
        demo.enable();// 程序初始化完成后，启用应用，让应用正常工作
        // 开始模拟发送消息
        // 模拟私聊消息
        // 开始模拟QQ用户发送消息，以下QQ全部编造，请勿添加

//        demo.privateMsg(0, 10001, 844157922L, "已启动!", 0);
//        demo.privateMsg(0, 10002, 2222222224L, "rm 海波斯 库拉对剑", 0);
//        demo.privateMsg(0, 10005, 2222222224L, "rm 德塔特", 0);
//        demo.privateMsg(0, 10003, 2222212224L, "rm le nz", 0);
//        demo.privateMsg(0, 10006, 2222222224L, "rm LANKA", 0);
//        demo.privateMsg(0, 10007, 2222222224L, "警报", 0);
//        demo.privateMsg(0, 10008, 2222222224L, "入侵", 0);
//        demo.privateMsg(0, 10009, 2222222224L, "突击", 0);
//        demo.privateMsg(0, 10010, 2222222224L, "裂缝", 0);
//        demo.privateMsg(0, 10011, 2222222224L, "地球", 0);
//        demo.privateMsg(0, 10012, 2222222224L, "平原", 0);
//        demo.privateMsg(0, 10011, 2222222224L, "奸商", 0);
//        demo.privateMsg(0, 10012, 2222222224L, "大黄", 0);
//        demo.privateMsg(0, 10011, 2222222224L, "达尔沃", 0);
//        demo.privateMsg(0, 10012, 2222222224L, "活动", 0);
//        demo.privateMsg(0, 10011, 2222222224L, "小小黑", 0);
//        demo.privateMsg(0, 10012, 2222222224L, "help", 0);
//
//        demo.privateMsg(0, 10012, 2222222224L, "WM查询 奶妈p", 0);
//        demo.privateMsg(0, 10012, 2222222224L, "WM 充电弹头p", 0);
//        demo.privateMsg(0, 10013, 2222222224L, "wiki 西格玛&南极座", 0);
//        demo.privateMsg(0, 10015, 2222222224L, "小小黑", 0);
//        demo.privateMsg(0, 10014, 2222222224L, "wfa 创伤", 0);
//        demo.privateMsg(0, 10011, 2222222224L, "模拟开卡", 0);

        CQ.logInfo("RobotQQ",CQ.getLoginQQ()+"");
//        demo.privateMsg(0, 10016, 2222222224L, "突击", 0);
//
//        demo.privateMsg(0, 10017, 2222222224L, "紫卡数值 绝路= 3+1", 0);
//        demo.privateMsg(0, 10018, 2222222224L, "订阅 卡拉克 亡魂 枪托", 0);
        // 模拟群聊消息
        // 开始模拟群聊消息
//        demo.groupMsg(0, 10006, 3456789012L, 844157922L, "", "[CQ:at,qq=10001] 以后叫我 a1", 0);
//        demo.groupMsg(0, 10008, 3456789012L, 11111111114L, "", "爬取url https://www.baidu.com", 0);

//        demo.groupMsg(0, 10010, 844157922L, 844157922L, "", "别名 Sci-critatak", 0);
        demo.groupMsg(0, 10011, 427984429L, 11111111114L, "", "ffwiki 诗人", 0);
        demo.groupMsg(0, 10012, 427984429L, 11111111114L, "", "全局wiki 吟游诗人", 0);
        demo.groupMsg(0, 10013, 427984429L, 11111111114L, "", "ffwiki 紫水宫", 0);
        // ......
        // 依次类推，可以根据实际情况修改参数，和方法测试效果
        // 以下是收尾触发函数
        // demo.disable();// 实际过程中程序结束不会触发disable，只有用户关闭了此插件才会触发

//        CQ.logInfo("测试",ImageTool.ocrRiven(ImageTool.image2Bytes("C:\\Users\\ws\\Pictures\\xst.png")));
        demo.exit();// 最后程序运行结束，调用exit方法
    }

    /**
     * 打包后将不会调用 请不要在此事件中写其他代码
     *
     * @return 返回应用的ApiVer、Appid
     */
    public String appInfo() {
        // 应用AppID,规则见 http://d.cqp.me/Pro/开发/基础信息#appid

        /**
         * 本函数【禁止】处理其他任何代码，以免发生异常情况。
         * 如需执行初始化代码请在 startup 事件中执行（Type=1001）。
         */
        return CQAPIVER + "," + AppID;
    }


    /**
     * 酷Q启动 (Type=1001)<br>
     * 本方法会在酷Q【主线程】中被调用。<br>
     * 请在这里执行插件初始化代码。<br>
     * 请务必尽快返回本子程序，否则会卡住其他插件以及主程序的加载。
     *
     * @return 请固定返回0
     */
    public int startup() {


        String proDirectory = System.getProperty("user.dir")+"\\app\\com.sobte.cqp.jcq\\app\\"+AppID+"\\"; //CQ.getAppDirectory();
        String devDirectory = System.getProperty("user.dir")+"\\";
        // 获取应用数据目录(无需储存数据时，请将此行注释)
        String appDirectory = devDirectory.equals(CQ.getAppDirectory())? devDirectory : proDirectory;
//        customSettings
        CQ.logInfo("CQ.getAppDirectory()",CQ.getAppDirectory());
        CQ.logInfo("文件目录",appDirectory);
        CQ.logInfo("文件目录",System.getProperty("user.dir"));
        customSettings  = PropertiesTool.initSettingMap(appDirectory+settingFile);
        customKeys      = PropertiesTool.initKeysMap(appDirectory+keysFile);
        //读取词库
        dict            = PropertiesTool.initDictMap();
        rivenPro = PropertiesTool.initKeysMap(appDirectory+rivenFile);
        rewards         = PropertiesTool.initRewards(appDirectory+rewardsFile);
        rmProperty      = (Map) JsonTool.jsonToMap(fileReader(appDirectory+rmFile));
        new AliasTool().init(rmProperty);
        CQ.logInfo("alias",""+AliasTool.alias.size());
        CQ.sendPrivateMsg(Long.parseLong(customSettings.get("devQQ").toString()), "报告主人，已启动");
        CQ.sendPrivateMsg(Long.parseLong(customSettings.get("devQQ").toString()), "项目目录"+System.getProperty("user.dir"));

        //缓存

        cacheManagerImpl = new CacheManagerImpl();
        cacheListener = new CacheListener(cacheManagerImpl);
        cacheListener.startListen();
        dictManagerImpl = new DictManagerImpl();
        initDictCache(dictManagerImpl,dict);
        CQ.logInfo("alertListener","加载前");
        CQ.logInfo("GroupList",CQ.getGroupList().toString());

        rivenManagerImpl = new RivenManagerImpl();
        initRivenCache(rivenManagerImpl,dict, rivenPro);
        alertListener = new AlertListener(cacheManagerImpl,rewards,customSettings,CQ.getGroupList());
        CQ.logInfo("alertListener","加载后");
        alertListener.startListen();

//        CQ.logInfo("测试WM接口1",HttpTool.getWMContent("valkyr_prime_set","orders"));
//        CQ.logInfo("测试WM接口2",HttpTool.getWMContent("valkyr_prime_set","statistics"));


//        CQ.logInfo("测试：：：：：：",MarketSearch.createMsg(JsonTool.jsonToMap(new HttpTool().getWMContent("valkyr_prime_set","orders")),JsonTool.jsonToMap(new HttpTool().getWMContent("valkyr_prime_set","statistics"))));
//        CQ.logInfo("测试：WFA  http",new HttpTool().getWfaContent("兰卡"));
//        CQ.logInfo("测试：WFA  ：：",new WfaSearch().createWfaMsg("兰卡"));
//        CQ.logInfo("测试 搜索",DictTool.searchZhFrom("Axi C3 Relic"));
        saleManagerImpl = new SaleManagerImpl();
        initSaleCache(saleManagerImpl,dict);
        subManageImpl = new SubManageImpl();
        initSunCache(subManageImpl,dict);
        rmManagerImpl = new RmManagerImpl();
        initRmCache(rmManagerImpl,dict);
        if(Boolean.parseBoolean(customSettings.get("noticeFlag").toString()))
        {
            new GroupMsg().sendGroupMsg(customSettings.get("notice").toString());
            CQ.logInfo("notice","notice已发送到全部群");
        }

        // 返回如：D:\CoolQ\app\com.sobte.cqp.jcq\app\com.example.demo\
        // 应用的所有数据、配置【必须】存放于此目录，避免给用户带来困扰。

        sqlSession = DBTools.getSession();
        goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        subscribeMapper = sqlSession.getMapper(SubscribeMapper.class);
        renameMapper = sqlSession.getMapper(RenameMapper.class);
        return 0;
    }

    public void initDictCache(DictManagerImpl dictManagerImpl,Map dict)
    {
        ArrayList<ArrayList<Map>> map= new ArrayList<>();
        map.add((ArrayList<Map>) dict.get("Dict"));
        map.add((ArrayList<Map>) dict.get("Sale"));
        map.add((ArrayList<Map>) dict.get("Alert"));
        map.add((ArrayList<Map>) dict.get("Invasion"));
        for(ArrayList<Map> type:map)
        {
            for(Map item:type)
            {
                if(item.get("en").toString()!=null &&!dictManagerImpl.isContains(item.get("en").toString()))
                {
                    dictManagerImpl.putCache(item.get("en").toString(),item.get("zh").toString(),0L);
                }
            }
        }
    }

    public void initSaleCache(SaleManagerImpl saleManagerImpl,Map dict)
    {
        ArrayList<Map> saleList = (ArrayList<Map>) dict.get("Sale");
        for(Map item:saleList)
        {
            if(item.get("zh").toString()!=null &&!saleManagerImpl.isContains(item.get("zh").toString()))
            {
                Sale sale = new Sale(item.get("search").toString(),item.get("zh").toString(),item.get("en").toString());
                saleManagerImpl.putCache(item.get("zh").toString(),sale,0L);
                if(!saleManagerImpl.isContains(item.get("en").toString()))
                    saleManagerImpl.putCache(item.get("en").toString(),sale,0L);
            }
        }
    }

    public void initSunCache(SubManageImpl subManageImpl,Map dict)
    {
        ArrayList<ArrayList<Map>> map= new ArrayList<>();
        map.add((ArrayList<Map>) dict.get("Alert"));
        map.add((ArrayList<Map>) dict.get("Invasion"));
        for(ArrayList<Map> type:map)
        {
            for(Map item:type)
            {
                if(!subManageImpl.isContains(item.get("zh").toString()))
                {
                    subManageImpl.putCache(item.get("zh").toString(),item,0L);
                }
            }
        }
    }

    public void initRivenCache(RivenManagerImpl rivenManagerImpl,Map dict,Map rivenPro)
    {
        ArrayList<Riven> rivenList = (ArrayList<Riven>) dict.get("Riven");
        rivenManagerImpl.putCache("rivenList",rivenList,0);
        rivenManagerImpl.putCache("property",rivenPro,0);
        CQ.logInfo("riven",JsonTool.ObjToJson(rivenList));
    }

    public void initRmCache(RmManagerImpl rmManagerImpl ,Map dict)
    {
        ArrayList<Map> dictList = (ArrayList<Map>) dict.get("Dict");
        ArrayList<Map> rivenList = (ArrayList<Map>) dict.get("Riven");
        for(Map item:rivenList)
        {
            rmManagerImpl.putCache(item.get("name").toString(),item.get("name").toString(),0L);
        }
        for(Map item:dictList)
        {
            if(item.get("zh").toString()!=null&&rmManagerImpl.isContains(item.get("zh").toString()))
            {
                rmManagerImpl.putCache(item.get("en").toString().toUpperCase().replace(" ",""),item.get("en").toString().replace(" ","_"),0L);
                rmManagerImpl.putCache(item.get("zh").toString().toUpperCase().replace(" ",""),item.get("en").toString().replace(" ","_"),0L);
            }
        }
        CQ.logInfo("rmManagerImpl:",JsonTool.ObjToJson(rmManagerImpl));
    }
    /**
     * 酷Q退出 (Type=1002)<br>
     * 本方法会在酷Q【主线程】中被调用。<br>
     * 无论本应用是否被启用，本函数都会在酷Q退出前执行一次，请在这里执行插件关闭代码。
     *
     * @return 请固定返回0，返回后酷Q将很快关闭，请不要再通过线程等方式执行其他代码。
     */
    public int exit() {

//        CQ.logInfo("ocrTest",ImageTool.ocrRiven("C:\\Users\\ws\\Pictures\\20181117220831.png"));
        cacheListener.exitLinsten();
        alertListener.exitLinsten();
        return 0;
    }

    /**
     * 应用已被启用 (Type=1003)<br>
     * 当应用被启用后，将收到此事件。<br>
     * 如果酷Q载入时应用已被启用，则在 {@link #startup startup}(Type=1001,酷Q启动) 被调用后，本函数也将被调用一次。<br>
     * 如非必要，不建议在这里加载窗口。
     *
     * @return 请固定返回0。
     */
    public int enable() {
        enable = true;
        return 0;
    }

    /**
     * 应用将被停用 (Type=1004)<br>
     * 当应用被停用前，将收到此事件。<br>
     * 如果酷Q载入时应用已被停用，则本函数【不会】被调用。<br>
     * 无论本应用是否被启用，酷Q关闭前本函数都【不会】被调用。
     *
     * @return 请固定返回0。
     */
    public int disable() {
        enable = false;
        return 0;
    }

    /**
     * 私聊消息 (Type=21)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType 子类型，11/来自好友 1/来自在线状态 2/来自群 3/来自讨论组
     * @param msgId   消息ID
     * @param fromQQ  来源QQ
     * @param msg     消息内容
     * @param font    字体
     * @return 返回值*不能*直接返回文本 如果要回复消息，请调用api发送<br>
     * 这里 返回  {@link IMsg#MSG_INTERCEPT MSG_INTERCEPT} - 截断本条消息，不再继续处理<br>
     * 注意：应用优先级设置为"最高"(10000)时，不得使用本返回值<br>
     * 如果不回复消息，交由之后的应用/过滤器处理，这里 返回  {@link IMsg#MSG_IGNORE MSG_IGNORE} - 忽略本条消息
     */
    public int privateMsg(int subType, int msgId, long fromQQ, String msg, int font) {
        // 这里处理消息
//        CQ.sendPrivateMsg(fromQQ, "你发送了这样的消息：" + msg + "\n来自Java插件");

        if(msg.equals(customSettings.get("delMsg").toString())&&cacheManagerImpl.getCacheDataByKey("delFlag_"+fromQQ)!=null)
        {
            CQ.logInfo("撤回消息",""+(int)cacheManagerImpl.getCacheDataByKey("delFlag_"+fromQQ));
            CQ.deleteMsg((Integer) cacheManagerImpl.getCacheDataByKey("delFlag_"+fromQQ));
            cacheManagerImpl.clearByKey("delFlag_"+fromQQ);
            CQ.sendPrivateMsg(fromQQ,customSettings.get("delTip").toString());
        }

        int thisMsgId = 0;

        String type=KeyWords.getKey(customKeys,msg);
        if(type!=null)
        {
            thisMsgId =CQ.sendPrivateMsg(fromQQ, CreateMsg.getMsg(customSettings,type));

        }
        if(msg.equals(customSettings.get("help")))
            thisMsgId =CQ.sendPrivateMsg(fromQQ, customSettings.get("tip").toString());
        if(MarketSearch.getWMSearchKey(msg.toLowerCase(),customSettings.get("wm").toString())!=null)
        {
            thisMsgId =CQ.sendPrivateMsg(fromQQ,MarketSearch.searchSaleList(MarketSearch.getWMSearchKey(msg.toLowerCase(),customSettings.get("wm").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg.toLowerCase(),customSettings.get("wm2").toString())!=null)
        {
            thisMsgId =CQ.sendPrivateMsg(fromQQ,MarketSearch.searchSaleList(MarketSearch.getWMSearchKey(msg.toLowerCase(),customSettings.get("wm2").toString())));
        }
        if(MarketSearch.getFirstSearchKey(msg,customSettings.get("wiki").toString())!=null)
        {
            thisMsgId =CQ.sendPrivateMsg(fromQQ,WikiSearch.createNewWikiMsg("warframe",MarketSearch.getWMSearchKey(msg,customSettings.get("wiki").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("ffwiki").toString())!=null)
        {
            thisMsgId =CQ.sendPrivateMsg(fromQQ,WikiSearch.createNewWikiMsg("ff14",MarketSearch.getWMSearchKey(msg,customSettings.get("wiki").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("qjwiki").toString())!=null)
        {
            thisMsgId =CQ.sendPrivateMsg(fromQQ,WikiSearch.createNewWikiMsg("qj",MarketSearch.getWMSearchKey(msg,customSettings.get("wiki").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("wfa").toString())!=null)
        {
            thisMsgId =CQ.sendPrivateMsg(fromQQ,new WfaSearch().createWfaMsg(MarketSearch.getWMSearchKey(msg,customSettings.get("wfa").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("sendGroup").toString())!=null&&isMasterQQ(fromQQ))
        {
            new GroupMsg().sendGroupMsg(MarketSearch.getWMSearchKey(msg,customSettings.get("sendGroup").toString()));
            CQ.sendPrivateMsg(fromQQ,"[" +MarketSearch.getWMSearchKey(msg,customSettings.get("sendGroup").toString())+"]\n已发送到全部群");
        }

        if(isMasterQQ(fromQQ))
        {
            if(cacheManagerImpl.getCacheDataByKey(fromQQ+"SendGroups")!=null)
            {
                new GroupMsg().sendGroupMsg(msg);
                CQ.sendPrivateMsg(fromQQ,"[" +msg+"]\n已发送到全部群");
                cacheManagerImpl.clearByKey(fromQQ+"SendGroups");
            }

            if(MarketSearch.getWMSearchKey(msg,customSettings.get("delRiven").toString())!=null)
            {
                String people = MarketSearch.getWMSearchKey(msg,customSettings.get("delRiven").toString());
                if(cacheManagerImpl.isContains("rivenCount_"+people))
                {
                    cacheManagerImpl.clearByKey("rivenCount_"+people);
                    cacheManagerImpl.clearByKey("rivenDetil_"+people);
                    CQ.sendPrivateMsg(fromQQ,people+"的紫卡已被重置");
                } else {
                    CQ.sendPrivateMsg(fromQQ,"["+people+"]没有获取紫卡或者["+people+"]不是正确的qq号");
                }
            }
        }

        if(msg.equals(customSettings.get("sendGroup").toString())&&isMasterQQ(fromQQ))
        {
            cacheManagerImpl.putCache(fromQQ+"SendGroups",fromQQ,0);
        }

        if(MarketSearch.getWMSearchKey(msg,customSettings.get("rename").toString())!=null&&isMasterQQ(fromQQ))
        {
            List<Group> groups = CQ.getGroupList();
            for(Group g:groups)
            {
                CQ.setGroupCard(g.getId(),CQ.getLoginQQ(),MarketSearch.getWMSearchKey(msg,customSettings.get("rename").toString()));
            }
            CQ.sendPrivateMsg(fromQQ,"群名片修改完成!");
        }

        if(MarketSearch.getAtKey(msg,customSettings.get("rivenProperty").toString())!=null)
        {
            String re=MarketSearch.getAtKey(msg,customSettings.get("rivenProperty").toString());
            String name = re.replaceAll("\\d+","").replace("+","").replace("-","").trim();
            int preNum=3;
            int s=0;
            boolean isMinus=false;
            for(int i=0;i<re.length();i++)
            {
                if(re.charAt(i)<='3'&&re.charAt(i)>='1')
                {
                    s++;
                    if(s==1)
                        preNum = (re.charAt(i)-'0');
                    if(s==2&&re.charAt(i)=='1')
                        isMinus=true;
                }
            }
            thisMsgId =CQ.sendPrivateMsg(fromQQ,new RivenCroe().getRivenResult(name,preNum,isMinus,8));
        }
        //获得紫卡
        if(msg.equals(customSettings.get("getRiven").toString()))
        {
            int rivenCount = (int) customSettings.get("rivenCount");
            LinkedHashMap<Riven,LinkedHashMap<String,Double>> rivenAndPropertys;

            //紫卡详情
            if(cacheManagerImpl.isContains("rivenDetil_"+fromQQ))
            {
                rivenAndPropertys = (LinkedHashMap<Riven, LinkedHashMap<String, Double>>) cacheManagerImpl.getCacheDataByKey("rivenDetil_"+fromQQ);
            } else {
                rivenAndPropertys = new LinkedHashMap<>();
            }

            //紫卡数量
            if(cacheManagerImpl.isContains("rivenCount_"+fromQQ))
            {
                rivenCount = (int) cacheManagerImpl.getCacheDataByKey("rivenCount_"+fromQQ);
                if(cacheManagerImpl.isContains("rivenDetil_"+fromQQ))
                {
                    int hasRivenCount = rivenAndPropertys.size();
                    rivenCount -= hasRivenCount;
                }
            }else {
                cacheManagerImpl.putCache("rivenCount_"+fromQQ,rivenCount,getTomorrowZeroSeconds());
            }

            if(rivenCount<=0)
            {
                thisMsgId = CQ.sendPrivateMsg(fromQQ,"今日紫卡已上限");
            }
            else {
                for(int i=0;i<rivenCount;i++)
                {
                    Riven r = new RivenCroe().getRandomRiven();
                    rivenAndPropertys.put(r,null);
                }
                cacheManagerImpl.putCache("rivenDetil_"+fromQQ,rivenAndPropertys,getTomorrowZeroSeconds());
                thisMsgId =CQ.sendPrivateMsg(fromQQ,"你获得了"+rivenCount+"张紫卡:"+getVeiledRivenMsg((rivenAndPropertys)));
            }
        }

        //模拟开卡
        if(msg.equals(customSettings.get("openRiven").toString()))
        {
            if(cacheManagerImpl.isContains("rivenCount_"+fromQQ)&&cacheManagerImpl.isContains("rivenDetil_"+fromQQ))
            {
                Riven flag = null;
                LinkedHashMap<Riven,LinkedHashMap<String,Double>> rivenAndPropertys = (LinkedHashMap<Riven, LinkedHashMap<String, Double>>) cacheManagerImpl.getCacheDataByKey("rivenDetil_"+fromQQ);
                for(Riven r:rivenAndPropertys.keySet())
                {
                    if(rivenAndPropertys.get(r)==null)
                        flag = r;
                }
                if(flag != null)
                {
                    LinkedHashMap<String,Double> opendRivenPropertys = new RivenCroe().getRendomResult(flag);
                    rivenAndPropertys.put(flag,opendRivenPropertys);
                    thisMsgId =CQ.sendPrivateMsg(fromQQ,"你打开了一张"+zhRivenType(flag.getType())+",开出了:\n"+flag.getName()+RivenTool.yourRivenString(opendRivenPropertys));
                } else {
                    thisMsgId =CQ.sendPrivateMsg(fromQQ,"你今天份的紫卡都开完了，明天再来吧");
                }

            }
            else {
                thisMsgId =CQ.sendPrivateMsg(fromQQ,"这里没有你的紫卡哦");
            }
        }

        if(MarketSearch.getWMSearchKey(msg,customSettings.get("subscribe").toString())!=null)
        {
            String goods =MarketSearch.getWMSearchKey(msg,customSettings.get("subscribe").toString());
            doSub(goods,null,fromQQ,true);
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("unsubscribe").toString())!=null)
        {
            String goods =MarketSearch.getWMSearchKey(msg,customSettings.get("unsubscribe").toString());
            doSub(goods,null,fromQQ,false);
        }
        if(msg.equals(customSettings.get("showSubscribe").toString()))
        {
            CQ.sendPrivateMsg(fromQQ,new SubscribeMsg(sqlSession).getUserSubs(fromQQ));
        }

        if(MarketSearch.getWMSearchKey(msg,customSettings.get("rm").toString())!=null)
        {
            String weapon =MarketSearch.getWMSearchKey(msg,customSettings.get("rm").toString());
            weapon = weapon.toUpperCase().replace(" ","");
            CQ.sendPrivateMsg(fromQQ,createRivenMarketMsg(rmManagerImpl.getEnByZh(weapon),rmProperty));
        }
        //刷新内存



        if(thisMsgId>0)
            cacheManagerImpl.putCache("delFlag_"+fromQQ,thisMsgId,60*2*1000);


        return MSG_IGNORE;
    }

    /**
     * 群消息 (Type=2)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType       子类型，目前固定为1
     * @param msgId         消息ID
     * @param fromGroup     来源群号
     * @param fromQQ        来源QQ号
     * @param fromAnonymous 来源匿名者
     * @param msg           消息内容
     * @param font          字体
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupMsg(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg,
                        int font) {
        // 如果消息来自匿名者
        if (fromQQ == 80000000L && !fromAnonymous.equals("")) {
            // 将匿名用户信息放到 anonymous 变量中
            Anonymous anonymous = CQ.getAnonymous(fromAnonymous);
        }
        msg = CQCode.decode(msg);
//        if(MarketSearch.getWMSearchKey(msg,customSettings.get("development").toString())!=null)
//        {
//            msg = MarketSearch.getWMSearchKey(msg,customSettings.get("development").toString());
//            String deMsg= CQCode.decode(msg);
//            CQ.logInfo("deCode",deMsg);
//            CQ.logInfo("enCode",CQCode.encode(deMsg,true));
//            CQ.sendGroupMsg(fromGroup,"得到的MSG："+msg);
//            CQ.sendGroupMsg(fromGroup,"deCode的MSG:"+deMsg);
//            CQ.sendGroupMsg(fromGroup,"deCode的MSG:\n"+CQCode.encode(deMsg,true)+"\n"+CQCode.encode(deMsg,false));
//        }

        if(msg.equals(customSettings.get("delMsg").toString())&&cacheManagerImpl.getCacheDataByKey("groupDelFlag_"+fromGroup)!=null)
        {

            CQ.deleteMsg((Integer) cacheManagerImpl.getCacheDataByKey("groupDelFlag_"+fromGroup));
            cacheManagerImpl.clearByKey("groupDelFlag_"+fromGroup);
            CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+customSettings.get("delTip").toString());
        }

        int thisMsgId = 0;

        // 解析CQ码案例 如：[CQ:at,qq=100000]
        // 解析CQ码 常用变量为 CC(CQCode) 此变量专为CQ码这种特定格式做了解析和封装
        // CC.analysis();// 此方法将CQ码解析为可直接读取的对象
        // 解析消息中的QQID
        //long qqId = CC.getAt(msg);// 此方法为简便方法，获取第一个CQ:at里的QQ号，错误时为：-1000
        //List<Long> qqIds = CC.getAts(msg); // 此方法为获取消息中所有的CQ码对象，错误时返回 已解析的数据
        // 解析消息中的图片
        //CQImage image = CC.getCQImage(msg);// 此方法为简便方法，获取第一个CQ:image里的图片数据，错误时打印异常到控制台，返回 null
        //List<CQImage> images = CC.getCQImages(msg);// 此方法为获取消息中所有的CQ图片数据，错误时打印异常到控制台，返回 已解析的数据

        // 这里处理消息

//        CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "你发送了这样的消息：" + msg + "\n来自Java插件");

        String type=KeyWords.getKey(customKeys,msg);
        if(type!=null)
        {
            thisMsgId = CQ.sendGroupMsg(fromGroup, CreateMsg.getMsg(customSettings,type));
        }

        if(msg.equals(customSettings.get("caches"))&&isMasterQQ(fromQQ))
            thisMsgId = CQ.sendGroupMsg(fromGroup, JsonTool.ObjToJson(cacheManagerImpl.getCacheAll()));

        if(msg.equals(customSettings.get("help")))
            thisMsgId = CQ.sendGroupMsg(fromGroup, customSettings.get("tip").toString());

        if(MarketSearch.getWMSearchKey(msg.toLowerCase(),customSettings.get("wm").toString())!=null)
        {
            thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) + MarketSearch.searchSaleList(MarketSearch.getWMSearchKey(msg.toLowerCase(),customSettings.get("wm").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg.toLowerCase(),customSettings.get("wm2").toString())!=null)
        {
            thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) + MarketSearch.searchSaleList(MarketSearch.getWMSearchKey(msg.toLowerCase(),customSettings.get("wm2").toString())));
        }
        if(MarketSearch.getFirstSearchKey(msg,customSettings.get("wiki").toString())!=null)
        {
            thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) +WikiSearch.createNewWikiMsg("warframe",MarketSearch.getWMSearchKey(msg,customSettings.get("wiki").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("ffwiki").toString())!=null)
        {
            thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) +WikiSearch.createNewWikiMsg("ff14",MarketSearch.getWMSearchKey(msg,customSettings.get("wiki").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("qjwiki").toString())!=null)
        {
            thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) +WikiSearch.createNewWikiMsg("qj",MarketSearch.getWMSearchKey(msg,customSettings.get("wiki").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("image").toString())!=null) {
            thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) +"\n"+getImage(msg) );
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("wfa").toString())!=null)
        {
            thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) +new WfaSearch().createWfaMsg(MarketSearch.getWMSearchKey(msg,customSettings.get("wfa").toString())));
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("sendGroup").toString())!=null)
        {
            if(isMasterQQ(fromQQ))
            {
                new GroupMsg().sendGroupMsg(MarketSearch.getWMSearchKey(msg,customSettings.get("sendGroup").toString()));
                CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"[" +MarketSearch.getWMSearchKey(msg,customSettings.get("sendGroup").toString())+"]\n已发送到全部群");
            } else {
                CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+" 身份校验失败，你无权使用此功能。");
            }
        }

        if(isMasterQQ(fromQQ))
        {
            //发送全部群
            if(cacheManagerImpl.getCacheDataByKey(fromQQ+"SendGroups"+fromGroup)!=null)
            {
                new GroupMsg().sendGroupMsg(msg);
                CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"[" +msg+"]\n已发送到全部群");
                cacheManagerImpl.clearByKey(fromQQ+"SendGroups"+fromGroup);
            }
            if(MarketSearch.getAtKey(msg,customSettings.get("delRiven").toString())!=null)
            {
                List<Long> peoples = new CQCode().getAts(msg);
                for(long q:peoples)
                {
                    if(cacheManagerImpl.isContains("rivenCount_"+q))
                    {
                        cacheManagerImpl.clearByKey("rivenCount_"+q);
                        cacheManagerImpl.clearByKey("rivenDetil_"+q);
                    }
                }
                CQ.sendGroupMsg(fromGroup,"以上同学的每日紫卡已刷新");
            }

        }

        if(msg.equals(customSettings.get("sendGroup").toString())&&isMasterQQ(fromQQ))
        {
            cacheManagerImpl.putCache(fromQQ+"SendGroups"+fromGroup,fromGroup,0);
        }

//        if(MarketSearch.getWMSearchKey(msg,customSettings.get("rename").toString())!=null&&isMasterQQ(fromQQ))
//        {
//            List<Group> groups = CQ.getGroupList();
//            for(Group g:groups)
//            {
//                CQ.setGroupCard(g.getId(),CQ.getLoginQQ(),MarketSearch.getWMSearchKey(msg,customSettings.get("rename").toString()));
//            }
//            CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) +"群名片修改完成");
//        }

        if(msg.equals(customSettings.get("draw").toString()))
        {
            if(CQ.getGroupMemberInfo(fromGroup,CQ.getLoginQQ()).getAuthority()>1)
            {
                if(CQ.getGroupMemberInfo(fromGroup,fromQQ).getAuthority()>1)
                {
                    CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)  +" 管理狗无权抽奖（自裁吧");
                }
                else {
                    int t = new Random().nextInt(10*60);
                    CQ.setGroupBan(fromGroup,fromQQ,t);
                    CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)  +"恭喜您，套餐已生效");
                }
            }
            else {
                CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) +customSettings.get("robotName").toString()+" 没有管理权限，无法抽奖,如有需要请联系群主。");
            }
        }
        if(MarketSearch.getAtKey(msg,customSettings.get("persecute").toString())!=null)
        {
            if(CQ.getGroupMemberInfo(fromGroup,CQ.getLoginQQ()).getAuthority()>1)
            {
                if(CQ.getGroupMemberInfo(fromGroup,fromQQ).getAuthority()>1)
                {
                    CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)  +"管理无法介入");
                }
                else {
                    new DrawCore().draw(msg,fromGroup,fromQQ);
                }
            }
            else {
                CQ.sendGroupMsg(fromGroup,CC.at(fromQQ) +customSettings.get("robotName").toString()+"没有管理权限，如有需要政策扶持，请联系群主。");
            }
        }
        /**
         * //获得紫卡
         *         if(msg.equals(customSettings.get("getRiven").toString()))
         *         {
         *             int rivenCount = (int) customSettings.get("rivenCount");
         *             LinkedHashMap<Riven,LinkedHashMap<String,Double>> rivenAndPropertys;
         *
         *             //紫卡详情
         *             if(cacheManagerImpl.isContains("rivenDetil_"+fromQQ))
         *             {
         *                 rivenAndPropertys = (LinkedHashMap<Riven, LinkedHashMap<String, Double>>) cacheManagerImpl.getCacheDataByKey("rivenDetil_"+fromQQ);
         *             } else {
         *                 rivenAndPropertys = new LinkedHashMap<>();
         *             }
         *
         *             //紫卡数量
         *             if(cacheManagerImpl.isContains("rivenCount_"+fromQQ))
         *             {
         *                 rivenCount = (int) cacheManagerImpl.getCacheDataByKey("rivenCount_"+fromQQ);
         *                 if(cacheManagerImpl.isContains("rivenDetil_"+fromQQ))
         *                 {
         *                     int hasRivenCount = rivenAndPropertys.size();
         *                     rivenCount -= hasRivenCount;
         *                 }
         *             }else {
         *                 cacheManagerImpl.putCache("rivenCount_"+fromQQ,rivenCount,getTomorrowZeroSeconds());
         *             }
         *
         *             if(rivenCount<=0)
         *             {
         *                 thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"今日紫卡已上限");
         *             }
         *             else {
         *                 for(int i=0;i<rivenCount;i++)
         *                 {
         *                     Riven r = new RivenCroe().getRandomRiven();
         *                     rivenAndPropertys.put(r,null);
         *                 }
         *                 cacheManagerImpl.putCache("rivenDetil_"+fromQQ,rivenAndPropertys,getTomorrowZeroSeconds());
         *                 thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"你获得了"+rivenCount+"张紫卡:"+getVeiledRivenMsg((rivenAndPropertys)));
         *             }
         *         }
         *
         *         //模拟开卡
         *         if(msg.equals(customSettings.get("openRiven").toString()))
         *         {
         *             if(cacheManagerImpl.isContains("rivenCount_"+fromQQ)&&cacheManagerImpl.isContains("rivenDetil_"+fromQQ))
         *             {
         *                 Riven flag = null;
         *                 LinkedHashMap<Riven,LinkedHashMap<String,Double>> rivenAndPropertys = (LinkedHashMap<Riven, LinkedHashMap<String, Double>>) cacheManagerImpl.getCacheDataByKey("rivenDetil_"+fromQQ);
         *                 for(Riven r:rivenAndPropertys.keySet())
         *                 {
         *                     if(rivenAndPropertys.get(r)==null)
         *                         flag = r;
         *                 }
         *                 if(flag != null)
         *                 {
         *                     LinkedHashMap<String,Double> opendRivenPropertys = new RivenCroe().getRendomResult(flag);
         *                     rivenAndPropertys.put(flag,opendRivenPropertys);
         *                     thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"你打开了一张"+zhRivenType(flag.getType())+",开出了:\n"+flag.getName()+RivenTool.yourRivenString(opendRivenPropertys));
         *                 } else {
         *                     thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"你今天份的紫卡都开完了，明天再来吧");
         *                 }
         *
         *             }
         *             else {
         *                 thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"这里没有你的紫卡哦");
         *             }
         *         }
         */


        if(MarketSearch.getAtKey(msg,customSettings.get("renameUser").toString())!=null&&CC.getAt(msg)==CQ.getLoginQQ())
        {
            Rename rn= renameMapper.selectByPrimaryKey(fromQQ);
            String newName = MarketSearch.getAtKey(msg,customSettings.get("renameUser").toString());
            if(rn!=null)
            {
                rn.setUsername(newName);
                int res = renameMapper.updateByPrimaryKeySelective(rn);
            } else {
                rn = new Rename();
                rn.setQq( fromQQ);
                rn.setUsername(newName);
                rn.setRobotname(customSettings.get("robotName").toString());
                int res = renameMapper.insert(rn);
            }
            //sqlSession.commit();
            CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"以后就叫你"+newName+"啦");
        }

        if(MarketSearch.getWMSearchKey(msg,customSettings.get("subscribe").toString())!=null)
        {
            String goods =MarketSearch.getWMSearchKey(msg,customSettings.get("subscribe").toString());
            doSub(goods,fromGroup,fromQQ,true);
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("unsubscribe").toString())!=null)
        {
            String goods =MarketSearch.getWMSearchKey(msg,customSettings.get("unsubscribe").toString());
            doSub(goods,fromGroup,fromQQ,false);
        }
        if(msg.equals(customSettings.get("showSubscribe").toString()))
        {
            CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+new SubscribeMsg(sqlSession).getUserSubs(fromQQ));
        }
        if(MarketSearch.getWMSearchKey(msg,customSettings.get("rm").toString())!=null)
        {
            String weapon =MarketSearch.getWMSearchKey(msg,customSettings.get("rm").toString());
            weapon = weapon.toUpperCase().replace(" ","");
            thisMsgId = CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+createRivenMarketMsg(rmManagerImpl.getEnByZh(weapon),rmProperty));
        }

        if(MarketSearch.getWMSearchKey(msg,customSettings.get("alias").toString())!=null)
        {
            String alias =MarketSearch.getWMSearchKey(msg,customSettings.get("alias").toString());
            CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+nameToProperty(alias,rmProperty));
        }

        if(MarketSearch.getAtKey(msg,customSettings.get("ocrRiven").toString())!=null)
        {
            CQImage image =CC.getCQImage(MarketSearch.getAtKey(msg,customSettings.get("ocrRiven").toString()));
//            CQ.logInfo("识别",JsonTool.ObjToJson(image));
            if(image!=null)
            {
                try {
                    CQ.sendGroupMsg(fromGroup,ImageTool.ocrRiven(image.getBytes()));
                } catch (IOException e) {  }
            }

        }

        if(thisMsgId>0)
            cacheManagerImpl.putCache("groupDelFlag_"+fromGroup,thisMsgId,60*2*1000);

        return MSG_IGNORE;
    }

//    public void dealMsgMatch(long fromGroup, long fromQQ, String msg,boolean isGroupMsg){
//        String reMsg="";
//        switch (msg){
//            case :
//        }
//    }

    public void doSub(String goods,Long fromGroup,Long fromQQ,boolean type)
    {
        /**
         * do something
         */
        String reType =type?"订阅":"退订";
        String re = new SubscribeMsg().createSubKey(goods);
        String reMsg = re==null?reType+new SubscribeMsg(sqlSession).doSubscribe(goods,fromQQ,type)+"成功":re;
        if(fromGroup==null)
        {   CQ.sendPrivateMsg(fromQQ,reMsg);    }
        else {  CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+reMsg);    }
    }

    public boolean isMasterQQ(Long qq)
    {
        ArrayList<Integer> masterQQs = (ArrayList<Integer>) customSettings.get("masterQQ");
        for(long mq:masterQQs)
        {
            if(qq.longValue()==mq)
            {
                return true;
            }
        }
        return false;
    }

    public void sendToMaster(String msg)
    {
        ArrayList<Integer> masterQQs = (ArrayList<Integer>) customSettings.get("masterQQ");
        for(long mq:masterQQs)
        {
            CQ.sendPrivateMsg(mq,msg);
        }
    }

    public String getImage(String msg)
    {
        String url =MarketSearch.getWMSearchKey(msg,customSettings.get("image").toString());
        CQCode cqCode = new CQCode();
        String image="";
        try {
            image =cqCode.image(new File(new ImageTool().ImageCreate(
                    customSettings.get("exe").toString(),
                    customSettings.get("js").toString(),
                    url,
                    UUID.randomUUID().toString()+".png"
            )));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    /**
     * 讨论组消息 (Type=4)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype     子类型，目前固定为1
     * @param msgId       消息ID
     * @param fromDiscuss 来源讨论组
     * @param fromQQ      来源QQ号
     * @param msg         消息内容
     * @param font        字体
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */

    public int discussMsg(int subtype, int msgId, long fromDiscuss, long fromQQ, String msg, int font) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 群文件上传事件 (Type=11)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType   子类型，目前固定为1
     * @param sendTime  发送时间(时间戳)// 10位时间戳
     * @param fromGroup 来源群号
     * @param fromQQ    来源QQ号
     * @param file      上传文件信息
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupUpload(int subType, int sendTime, long fromGroup, long fromQQ, String file) {
        GroupFile groupFile = CQ.getGroupFile(file);
        if (groupFile == null) { // 解析群文件信息，如果失败直接忽略该消息
            return MSG_IGNORE;
        }
        // 这里处理消息
        return MSG_IGNORE;
    }

    /**
     * 群事件-管理员变动 (Type=101)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/被取消管理员 2/被设置管理员
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param beingOperateQQ 被操作QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupAdmin(int subtype, int sendTime, long fromGroup, long beingOperateQQ) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 群事件-群成员减少 (Type=102)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/群员离开 2/群员被踢
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ(仅子类型为2时存在)
     * @param beingOperateQQ 被操作QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupMemberDecrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 群事件-群成员增加 (Type=103)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/管理员已同意 2/管理员邀请
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ(即管理员QQ)
     * @param beingOperateQQ 被操作QQ(即加群的QQ)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupMemberIncrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 好友事件-好友已添加 (Type=201)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype  子类型，目前固定为1
     * @param sendTime 发送时间(时间戳)
     * @param fromQQ   来源QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int friendAdd(int subtype, int sendTime, long fromQQ) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 请求-好友添加 (Type=301)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype      子类型，目前固定为1
     * @param sendTime     发送时间(时间戳)
     * @param fromQQ       来源QQ
     * @param msg          附言
     * @param responseFlag 反馈标识(处理请求用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int requestAddFriend(int subtype, int sendTime, long fromQQ, String msg, String responseFlag) {
        // 这里处理消息

        /**
         * REQUEST_ADOPT 通过
         * REQUEST_REFUSE 拒绝
         */

        // CQ.setFriendAddRequest(responseFlag, REQUEST_ADOPT, null); // 同意好友添加请求
        return MSG_IGNORE;
    }

    /**
     * 请求-群添加 (Type=302)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype      子类型，1/他人申请入群 2/自己(即登录号)受邀入群
     * @param sendTime     发送时间(时间戳)
     * @param fromGroup    来源群号
     * @param fromQQ       来源QQ
     * @param msg          附言
     * @param responseFlag 反馈标识(处理请求用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int requestAddGroup(int subtype, int sendTime, long fromGroup, long fromQQ, String msg,
                               String responseFlag) {
        // 这里处理消息

        /**
         * REQUEST_ADOPT 通过
         * REQUEST_REFUSE 拒绝
         * REQUEST_GROUP_ADD 群添加
         * REQUEST_GROUP_INVITE 群邀请
         */
		/*if(subtype == 1){ // 本号为群管理，判断是否为他人申请入群
			CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_ADD, REQUEST_ADOPT, null);// 同意入群
		}*/
		if(subtype == 2){
			CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_INVITE, REQUEST_REFUSE, "群数量已达上限啦！");// 同意进受邀群
            CQ.logInfo("群邀请","已拒绝");
		}

        return MSG_IGNORE;
    }

    /**
     * 本函数会在JCQ【线程】中被调用。
     *
     * @return 固定返回0
     */
    public int menuA() {
        JOptionPane.showMessageDialog(null, "这是测试菜单A，可以在这里加载窗口");
        return 0;
    }

    /**
     * 本函数会在酷Q【线程】中被调用。
     *
     * @return 固定返回0
     */
    public int menuB() {
        JOptionPane.showMessageDialog(null, "这是测试菜单B，可以在这里加载窗口");
        return 0;
    }

}
