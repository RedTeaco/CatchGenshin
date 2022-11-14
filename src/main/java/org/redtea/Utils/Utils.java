package org.redtea.Utils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class Utils {
    //使眼色从绿变为红
    public static Color getColor(double val) {
        double one = (double) (255 + 255) / 60;//（255+255）除以最大取值的三分之二
        int r, g = 0, b = 0;
        if (val < 30)//第一个三等分
        {
            r = (int) (one * val);
            g = 255;
        } else if (val >= 30 && val < 60)//第二个三等分
        {
            r = 255;
            g = 255 - (int) ((val - 30) * one);//val减最大取值的三分之一
        } else {
            r = 255;
        }//最后一个三等分
        return new Color(r, g, b);
    }
    //设置全局字体
    public static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
