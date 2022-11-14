package org.redtea.resourceUpdate;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String getResourcesPath(){
        File file = new File("");
        String str = file.getAbsolutePath() + "\\src\\main\\resources\\icons\\";
        return str;
    }


    //获取网页源码
    public static String getHtml(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection hrc = (HttpURLConnection) url.openConnection();
        InputStream in = hrc.getInputStream();
        String html = Utils.convertStreamToString(in);
        return html;
    }
    public static String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        String str = System.getProperty("line.separator");
        try {
            while ((line = reader.readLine()) != null){
                sb.append(line).append(str);
            }
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static Map<String,String> getAvatarUrl(String html){
        Map<String, String> map = new HashMap<>();
        final String IMGMATCH = "<img alt=.*?>";
        final String HTTP_IMG = "(http|https)://.+\\.(jpg|gif|png)";
        Matcher matcher = Pattern.compile(IMGMATCH).matcher(html);
        while (matcher.find()){
            try {
                String str = matcher.group().substring(0,matcher.group().length()-1);
                String[] strings = str.split(" ");
                String[] avatars = strings[1].split("\"")[1].split("头像");
                String avatar = avatars[0]+avatars[1];
                String url = strings[9].substring(strings[9].length()-4);
                if ( url.equals(".png") || url.equals(".jpg")){
                    map.put(avatar,strings[9]);
                }
            }catch (IllegalStateException | ArrayIndexOutOfBoundsException e){
                //e.printStackTrace();
            }
        }
        return map;
    }

    public static Map<String,String> getWeaponUrl(String html){
        Map<String, String> map = new HashMap<>();
        final String IMGMATCH = "<img alt=.*?>";
        final String HTTP_IMG = "(http|https)://.+\\.(jpg|gif|png)";
        Matcher matcher = Pattern.compile(IMGMATCH).matcher(html);
        while (matcher.find()){
            try {
                String str = matcher.group().substring(0,matcher.group().length()-1);
                String[] strings = str.split(" ");
                String url = strings[7].substring(strings[7].length()-4);
                if ( url.equals(".png") || url.equals(".jpg")){
                    map.put(strings[1].split("\"")[1],strings[7].split("\"")[1]);
                }
            }catch (IllegalStateException | ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
        return map;
    }

    public static void downPictor(String url,String path){
        CloseableHttpClient httpClient = null;
        InputStream inputStream = null;
        File file = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet method = new HttpGet(url);
            HttpResponse result = httpClient.execute(method);
            inputStream = result.getEntity().getContent();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inputStream);
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File(path);
            if(!new File(imageFile.getParent()).exists()){
                new File(imageFile.getParent()).mkdirs();
            }
            //创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
            //关闭输出流
            outStream.close();
        } catch (Exception ignored) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ignored) {

            }
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException ignored) {

            }
        }
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}

