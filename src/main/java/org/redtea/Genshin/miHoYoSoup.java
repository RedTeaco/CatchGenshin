package org.redtea.Genshin;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.redtea.EasyExcel.util.TestFileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.redtea.EasyExcel.read.readFromExcel.readData;
import static org.redtea.EasyExcel.write.WriteSave.writeToSave;
import static org.redtea.EasyExcel.write.WriteTest.writeToExcel;

//接受url,解析成实体列表并返回
public class miHoYoSoup {
    //private static final String FILENAME_TEST = getPath()+"/src/main/java/org/redtea/url.txt";
    private static final String FILENAME = getPath() + "/url.txt";
    private int[] resultIntList;
    private List<ItemEntity> resultList;
    private List<String> counts;
    public miHoYoSoup() {

    }

    private static String getURL() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }

            return sb.toString().split("#/log")[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getPath() {
        File file = new File("");
        return file.getAbsolutePath();
    }

    public static void main(String[] args) {
        miHoYoSoup miHoYoSoup = new miHoYoSoup();
        String url = getURL();
        try {
            Map<String, List<ItemEntity>> ItemEntityMap = miHoYoSoup.getAllItem(url);
            miHoYoSoup.postprocess(ItemEntityMap);
            System.out.println(Arrays.toString(miHoYoSoup.getResultIntList()));
            System.out.println(miHoYoSoup.getResultList());
        } catch (NullPointerException e) {
            System.out.println("当前url已过期，请点击游戏内的历史记录后重新运行。");
        }

    }

    public int[] getResultIntList() {
        return resultIntList;
    }

    public List<ItemEntity> getResultList() {
        return resultList;
    }

    public List<String> getCounts() {
        return counts;
    }

    public void miHoYoSoupGetLogs() {
        String url = getURL();
        //System.out.println(url);
        try {
            Map<String, List<ItemEntity>> ItemEntityMap = getAllItem(url);
            postprocess(ItemEntityMap);
/*            System.out.println(Arrays.toString(getResultIntList()));
            System.out.println(getResultList());*/
        } catch (NullPointerException e) {
            System.out.println("当前url已过期，请点击游戏内的历史记录后重新运行。");
        }
    }

    //从url获取数据并存储到map中
    public List<ItemEntity> parsejson(String url) {
        try {
            //String url = "https://hk4e-api.mihoyo.com/event/gacha_info/api/getGachaLog?win_mode=fullscreen&authkey_ver=1&sign_type=2&auth_appid=webview_gacha&init_type=301&gacha_id=e26ce9efdc5b0b3193edf2aa4736c04983a37a4d&timestamp=1664323648&lang=zh-cn&device_type=pc&game_version=CNRELWin3.1.0_R10676272_S10805493_D10772333&plat_type=pc&region=cn_qd01&authkey=FOabk8FmiZ8r80Xt0Hu1BF%2b5bTfOkAUxa2Sc4m7ETU9l%2fjxTFy2nb%2fDCZYstDl4amYRjLBGt2qhlfdyuqqM2eeZAwLhmr%2bjACGMiIuDjGH5sAKBJk9Ix1i8dLkiDg1LyhCPGPf5jUT74DPXNx8jtQ6HuYCHJtGLswOnv7pDh9e4iRK88xGGvYv6bKwG7y3mEzk5Jh4htvWVghSf54G4bFYebryxfwZvM4oKnIyjZ0kzq%2fP2RaEkImsb1wMkvaf%2f9xHEu3S8QP%2bTSNxs4nnHZAhlWdsa2%2bcq%2f%2bSBjNjgs%2bhdPVdwnr6FMY13azvVYhEOURCNDHx23MU9qXC9KEDEPFhaNZNuLv5hbqghnyoFbVzkPAy%2ffqvJY8MBqqBBPtyHphFqIDNTrcQ8CSKTp2qO%2bESqAD%2fu59IO2RasFQpFSvPF7EP4zPUJCzXlkkKypUg3nclx501mLLSuurla%2bzexDGJhqyxgPPRWNyc%2b1WL54dUGw%2fdQVdk4HArxTEwbil5yyEaPfnneun4Ed5uLUO%2bycENpMOL2LhYb4lVh777Rgl3pED92%2bJe7B9X2iq16%2fZAr27ZyU0xWfCKRz%2fxdmso4ZHFxsbJGwrzvZxxZD%2ffuprmteK5w4yixTF0CgBLDMgE8TOB3Sb2k%2fdRfqtp9BKW2Xx1XZ%2bhJz8chrygoslH6lOjQF0Cvg5Kvtg%2fmEha1aKQVjn%2fI0HnqP8yDsMm1vUQBrDamFbzLrliBREdqhs16Vsc6I6lB%2f0Ahi4RkZFd2DGACmtDXoLWwYvRxAuMIunYd7h0Au6PBus1V2PPbXRLX3%2fbOU4KDRMuc0rbGK9YbIX%2bHnr%2bLviqiTjpTSnf8BsRQqIRcfE2vz%2fuiojgBWrJFIHwDtcNBepv9vGP9hmHJrqtnCMBtidlXy8oIFqGGPZFQjslToI9voaSNQ8nTRf0YdNZaI8qHy9n25Qn1%2f2zIxBPKh9cPkwrayAYlbBBNKRkgFZjmwAOK94tjjzQnZFhwenZTgl4UVAPVP3VBsy4ThHGqkMYIHTBSM%2fU0l5Y3OHRbdN%2buca84tY2NQ%2bqVchGFS3Dw8Q%2bsnDYienJICm7%2b3%2fi64r%2bt3AmuZJYJ%2bSnrxaUVcvJRK%2bfTNvNSKCOgKj23sbSv0TpDTo%2f9R9oNlunFU37rb6x4ty5WiAZKxaJtGKey5i9%2bQbWlFnxsvubDUl%2bW4QcHQ1ZNn1Kkmt18X%2bBvKMhOvBtHHLkptnMMmlQCyEJHTC24zYCgfOAHbZRj5Mg9TPBL8sAey230foXNvH1SknUvI%2bd5L7XtuBl0C6R%2bgeuBEqy%2f5J2k0Y01xurDz4XO9gCZBQ8DKURkx3enSPBvlem8TiNEIWjocNIzZRHj5pIeFyA%3d%3d&game_biz=hk4e_cn&gacha_type=301&page=4&size=5&end_id=1662775560000290340";
            Document document = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", "gzip,deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("Content-Type", "application/x-javascript;charset=UTF-8")
                    .header("User-Agent", "Mozilla/5.0(Windows NT 6.1; WOW64;rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(10000).ignoreContentType(true).get();
            //System.out.println(document.body().text());
            String jsonstr = document.body().text();
            //将jason字符串转换为map
            Map<String, Object> map1 = (Map<String, Object>) JSON.parse(jsonstr);
            //获取data中的list信息
            Map<String, Object> map2 = (Map<String, Object>) JSON.parse(map1.get("data").toString());
            List<ItemEntity> itemEntityList = JSON.parseArray(map2.get("list").toString(), ItemEntity.class);
            return itemEntityList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //将url拼接成可查询的url
    private String getURLDiffType(String url) {
        StringBuilder sb = new StringBuilder();
        String base = "https://hk4e-api.mihoyo.com/event/gacha_info/api/getGachaLog?";
        sb.append(base);
        String attr = url.split("\\?")[1];
        String[] attrs = attr.split("#")[0].split("&");
        //拼接参数
        for (int i = 0; i < (attrs.length - 1); i++) {
            sb.append(attrs[i]);
            sb.append("&");
        }
        return sb.toString();
    }

    //从抽卡记录的url中提取出数据
    private Map<String, List<ItemEntity>> getAllItem(String url) {

        //100:新手祈愿，200:常驻祈愿，301:角色UP,302:武器UP
        String[] gachatypes = new String[]{"100", "200", "301", "302"};
        //定义一个Map存放四个卡池的数据
        Map<String, List<ItemEntity>> map = new LinkedHashMap<>();
        map.put("100", new ArrayList<>());
        map.put("200", new ArrayList<>());
        map.put("301", new ArrayList<>());
        map.put("302", new ArrayList<>());
        String baseurl = getURLDiffType(url);
        for (int i = 0; i < gachatypes.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(baseurl);
            sb.append("gacha_type=" + gachatypes[i] + "&");
            sb.append("page=1&");
            sb.append("size=20&");
            String baseurlplus = sb.toString();
            String end_id = "0";
            while (true) {
                StringBuilder str = new StringBuilder();
                str.append(baseurlplus);
                str.append("end_id=" + end_id);
                //System.out.println("查询卡池:"+gachatypes[i]+"\nend_id="+end_id);
                List<ItemEntity> itemEntityList = parsejson(str.toString());
                //如果查询到的数据为空表示查完了
                if (itemEntityList.size() == 0) {
                    System.out.println("卡池(" + gachatypes[i] + ")查询完毕");
                    if (i < 3) {
                        System.out.println("查询卡池" + gachatypes[i + 1] + "中...");
                    } else {
                        System.out.println();
                    }
                    break;
                }
                //将数据装到map里
                for (int j = 0; j < itemEntityList.size(); j++) {
                    map.get(gachatypes[i]).add(itemEntityList.get(j));
                }
                //得到最后一个元素的end_id
                end_id = itemEntityList.get(itemEntityList.size() - 1).getId();
                try {
                    //每个查询间隔一段时间
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;

    }

    //从保存的excel文件中读取出新的数据

    //对查询到的结果进行后处理操作
    private void postprocess(Map<String, List<ItemEntity>> ItemEntityMap) {
        //创建结果列表
        List<ItemEntity> resultList = new ArrayList<>();
        counts = new ArrayList<>();
        //首先取出四个卡池的信息
        List<ItemEntity> pool1 = ItemEntityMap.get("100");
        List<ItemEntity> pool2_new = ItemEntityMap.get("200");
        List<ItemEntity> pool3_new = ItemEntityMap.get("301");
        List<ItemEntity> pool4_new = ItemEntityMap.get("302");
        //pool2: list中的每一个数据都是ItemEntity
        //将时间顺序改为从先到后
        Collections.reverse(pool1);
        Collections.reverse(pool2_new);
        Collections.reverse(pool3_new);
        Collections.reverse(pool4_new);
        List<ItemEntity> pool2;
        List<ItemEntity> pool3;
        List<ItemEntity> pool4;

        //从先前的excel表中取出数据
        String filename = TestFileUtil.getPath() + "//data//" + pool2_new.get(0).getUid() + ".xlsx";
        File data_excel = new File(filename);
        if (data_excel.exists()) {
            Map<String, List<ItemEntity>> old_map = readData(filename);
            List<ItemEntity> pool2_old = old_map.get("200");
            List<ItemEntity> pool3_old = old_map.get("301");
            List<ItemEntity> pool4_old = old_map.get("302");
            pool2 = get_collects(pool2_old, pool2_new);
            pool3 = get_collects(pool3_old, pool3_new);
            pool4 = get_collects(pool4_old, pool4_new);
        } else {
            pool2 = pool2_new;
            pool3 = pool3_new;
            pool4 = pool4_new;
        }

        //统计所有数据
        int total_all = pool1.size() + pool2.size() + pool3.size() + pool4.size();
        //统计武器数量
        int total_weapon = 0;
        //统计角色数量
        int total_characters = 0;
        //统计3星数量
        int total_third_star = 0;
        //统计4星数量
        int total_fourth_star = 0;
        //统计5星数量
        int total_fifth_star = 0;

        //遍历四个卡池得到数据
        writeToExcel(pool1, pool2, pool3, pool4);
        writeToSave(pool1, pool2, pool3, pool4);

        int j = 0;
        int resultnum = 0;
        for (List<ItemEntity> itemEntities : Arrays.asList(pool1, pool2, pool3, pool4)) {
            int num = 0;
            String pools = "新手池 常驻池 角色池 武器池".split(" ")[j];
            j++;
            for (ItemEntity item : itemEntities) {
                if (item.getItem_type().equals("武器")) {
                    total_weapon++;
                }
                if (item.getItem_type().equals("角色")) {
                    total_characters++;
                }
                if (item.getRank_type().equals("3")) {
                    total_third_star++;
                }
                if (item.getRank_type().equals("4")) {
                    total_fourth_star++;
                }
                if (item.getRank_type().equals("5")) {
                    total_fifth_star++;
                    String pool = switch (item.getGacha_type()) {
                        case "100" -> "新手池";
                        case "200" -> "常驻池";
                        case "301", "400" -> "角色池";
                        case "302" -> "武器池";
                        default -> "";
                    };
                    num += 1;
                    System.out.println(item.getName() + ":" + num + "发,来自" + pool);
                    resultList.add(item);
                    resultList.get(resultnum).setCount(String.valueOf(num));
                    resultList.get(resultnum).setGacha_type(pool);
                    resultnum++;
                    num = 0;
                } else {
                    num += 1;
                }

            }
            String t = pools + "已垫" + num + "发";
            System.out.println(t + "\n");
            counts.add(String.valueOf(num));
        }
        this.resultList = resultList;
        //打印数据
        System.out.println("\n\n所有卡池的总物品是：" + total_all);
        System.out.printf("新手池抽卡总数为：%d\n常驻卡池的抽卡总数为：%d\n角色UP池的抽卡总数为:%d\n武器池的抽卡总数为：%d\n%n", pool1.size(), pool2.size(), pool3.size(), pool4.size());
        System.out.println("总角色数是：" + total_characters + ",概率为:" + String.format("%.4f", (double) total_characters / total_all * 100) + "%");
        System.out.println("总武器数是：" + total_weapon + ",概率为:" + String.format("%.4f", (double) total_weapon / total_all * 100) + "%");
        System.out.println("总4星数是：" + total_fourth_star + ",概率为:" + String.format("%.4f", (double) total_fourth_star / total_all * 100) + "%");
        System.out.println("总5星数是：" + total_fifth_star + ",概率为:" + String.format("%.4f", (double) total_fifth_star / total_all * 100) + "%");
        System.out.println("总3星数是：" + total_third_star + ",概率为:" + String.format("%.4f", (double) total_third_star / total_all * 100) + "%");
        this.resultIntList = new int[]{total_all, total_characters, total_weapon, total_third_star, total_fourth_star, total_fifth_star};
    }

    //合并两个list并删除重复项
    public List<ItemEntity> get_collects(List<ItemEntity> list1, List<ItemEntity> list2) {
        List<ItemEntity> collect = Stream.of(list1, list2)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        return collect;
    }
}


