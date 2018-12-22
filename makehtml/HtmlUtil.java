package cn.itcast.common.util.makehtml;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

public class HtmlUtil {
    private static Logger log = Logger.getLogger(HtmlUtil.class);

    /**
     * @param paths  [/upload/2010/0818/3906/image001.gif,/upload/2010/0818/3906/image002.gif]
     * @param prefix
     */
    public static void createImgFolders(List<String> paths, String prefix) {
        for (String path : paths) {
            String p = path.substring(0, path.lastIndexOf("/") + 1);
            createImgFolders(p, prefix);
        }

    }

    /**
     * @param urlstr /upload/2010/0818/3906/
     * @param prefix d://
     */
    private static void createImgFolders(String urlstr, String prefix) {
        if (urlstr == null || "".equals(urlstr)) return;
        String nurl = urlstr.substring(0, urlstr.lastIndexOf("/"));
        String furl = nurl.replaceAll("//", "\\");
        String allPath = prefix + File.separator + furl;
        File folders = new File(allPath);
        if (!folders.exists()) {
            folders.mkdirs();
        }

    }

    /**
     * @param urlstr 不带http://img.fhy.net   例如/static/images/common/touxiang/s_boy01.gif
     * @param prefix d://
     * @return
     */

    private static String getStringPath(String urlstr, String prefix) {
        if (urlstr == null || "".equals(urlstr)) return "";
        String furl = urlstr.replaceAll("//", File.separator);
        String allPath = prefix + File.separator + furl;
        return allPath;
    }


    /**
     * 下载图片  ,下载前须先生成目录
     *
     * @param urlstr 不带http://img.fhy.net   例如/static/images/common/touxiang/s_boy01.gif
     * @param prefix d://
     * @param picServerPath 文件服务器保存文件的路径
     * @return
     */
    public static void getImgFromUrl(String urlstr, String prefix,String picServerPath) throws Exception {
        int num = urlstr.indexOf('/', 8);
        int extnum = urlstr.lastIndexOf('.');
        String u = urlstr.substring(0, num);
        String ext = urlstr.substring(extnum + 1, urlstr.length());
        FileOutputStream fout = null;
        try {
            // 图片的路径 + 文件名
            String realPath = getStringPath(urlstr, prefix);
            //判断图片是否存在，如果在不下载
            File fimg = new File(realPath);
            if (!fimg.exists()) {
                URL url = new URL(picServerPath + urlstr);
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("referer", u);       //通过这个http头的伪装来反盗链
                BufferedImage image = ImageIO.read(connection.getInputStream());
                fout = new FileOutputStream(realPath);
                ImageIO.write(image, ext, fout);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                }
            }
        }

    }

    /**
     * 得到图片对应的文件目录
     *
     * @param source 函数<img src=/upload/2010/0818/3906/image001.gif>的值域
     * @return /upload/2010/0818/3906/image001.gif 的list
     */

    public static List<String> getLocalPath(String source) {
        List list = new ArrayList<String>();
        String path;
        if (null == source || "null".equals(source)) return list;
        StringBuilder sb = new StringBuilder(source);
        Pattern srcReg = Pattern.compile("(?i)(?:src=[\"\']?([./\\w]+)[\"\']?)");
        Matcher srcMat = srcReg.matcher(sb);
        Pattern imgReg = Pattern.compile("(?i)<img\\s*[^>]*?>");
        Matcher imgMat = imgReg.matcher(sb);

        int potinter = 0;
        while (imgMat.find(potinter)) {
            potinter = imgMat.end();
            srcMat.region(imgMat.start(), imgMat.end());
            while (srcMat.find()) {
                path = srcMat.group(1);
                list.add(path);
            }
        }
        log.debug(sb.toString());
        return list;
    }

    public static List<String> distinctList(List<String> list) {
        if (list == null || list.size() == 0) return new ArrayList<String>();
        List<String> temp = new ArrayList<String>();
        for (String s : list) {
            if (!temp.contains(s)) {
                temp.add(s);
            }
        }
        return temp;

    }


}


