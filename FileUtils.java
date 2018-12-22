package cn.itcast.common.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * 
* <p>文件工具类</p>
* @ClassName: FileUtils
* @date 2012-5-21 下午04:25:52
*
 */
public class FileUtils {
    private static final int BUFFER_SIZE = 16 * 1024;
    public final static String encoding = "GBK";
    private static byte[] b = new byte[1024];
    public final static String UTF8_ENCODING = "UTF-8";
    
    /**宽度px*/
    public static final int formatWidth = 100;
    public static final int formatHeight = 100;
    
    public static final int FORMATWIDTH_200 = 200;
    public static final int FORMATHEIGHT_200 = 200;
    
    /** 中等图的长边最大值（宽或高长的那条边的最大值） */
    public static final int FORMAT_MEDIUM = 900;

    public static void copy(File src, File dst) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst),
                    BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getPath(String root,String md5) throws Exception {
        if(md5==null||"".equals(md5))throw new Exception("userid md5 error");
        String level1 = root+md5.substring(0,1);
        String level2 = level1+File.separator+md5.substring(1,3);
        String level3 = level2+File.separator+md5;
        File flevel1 = new File(level1);
        File flevel2 = new File(level2);
        File flevel3 = new File(level3);
        if(!flevel1.exists()){
            flevel1.mkdirs();
        }
        if(!flevel2.exists()){
            flevel2.mkdirs();
        }
        if(!flevel3.exists()){
            flevel3.mkdirs();
        }
        return level3;
    }

    public static String getMd5FileString(String md5){
        String level1 = md5.substring(0,1);
        String level2 = level1+File.separator+md5.substring(1,3);
        String level3 = level2+File.separator+md5;
        return level3;
    }

    /**
     *
     * @param srcPathname   d://test
     * @param zipFilepath    d://test.zip
     * @throws Exception
     */

    public static void zip(String srcPathname, String zipFilepath)
            throws Exception {
        File file = new File(srcPathname);
        if (!file.exists())
            throw new RuntimeException("source file or directory "
                    + srcPathname + " does not exist.");

        Project proj = new Project();
        FileSet fileSet = new FileSet();
        fileSet.setProject(proj);
        // 判断是目录还是文件
        if (file.isDirectory()) {
            fileSet.setDir(file);
        } else {
            fileSet.setFile(file);
        }

        Zip zip = new Zip();
        zip.setProject(proj);
        zip.setDestFile(new File(zipFilepath));
        zip.addFileset(fileSet);
        zip.setEncoding(encoding);
        zip.execute();
    }

    public static void unzip(String zipFileName, String extPlace) throws Exception {
        try {

            File ext = new File(extPlace);
            if(!ext.exists()){
                ext.mkdirs();
            }
            File f = new File(zipFileName);
            ZipFile zipFile = new ZipFile(zipFileName);
            if((!f.exists()) && (f.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            String strPath, gbkPath, strtemp;
            File tempFile = new File(extPlace);
            strPath = tempFile.getAbsolutePath();
            java.util.Enumeration e = zipFile.getEntries();
            while(e.hasMoreElements()){
                org.apache.tools.zip.ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath=zipEnt.getName();
                if(zipEnt.isDirectory()){
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else {
                    //读写文件
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    gbkPath=zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;

                    //建目录
                    String strsubdir = gbkPath;
                    for(int i = 0; i < strsubdir.length(); i++) {
                        if(strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                            String temp = strPath + File.separator + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if(!subdir.exists())
                            subdir.mkdir();
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.close();
                    fos.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void unzip(File srcZipFile, String extPlace) throws Exception {
        try {

            File ext = new File(extPlace);
            if(!ext.exists()){
                ext.mkdirs();
            }


            if((!srcZipFile.exists()) && (srcZipFile.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            ZipFile zipFile = new ZipFile(srcZipFile);
            String strPath, gbkPath, strtemp;
            File tempFile = new File(extPlace);
            strPath = tempFile.getAbsolutePath();
            java.util.Enumeration e = zipFile.getEntries();
            while(e.hasMoreElements()){
                org.apache.tools.zip.ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath=zipEnt.getName();
                if(zipEnt.isDirectory()){
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else {
                    //读写文件
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    gbkPath=zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;

                    //建目录
                    String strsubdir = gbkPath;
                    for(int i = 0; i < strsubdir.length(); i++) {
                        if(strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                            String temp = strPath + File.separator + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if(!subdir.exists())
                            subdir.mkdir();
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.close();
                    fos.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void copyFolders(File src,File dest,final String filterName) throws IOException {
        FileFilter ff = new FileFilter() {
            @Override
			public boolean accept(File pathname) {
                if(pathname.isDirectory()){
                    if(pathname.getName().equalsIgnoreCase(filterName))return false;
                }return true;

            }
        };
        org.apache.commons.io.FileUtils.copyDirectory(src,dest,ff);
    }
    
    public static void copyFolders(File src,File dest) throws IOException {
        org.apache.commons.io.FileUtils.copyDirectory(src,dest);
    }

    public static String getWebRootPath(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        java.net.URL url = classLoader.getResource("");
        String root_class_path = url.getPath()+"/";
        File rootFile = new File(root_class_path);
        String web_dir_path = rootFile.getParent()+"/";
        File webInfoDir = new File(web_dir_path);
        String servlet_path = webInfoDir.getParent();
        return servlet_path;
    }
    
    /**
	 * 保存文件
	 * @param inputFile 上传文件
	 * @param destFile 目标文件
	 */
    public static void saveFile(File inputFile, File destFile) {
        InputStream in = null;  
        OutputStream out = null;  
        try {
        	if(!destFile.getParentFile().exists()) { //查看文件的上层目录是否存在
        		destFile.getParentFile().mkdirs();
        	}
            in = new BufferedInputStream(new FileInputStream(inputFile));  
            out = new BufferedOutputStream(new FileOutputStream(destFile));  
            byte[] buffer = new byte[1024*10];  
            int  size ;
            while ((size=in.read(buffer))!=-1) {
            	out.write(buffer,0,size);  
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally { 
        	try {
        		if (null != in) {
        			in.close();
                }
                if (null != out) {  
                    out.close();
                }
			} catch (IOException e) {
			}
        }  
	}
    
    
    /**
	 * 保存文件
	 * @param inputFile 上传文件
	 * @param destFile 目标文件
	 */
    public static void saveFile(InputStream in, File destFile) {
        OutputStream out = null;  
        try {
        	if(!destFile.getParentFile().exists()) { //查看文件的上层目录是否存在
        		destFile.getParentFile().mkdirs();
        	}
            out = new BufferedOutputStream(new FileOutputStream(destFile));  
            byte[] buffer = new byte[1024*10];  
            int  size ;
            while ((size=in.read(buffer))!=-1) {
            	out.write(buffer,0,size);  
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally { 
        	try {
        		if (null != in) {
        			in.close();
                }
                if (null != out) {  
                    out.close();
                }
			} catch (IOException e) {
			}
        }  
	}
    
    /**
     * 
     * <P>http 文件下载</P>
     * @param allpath 文件路径
     * @param name   文件名称
     * @param response 
     * @param encode 编码格式
     * @param fileFormat 文件格式
     * @return
     * @throws Exception
     */
    public static boolean downloadFiles(String allpath, String name,
    		HttpServletResponse response,String encode,String fileFormat)throws Exception{
    	
    	String filename = java.net.URLEncoder.encode(name, encode);
    	response.setContentType(fileFormat);
    	response.setHeader("Content-disposition", "attachement; filename=" + filename);

        InputStream in = null;
        OutputStream out = null;
        File f = new File(allpath);
        if (!f.exists()) {
            throw new IOException(name + ",所下载的文件不存在!");
        }
        response.setContentLength((int) f.length());
        try {
            in = new FileInputStream(allpath);
            out = response.getOutputStream();
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
        }catch(IOException ex){
        	return false;
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.flush();
                out.close();
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
        }
        return true;
    }
    
    /**
     * 得到文件大小(单位:byte)
     * @param fileFullPathName
     * @return
     */
    public static long getFileSize(String fileFullPathName){
    	
    	if(StringUtil.isEmpty(fileFullPathName)){
    		return 0;
    	}
    	File file = new File(fileFullPathName);
    	if(!file.exists()){
    		return 0;
    	}
    	return file.length();
    }
}
