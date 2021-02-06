package com.myutils.hutool;

import cn.hutool.Hutool;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.*;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.myutils.utils.Employee;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.Data;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@SpringBootTest
public class HuToolest {

    /**
     * 打印所有工具类
     */
    @Test
    public void testGetAllUtils(){
        Hutool.printAllUtils();
    }

    /**
     * 日期工具类
     */
    @Test
    public void testDateUtil() {
        /**
         * yyyy-MM-dd HH:mm:ss
         * yyyy-MM-dd
         * HH:mm:ss
         * yyyy-MM-dd HH:mm
         * yyyy-MM-dd HH:mm:ss.SSS
         */
        String dateStr1 = "2017-03-01";
        Date date2 = DateUtil.parse(dateStr1);
        /**
         * 格式化日期输出
         */
        String dateStr = "2021-01-28";
        Date dateFormat = DateUtil.parse(dateStr);
        //结果 2021/01/26
        String format = DateUtil.format(dateFormat, "yyyy/MM/dd");
        //常用格式的格式化，结果：2021-01-28
        String formatDate = DateUtil.formatDate(dateFormat);
        //结果：2021-01-28 00:00:00
        String formatDateTime = DateUtil.formatDateTime(dateFormat);
        //结果：00:00:00
        String formatTime = DateUtil.formatTime(dateFormat);
        /**
         * 获取Date对象的某个部分
         */
        Date datePart = DateUtil.date();
        //获得年的部分
        System.out.println("年:"+DateUtil.year(datePart));
        //获得月份，从0开始计数
        System.out.println("月:"+DateUtil.month(datePart));
        //获得月份枚举
        System.out.println("日:"+DateUtil.dayOfMonth(datePart));
        /**
         * 日期时间偏移
         */
        //昨天
        DateTime yesterday = DateUtil.yesterday();
        System.out.println("昨天:" + yesterday);
        //明天
        System.out.println("明天:"+DateUtil.tomorrow());
        //上周
        System.out.println("上周:"+DateUtil.lastWeek());
        //下周
        System.out.println("下周:"+DateUtil.nextWeek());
        //上个月
        System.out.println("上个月:"+DateUtil.lastMonth());
        //下个月
        System.out.println("下个月:"+DateUtil.nextMonth());
        /**
         * 开始 和 结束时间
         */
        String dateStr2 = "2021-01-28 11:04:44";
        Date date = DateUtil.parse(dateStr2);
        //一天的开始，结果：2021-01-28 00:00:00
        Date beginOfDay = DateUtil.beginOfDay(date);
        System.out.println("一天的开始: "+beginOfDay);
        //一天的结束，结果：2021-01-28 23:59:59
        Date endOfDay = DateUtil.endOfDay(date);
        System.out.println("一天的结束: "+endOfDay);
        /**
         * 时间区间
         */
        String start = "2021-01-28";
        String end = "2021-02-09";
        List<DateTime> dateTimeList = DateUtil.rangeToList(DateUtil.parse(start), DateUtil.parse(end), DateField.DAY_OF_MONTH);
        dateTimeList.stream().forEach(System.out::println);
    }

    /**
     * 1.8的LocalDateTime
     */
    @Test
    public void testLocalDateTimeUtils(){
        System.out.println(
                "现在:" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss")
        );
        System.out.println(
                "每天吃饭的点:" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd 11:58:00")
        );
        System.out.println(
                "距离下班还有:" + LocalDateTimeUtil.between(LocalDateTime.now(),LocalDateTime.of(2021,1,28,18,30,0), ChronoUnit.SECONDS)+"s"
        );
    }

    /**
     * 原始写法(浅克隆):实现Cloneable接口
     */
    @Data
    private static class Cat implements Cloneable{
        private String name = "miaomiao";
        private int age = 2;
        private Map map = new HashMap();

        @Override
        public Cat clone() {
            try {
                return (Cat) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new CloneRuntimeException(e);
            }
        }
    }
    /**
     * 克隆(深克隆):继承CloneSupport
     */
    @Data
    private static class Dog extends CloneSupport<Dog> {
        private String name = "wangwang";
        private int age = 3;
    }

    /**
     * 对java.util.Objects{@link Objects}的进一步封装;
     */
    @Test
    public void testObjectUtil(){
        //Objects的克隆
        Cat cat = new Cat();
        Cat cat1 = cat.clone();
        //ObjectUtil的深克隆
        Cat cat2 = ObjectUtil.cloneByStream(cat);

        //判断非空
        System.out.println(ObjectUtil.isNull(null));//true
        System.out.println(ObjectUtil.isNull(""));//false
        System.out.println(ObjectUtil.isNull(" "));//false
        System.out.println(ObjectUtil.isEmpty(null));//true
        System.out.println(ObjectUtil.isEmpty(""));//true
        System.out.println(ObjectUtil.isEmpty(" "));//false
        System.out.println(ObjectUtil.equals("", " "));//false
        System.out.println(ObjectUtil.isBasicType(" "));//false
    }

