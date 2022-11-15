package org.redtea.Utils;

import java.io.File;
import java.io.FilenameFilter;

public class NameFilter implements FilenameFilter {
    //扩展名
    private String extent;

    public NameFilter(String extent){
        this.extent = extent;
    }

    @Override
    public boolean accept(File dir, String name){
        return name.endsWith(extent);
        //文件扩展名符合，返回true
    }
}
