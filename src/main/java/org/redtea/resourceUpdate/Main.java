package org.redtea.resourceUpdate;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Main.count = count;
    }

    private static int count = 0;
    private static int countEnd = 15;
    public static void start(){
        String html = null;
        String htmlWeapon = null;
        Map<String,String> mapAvatar = new HashMap<>();
        Map<String,String> mapWeapon = new HashMap<>();
        try {
            html = Utils.getHtml("https://wiki.biligame.com/ys/%E8%A7%92%E8%89%B2%E7%AD%9B%E9%80%89");
            htmlWeapon = Utils.getHtml("https://wiki.biligame.com/ys/%E6%AD%A6%E5%99%A8%E5%9B%BE%E9%89%B4");
        }catch (IOException e){
            //e.printStackTrace();
        }
        count = 15;
        countEnd = 20;
        mapAvatar = Utils.getAvatarUrl(html);
        count = 20;
        countEnd=25;
        mapWeapon = Utils.getWeaponUrl(htmlWeapon);
        count = 25;
        countEnd = 65;
        for (String key:mapAvatar.keySet()){
            File file = new File(Utils.getResourcesPath()+"characters\\"+key);
            if (!file.exists()){
                Utils.downPictor(mapAvatar.get(key),Utils.getResourcesPath()+"characters\\"+key);
            }
        }
        count = 65;
        countEnd = 100;
        for (String key:mapWeapon.keySet()){
            File file = new File(Utils.getResourcesPath()+"weapons\\"+key);
            if (!file.exists()){
                Utils.downPictor(mapWeapon.get(key),Utils.getResourcesPath()+"weapons\\"+key);
            }
        }
        count = 100;
    }

    public static int getCountEnd() {
        return countEnd;
    }

    public static void setCountEnd(int countEnd) {
        Main.countEnd = countEnd;
    }
}