    /**
     * 加密解密工具:
     * 对称加密（symmetric），例如：AES、DES等
     * 非对称加密（asymmetric），例如：RSA、DSA等
     * 摘要加密（digest），例如：MD5、SHA-1、SHA-256、HMAC等
     */
    @Test
    public void testSecureUtil(){
        System.out.println("MD5:"+SecureUtil.md5("oyqj"));
        System.out.println("SHA256:"+SecureUtil.sha256("oyqj"));
    }

    /**
     * 防止 xss 注入和 SQL 注入
     */
    @Test
    public void testHtmlUtil(){
        //转义HTML特殊字符
        System.out.println(HtmlUtil.escape("<html><body>123'123'</body></html>"));
        //清除所有 HTML 标签
        System.out.println("----------");
        System.out.println(HtmlUtil.cleanHtmlTag("pre<div class=\"test_div\">\r\n\t\tdfdsfdsfdsf\r\n</div><div class=\"test_div\">BBBB</div>"));
        System.out.println("----------");
        System.out.println(buildSafeStr("pre<div class=\"test_div\">\r\n\t\tdfdsfdsfdsf\r\n</div><div class=\"test_div\">BBBB</div>"));
        System.out.println("----------");

        //过滤 HTML 文本，防止 XSS 攻击
        System.out.println(HtmlUtil.filter("<alert></alert>"));
    }

    public static String buildSafeStr(String in) {
        return in == null ? null : in.replaceAll("(\\^)|(\\&)|(\\|)|(\\$)|(\\%)|(\\@)|(\\')|(\\\")|(\\>)|(\\<)|(\\))|(\\()|(\\\\)|(\\#|$)", "");
    }

    /**
     * 增强的StringUtils
     */
    @Test
    public void testStrUtil(){
        //字符串模板代替字符串拼接,slf4j
        System.out.println(StrUtil.format("this is {} for {}", "a", "b"));
        //截取: 从0开始,最后一个为-1, 左开右闭,不会越界报错
        String str = "abcdefgh";
        System.out.println(StrUtil.sub(str, 0, 3)); // abc
        System.out.println(StrUtil.sub(str, 1, 3)); // bc
        System.out.println(StrUtil.sub(str, 2, -3)); //cde
        System.out.println(StrUtil.sub(str, 3, 2)); //c
        System.out.println(StrUtil.sub(str, 3, -1)); //defg
        System.out.println(StrUtil.sub(str, 0, 9)); //abcdefgh
        //定义了很多常用字符
        System.out.println(StrUtil.UNDERLINE);
        System.out.println(StrUtil.DOT);
        System.out.println(StrUtil.COMMA);
        System.out.println(StrUtil.DASHED);
        System.out.println(StrUtil.EMPTY_JSON);
        //判断非空
        System.out.println(StrUtil.hasBlank(null));//true
        System.out.println(StrUtil.hasBlank(""));//true
        System.out.println(StrUtil.hasBlank(" "));//true
        System.out.println(StrUtil.hasBlank("null"));//false
        System.out.println(StrUtil.hasEmpty(null));//true
        System.out.println(StrUtil.hasEmpty(""));//true
        System.out.println(StrUtil.hasEmpty(" "));//false
        System.out.println(StrUtil.hasEmpty("null"));//false
    }

    /**
     * 切割字符串:
     * 分割限制分割数
     * 分割后每个字符串是否需要去掉两端空格
     * 是否忽略空白片
     * 根据固定长度分割
     * 通过正则分隔
     */
    @Test
    public void testStrSpliter(){
        String str1 = "a, ,efedsfs,   ddf";
        //参数：被切分字符串，分隔符逗号，0表示无限制分片数，去除两边空格，忽略空白项
        List<String> split = StrSpliter.split(str1, ',', 0, true, true);
        //正则切分
//        StrSpliter.splitByRegex(str1,"",2,true,true);
    }


    /**
     * xml文件的简单操作
     */
    @Test
    public void testXmlUtil(){
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:xmlFile/test.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //读取xml
        Document readXML = XmlUtil.readXML(file);
        System.out.println(readXML.getXmlVersion());
        //解析xml
        String str = ResourceUtil.readUtf8Str("xmlFile/test.xml");
        Document parseXml = XmlUtil.parseXml(str);
        System.out.println(parseXml.getXmlVersion());
        //创建xml
        Document document = XmlUtil.createXml();
        //xml转成文件
        XmlUtil.toFile(parseXml,"xmlFile/test2.xml");
    }

