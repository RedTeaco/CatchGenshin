package org.redtea.getUrl;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getUrl {
    public static String Main(){
        return getUrls(getGamePath()+"\\webCaches\\Cache\\Cache_Data\\data_2");
    }

    public static String outUrl(){
        String url = getUrls(getGamePath()+"\\webCaches\\Cache\\Cache_Data\\data_2");
        try {
            FileWriter fileWriter = new FileWriter(getPath()+"\\data\\url.txt");
            fileWriter.write(url);
            fileWriter.flush();
            //System.out.println(url);
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getGamePath(){
        String string = System.getenv("userprofile");
        String getPath = string+"\\AppData\\LocalLow\\miHoYo\\原神\\output_log.txt";
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(getPath))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rawPath = sb.toString();
        //System.out.println(rawPath);
        String GET_PATH = "[A-Z]?:/[^/].*?(GenshinImpact_Data|YuanShen_Data)";
        Matcher matcher = Pattern.compile(GET_PATH).matcher(rawPath);
        matcher.find();
        //System.out.println(matcher.group());
        return matcher.group();
    }

    public static String getUrls(String path){
        File dest = new File("./data/data_2");
        try {
            copyFileUsingApacheCommonsIO(new File(path),dest);
        }catch (IOException e){
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(dest))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rawUrl = sb.toString();
        String URL = "https://webstatic.mihoyo.com/.+?game_biz=hk4e_(global|cn)";
        Matcher matcher = Pattern.compile(URL).matcher(rawUrl);
        String result = null;
        while (matcher.find()){
            result = matcher.group();
        }
        result += "#/log";
        dest.delete();
        return result;
    }

    public static String getPath(){
        File file = new File("");
        return file.getAbsolutePath();
    }

    private static void copyFileUsingApacheCommonsIO(File source, File dest)
            throws IOException {
        FileUtils.copyFile(source, dest);
    }

    public static void main(String[] args) {
        System.out.println(Main());
    }
}