    /**
     * 资源快捷读取封装
     * @throws IOException
     */
    @Test
    public void testResourceUtil() throws IOException {
        /**
         * 原始写法:
          */
        //获取resource的单个文件
        ClassPathResource classPathResource = new ClassPathResource("xmlFile/test.xml");
        String str = IOUtils.toString(classPathResource.getInputStream(), Charset.defaultCharset());
        System.out.println("原始写法:"+str);
        System.out.println("=============");
        /**
         * ResourceUtil写法
         */
        String s = ResourceUtil.readUtf8Str("xmlFile/test.xml");
        File file1 = FileUtil.newFile("xmlFile/test.xml");
        System.out.println("ResourceUtil写法:"+s);

        /**
         * 获取resources下指定路径下的所有文件
         */
        Resource[] resources = new PathMatchingResourcePatternResolver()
                        .getResources(ResourceUtils.CLASSPATH_URL_PREFIX + "jsonFile/*.*");
        //保存文件路径
        String transPath = "./jsonFile/";
        for (Resource resource : resources) {
            //获取文件流
            InputStream inputStream = resource.getInputStream();
            //获取文件名
            String filename = resource.getFilename();
            //复制文件
            File file = new File(transPath+filename);
            System.out.println(file.getName());
            //对文件进行操作
        }
    }

    /**
     * 树结构: TreeUtil
     */
    @Test
    public void testTreeUtil(){
        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5));
        nodeList.add(new TreeNode<>("11", "1", "用户管理", 222222));
        nodeList.add(new TreeNode<>("111", "11", "用户添加", 0));
        nodeList.add(new TreeNode<>("2", "0", "店铺管理", 1));
        nodeList.add(new TreeNode<>("21", "2", "商品管理", 44));
        nodeList.add(new TreeNode<>("221", "2", "商品管理2", 2));
        // 0表示最顶层的id是0
        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");
        /**
         * 自定义字段名
         */
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("rid");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(nodeList, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getWeight());
                    tree.setName(treeNode.getName());
                    // 扩展属性 ...
                    tree.putExtra("extraField", 666);
                    tree.putExtra("other", new Object());
                });
        //获取ID对应的节点，如果有多个ID相同的节点，只返回第一个。此方法只查找此节点及子节点，采用递归深度优先遍历。
//        TreeUtil.getNode(null,"");
    }

    @Test
    public void testIdUtil(){
        //ObjectId是MongoDB数据库的一种唯一ID生成策略
        IdUtil.objectId();
        //uuid
        System.out.println("IdUtil.randomUUID() = " + IdUtil.randomUUID());//带-的
        System.out.println("IdUtil.simpleUUID() = " + IdUtil.simpleUUID());//不带-的
        System.out.println("IdUtil.fastUUID() = " + IdUtil.fastUUID());
        System.out.println("IdUtil.fastSimpleUUID() = " + IdUtil.fastSimpleUUID());
    }

    /**
     * 反射
     */
    @Test
    public void testReflectUtil(){
        //获取某个类的所有方法
        Method[] methods = ReflectUtil.getMethods(TestClass.class);
        //获取某个类的指定方法
        Method method = ReflectUtil.getMethod(TestClass.class, "getA");
        //构造对象
        ReflectUtil.newInstance(TestClass.class);
        //执行方法
        TestClass testClass = new TestClass();
        ReflectUtil.invoke(testClass, "setA", 10);
    }

    class TestClass {
        private int a;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

    /**
     * 文件工具类(方法命名,参考linux)
     */
    @Test
    public void testFileUtil(){
        //ls  touch  mkdir del(递归删除)  copy
        List<String> fileNames = FileUtil.listFileNames("jsonFile");
        File touch = FileUtil.touch(new File(""));
        File mkdir = FileUtil.mkdir("");
        boolean del = FileUtil.del("");
        FileUtil.copy("","",true);

    }

    /**
     * Validator字段验证器
     */
    @Test
    public void testValidator(){
        System.out.println(Validator.isEmail("123456@qq.com"));//true
        System.out.println(Validator.isCreditCode("45645654"));//false
        System.out.println(Validator.isChinese("我is华中科技"));//false
        System.out.println(Validator.hasChinese("我is华中科技"));//true
        System.out.println(Validator.isMobile("17158469552"));//true
        System.out.println(Validator.isPlateNumber("鄂"));//false
        //判断身份信息,大陆港澳台
        Validator.isCitizenId("");
        //自定义正则
        Validator.isMatchRegex("","");

        //验证正则
        Validator.validateMatchRegex("","","");
    }

    @Test
    public void testCollUtil(){
        ArrayList<String> listA = CollUtil.toList("1", "3", "4", "5");
        ArrayList<String> listB = CollUtil.toList("1", "2", "3");
        //截取,如果越界,返回空集合
        System.out.println(CollUtil.sub(listA, 0, 2));
        //并集
        System.out.println(CollUtil.union(listA, listB));
        //交集
        System.out.println(CollUtil.intersection(listA, listB));
        //差集
        System.out.println(CollUtil.disjunction(listA, listB));

    }

    /**
     * 拼音工具-PinyinUtil
     */
    @Test
    public void testPinyinUtil(){
        //获取拼音: "ni hao"
        String pinyin = PinyinUtil.getPinyin("你好", " ");
        //获取首字母: "h, s, d, y, g"
        String result = PinyinUtil.getFirstLetter("H是第一个", ", ");
    }


    /**
     * HttpUtil
     */
    @Test
    public void testHttpUtil(){
        /**
         * get
         */
        // 最简单的HTTP请求，可以自动通过header等信息判断编码，不区分HTTP和HTTPS
        String result1= HttpUtil.get("https://www.baidu.com");
        // 当无法识别页面编码的时候，可以自定义请求页面的编码
        String result2= HttpUtil.get("https://www.baidu.com", CharsetUtil.CHARSET_UTF_8);
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("city", "北京");
        String result3= HttpUtil.get("https://www.baidu.com", paramMap);

        /**
         * post
         */
        HashMap<String, Object> paramMap1 = new HashMap<>();
        paramMap1.put("city", "北京");
        String result= HttpUtil.post("https://www.baidu.com", paramMap1);
    }

    @Test
    public void testNumberUtil(){
        BigDecimal add = NumberUtil.add(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
        BigDecimal sub = NumberUtil.sub(BigDecimal.ZERO, BigDecimal.ONE);
        BigDecimal mul = NumberUtil.mul(BigDecimal.ZERO, BigDecimal.ONE);
        BigDecimal div = NumberUtil.div(BigDecimal.ZERO, BigDecimal.ONE,2, RoundingMode.CEILING);
        System.out.println(add);
        System.out.println(sub);
        System.out.println(mul);
        System.out.println(div);
        BigDecimal multiply = NumberUtil.add(BigDecimal.ONE).multiply(BigDecimal.TEN);
        System.out.println(multiply);
    }

    @Test
    public void testOther(){
        //json
        JSONUtil.isJson("");
        //cache
        FIFOCache<Object, Object> objects = CacheUtil.newFIFOCache(3);
        //fast convert
        String numberToChinese = Convert.numberToChinese(54, true);//数字转中文
        String digitToChinese = Convert.digitToChinese(32.24);//叁拾贰元贰角肆分
        System.out.println(numberToChinese);
        System.out.println(digitToChinese);
        //CaptchaUtil 生成验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(50, 80);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(80, 80);
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(50, 80);
        System.out.println(shearCaptcha.getCode());
        BufferedImage image = lineCaptcha.getImage();
        //bean
        Employee employee = new Employee();
        employee.setId(2);
        employee.setEmpName("oyqj");
        Map<String, Object> map = BeanUtil.beanToMap(employee);
        HashMap<String, Object> map1 = new HashMap<String, Object>() {{
            put("id", "2");
            put("empName", "oyqj");
        }};
        System.out.println(map);

        Employee employee1 = BeanUtil.mapToBean(map1, Employee.class, true, CopyOptions.create().ignoreCase());
        System.out.println(employee1);

        new Thread(()-> System.out.println("start")).start();

    }

    @Test
    public void test(){
        long thisTime = 1600330500000L;
        long beforeTime = 1600330200000L;
        double interval = (thisTime - beforeTime)/(60*60*1000);
        double interval1 = Double.valueOf(thisTime - beforeTime)/(60*60*1000);
        System.out.println(interval);
        System.out.println(interval1);
    }
    @Test
    public void test1(){
        String trim = StrUtil.trim(" 11   2 3 ");
        System.out.println(StrUtil.replace(" 112 3 ", " ", ""));
        System.out.println(trim);
    }


    public static void main(String args[]) {
        Thread t = new Thread() {
            public void run() {
                pong();
            }
        };
        t.start();
        System.out.print("ping");
    }

    static void pong() {
        System.out.print("pang");
    }



}
