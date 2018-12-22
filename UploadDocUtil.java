package cn.itcast.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.itcast.common.util.image.ScaleImage;

/**
 * 上传文件工具类
 *
 */
public class UploadDocUtil {
	
	public static String fileSeparator = System.getProperty("file.separator"); // 上传服务器的系统分隔符
	public static final String FILE_UPLOAD_ROOTPATH = "fileupload.rootpath"; // 服务器根路径
	public static final String UPLOAD_PATH = "upload.path";
	
	/**试题上传路径*/
	public static final String THEMES_UPLOAD_PATH = "themes.upload.path";
	
	//static Logger log = Logger.getLogger(UploadDocUtil.class);
	
	/**
	 * 系统试题中图片上传保存到数据库的url
	 * @return
	 */
	public static String getDbSavePathByThemesFile() {
		// 当前服务器上传路径(配置文件)
		String uploadPath = PropertiesUtils.getPropValue(THEMES_UPLOAD_PATH);
		// 写入目录 serverUploadPath/year/month/day/hour/minute
		int year = DateFormatUtil.getCurrentYear();
		int month = DateFormatUtil.getCurrentMonth();
		int day = DateFormatUtil.getCurrentDay();
		int hour = DateFormatUtil.getCurrentHour();
		int minute = DateFormatUtil.getCurrentMinute();
		StringBuffer sb = new StringBuffer(uploadPath);
		sb.append(fileSeparator)
		.append(year).append(fileSeparator)
		.append(month).append(fileSeparator)
		.append(day).append(fileSeparator)
		.append(hour).append(fileSeparator)
		.append(minute);
		return sb.toString();
	}
	
	/**
	 * 系统试题上传文件服务器全路径
	 * @return
	 */
	public static String getFullPathByThemesFileSave(){
		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesUtils.getPropValue(FILE_UPLOAD_ROOTPATH))
		  .append(getDbSavePathByThemesFile());
		return sb.toString();
	}
	
	/**
	 * 重载方法: 得到上传文件的保存路径
	 * @param uploadRootPath 上传文件的根路径
	 * @return
	 */
	public static String getFullUploadFilePath(String uploadRootPath) {
		int year = DateFormatUtil.getCurrentYear();
		int month = DateFormatUtil.getCurrentMonth();
		int day = DateFormatUtil.getCurrentDay();
		String saveFileDir = uploadRootPath + fileSeparator + year
				+ fileSeparator + month + fileSeparator + day;
		return saveFileDir;
	}
	
	public static String getSavePathByUploadFile() {
		String uploadPath = PropertiesUtils.getPropValue(UPLOAD_PATH);
		int year = DateFormatUtil.getCurrentYear();
		int month = DateFormatUtil.getCurrentMonth();
		int day = DateFormatUtil.getCurrentDay();
		String saveFileDir =  uploadPath + fileSeparator+year
				+ fileSeparator + month + fileSeparator + day;
		return saveFileDir;
	}
	
	/**
	 * 上传文件保存到DB路径
	 * @param user_id
	 * @return
	 */
	public static String getUploadFileSavePath(long user_id) {
		// 当前服务器上传路径(配置文件)
		String uploadPath = PropertiesUtils.getPropValue(UPLOAD_PATH);
		// 写入目录 serverUploadPath/year/month/day/user_id
		int year = DateFormatUtil.getCurrentYear();
		int month = DateFormatUtil.getCurrentMonth();
		int day = DateFormatUtil.getCurrentDay();
		StringBuffer sb = new StringBuffer(uploadPath);
		sb.append(fileSeparator)
		.append(year).append(fileSeparator)
		.append(month).append(fileSeparator)
		.append(day).append(fileSeparator)
		.append(user_id);
		return sb.toString();
	}
	
	/**
	 * 得到 存储DB路径全名
	 * @param saveFileDir 存储路径目录
	 * @param fileName 存储文件名称
	 * @return
	 */
	public static String getUploadFileSaveFullPath(String saveFileDir,
			String fileName){
		
		if(StringUtil.isEmpty(fileName) || 
				StringUtil.isEmpty(saveFileDir)){
			return null;
		}
		StringBuffer sb = new StringBuffer(saveFileDir);
		if(!saveFileDir.endsWith(fileSeparator)){
			sb.append(fileSeparator).append(fileName);
		}else{
			sb.append(fileName);
		}
		return sb.toString();
	}
	
	/**
	 * 上传文件生成缩略图（100*100），返回小图保存相对路径
	 * @param user_id 用户编号
	 * @param fromFileStr 源文件绝对路径
	 * @param formatWideth 缩略图宽
	 * @param formatHeight 缩略图高
	 * @param formatName 保存文件格式 JPEG、TIFF、RAW、BMP、GIF、PNG
	 * @param imgFileFileName 图片名称
	 * @return
	 * @throws Exception 
	 */
	public static String getSmallPicSavePathByUploadFile(long user_id,
			String fromFileStr,int formatWideth, int formatHeight,
            String imgFileFileName) throws Exception {
		
		if(user_id<=0 || StringUtil.isEmpty(fromFileStr)
				|| formatWideth==0 || formatHeight==0){
			return null;
		}
		
		StringBuffer sb = new StringBuffer(getUploadFileSavePath(user_id));
		sb.append(fileSeparator).append(formatWideth)
		.append("multiply").append(formatHeight);
		String docFile = PropertiesUtils.getPropValue(FILE_UPLOAD_ROOTPATH)
				+sb.toString();
		File file = new File(docFile);
		if(!file.exists()){
			file.mkdirs();
		}
		
		sb.append(fileSeparator).append(imgFileFileName);
		String absolutePath = PropertiesUtils.getPropValue(FILE_UPLOAD_ROOTPATH)
				+sb.toString(); 
		
		ScaleImage scaleImg = new ScaleImage();
		scaleImg.saveImageAsFormateJpg(fromFileStr, absolutePath,
                formatWideth, formatHeight,
                getImgExpandName(imgFileFileName));
		return sb.toString();
	}
	
	/**
	 * 得到中等图保存数据库的文件路径
	 * @param uploadFileSavePath 保存文件的相对路径
	 * @param imgFileFileName
	 * @return
	 */
	public static String getMediumPicSavePathByUploadFile(String uploadFileSavePath,
            String imgFileFileName) {
		if(StringUtil.isEmpty(uploadFileSavePath)){
			return null;
		}
		StringBuffer sb = new StringBuffer(uploadFileSavePath);
		sb.append(fileSeparator).append("mediumpic");
		String docFile = PropertiesUtils.getPropValue(FILE_UPLOAD_ROOTPATH)
				+sb.toString();
		File file = new File(docFile);
		if(!file.exists()){
			file.mkdirs();
		}
		if(!StringUtil.isEmpty(imgFileFileName)){
			sb.append(fileSeparator).append(imgFileFileName);
		}
		return sb.toString();
	}
	
	/**
	 * 得到缩略图保存数据库的文件路径
	 * @param uploadFileSavePath 保存文件的相对路径
	 * @param formatWideth
	 * @param formatHeight
	 * @param imgFileFileName
	 * @return
	 */
	public static String getScalePicSavePathByUploadFile(String uploadFileSavePath,
			int formatWideth, int formatHeight,
            String imgFileFileName) {
		
		if(StringUtil.isEmpty(uploadFileSavePath) 
				|| formatWideth==0 || formatHeight==0){
			return null;
		}
		StringBuffer sb = new StringBuffer(uploadFileSavePath);
		sb.append(fileSeparator).append(formatWideth)
		.append("multiply").append(formatHeight);
		String docFile = PropertiesUtils.getPropValue(FILE_UPLOAD_ROOTPATH)
				+sb.toString();
		File file = new File(docFile);
		if(!file.exists()){
			file.mkdirs();
		}
		if(!StringUtil.isEmpty(imgFileFileName)){
			sb.append(fileSeparator).append(imgFileFileName);
		}
		return sb.toString();
	}
	
	/**
	 * 用户上传文件到文件服务器路径。
	 * @param user_id
	 * @return
	 */
	public static String getServerUpFileSavePath(long user_id) {
		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesUtils.getPropValue(FILE_UPLOAD_ROOTPATH))
		  .append(getUploadFileSavePath(user_id));
		return sb.toString();
	}
	
	/**
	 * 解zip文件或写文件到文件服务器
	 * @param relativeFilePath 文件相对全路径名称
	 * @param saveFileDir 保存目录
	 * @return
	 * @throws Exception
	 */
	public static boolean unZipFileOrWriteFile(String relativeFilePath,
			String saveFileDir) throws Exception{
		
		String zip = "zip";
		String serverUploadPath = PropertiesUtils.getPropValue(FILE_UPLOAD_ROOTPATH);
		File file = new File(serverUploadPath+relativeFilePath);
		if(file.exists()){//文件存在
			String expandName = getImgExpandName(relativeFilePath);
			if(zip.equals(expandName)){
				String fileDir = serverUploadPath+saveFileDir;
				FileUtils.unzip(file, fileDir);
				// 创建文件成功，删除zip文件
				file.delete();
			}
		}
		return true;
	}
	
	/**
	 * 解压文件，写入服务器
	 * @param saveFileDir
	 * @param zipFile 压缩文件
	 * @param zipFileFileName
	 * @return
	 * @throws Exception
	 */
	public static boolean writeFile(String saveFileDir,File zipFile,String zipFileFileName)
			throws Exception{
		
		FileOutputStream os = null;
		String serverUploadPath = PropertiesUtils.getPropValue(FILE_UPLOAD_ROOTPATH);
		String fileDir = serverUploadPath+saveFileDir;
		File uploadPicParent = new File(fileDir);
		if (!uploadPicParent.exists()){
			uploadPicParent.mkdirs();
		}
		//创建压缩文件，图片文件名为当前毫秒数,上传压缩文件
		File zipF = new File(uploadPicParent, zipFileFileName);
		InputStream is = new FileInputStream(zipFile);
		os = new FileOutputStream(zipF);
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
		is.close();
		os.close();
		
		FileUtils.unzip(zipF, fileDir);
		// 创建文件成功，删除zip文件
		if (zipF.exists()) {
			zipF.delete();
		}
		return true;
	}
	
	/**
	 * 文件上传到文件服务器目录
	 * @param saveFileDir 
	 * @param uploadFile 上传文件
	 * @param uploadFileFileName
	 * @param serverUploadPath 上传服务器存储根路径
	 * @return
	 * @throws Exception
	 */
	public static boolean writeFile(String saveFileDir,File uploadFile,
			String uploadFileFileName,String serverUploadPath)throws Exception{
		
		FileOutputStream os = null;
		String fileDir = serverUploadPath+saveFileDir;
		File uploadPicParent = new File(fileDir);
		if (!uploadPicParent.exists()){
			uploadPicParent.mkdirs();
		}
		//创建压缩文件，图片文件名为当前毫秒数,上传压缩文件
		File zipF = new File(uploadPicParent, uploadFileFileName);
		InputStream is = new FileInputStream(uploadFile);
		os = new FileOutputStream(zipF);
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
		is.close();
		os.close();
		return true;
	}
	
	/**
	 * 得到文件扩展名，文件格式
	 * @param imgFileFileName 文件名称（包含扩展名）
	 * @return 
	 */
	public static String getImgExpandName(String imgFileFileName){
		
		if(StringUtil.isEmpty(imgFileFileName)){
			return null;
		}
		return imgFileFileName.substring(imgFileFileName.indexOf(".")+1);
	}
	
	/**
     * 文件MD5摘要
     * @param uploadFile
     * @return
     */
    public static String createMd5(File file) {
    	if(file==null){
    		return "";
    	}
        MessageDigest mMDigest;
        FileInputStream Input;
        byte buffer[] = new byte[1024];
        int len;
        if (!file.exists())
            return null;
        try {
            mMDigest = MessageDigest.getInstance("MD5");
            Input = new FileInputStream(file);
            while ((len = Input.read(buffer, 0, 1024)) != -1) {
                mMDigest.update(buffer, 0, len);
            }
            Input.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        BigInteger mBInteger = new BigInteger(1, mMDigest.digest());
        return mBInteger.toString(16);
    }
    
    private static String byte2hex(final byte[] b) {
    	String hs = "";
    	String stmp = "";
    	for (int n = 0; n < b.length; n++) {
    		stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
    	if (stmp.length() == 1) {
    		hs = hs + "0" + stmp;
    	} else {
    		hs = hs + stmp;
    		}
    	}
    	return hs;
    }
    	 
    	
    /**
	* 对一个文件求他的md5值
	* @param f
	* 要求md5值的文件
	* @return md5串
	*/	 
    public static String md5(File f) {
    	
    	FileInputStream fis = null;
    	 
    	try {
	    	MessageDigest md = MessageDigest.getInstance("MD5");
	    	fis = new FileInputStream(f);
	    	byte[] buffer = new byte[8192];
	    	int length;
	    	while ((length = fis.read(buffer)) != -1) {
	    		md.update(buffer, 0, length);
	    	}
	    	return new String(byte2hex(md.digest()));
    	} catch (Exception e) {
    		return null;
    	} finally {
    	 
    	try {
	    	if (fis != null)
	    		fis.close();
	    	} catch (IOException e) {
    	 
	    		e.printStackTrace();
	    	}
    	}
    }
    
    /**
     * 得到执行生成缩略图命令
     * @param fromFileStr 源文件全文件路径名称
     * @param targetFileFullPath 目标文件全文件路径名称
     * @param formatWideth 宽度
     * @param formatHeight 高度
     * @return
     */
    public static String getScaleImgCommandStr(
    		String fromFileStr, String targetFileFullPath,
            int formatWideth, int formatHeight
            ){
    	StringBuffer sb = new StringBuffer("/usr/bin/convert");
    	sb.append(" -resize");
    	sb.append(" ");
    	if(formatWideth>0)
    		sb.append(formatWideth);
    	if(formatHeight>0)
    		sb.append("x").append(formatHeight);
    	sb.append(" ").append(fromFileStr);
    	sb.append(" ").append(targetFileFullPath);
    	return sb.toString();
    }
    
    /**
     * <p>得到执行生成缩略图命令</p>
     * 去除拍摄参数等信息+有损压缩<br>
     * @param fromFileStr 源文件全文件路径名称
     * @param targetFileFullPath 目标文件全文件路径名称
     * @param formatWideth 宽度
     * @param formatHeight 高度
     * @return ImageMagick的压缩命令，该返回值可通过Runtime.getRuntime().exec(command);执行
     * @author leo_soul
     * 2014年12月3日
     */
    public static String getLossScaleImgCommandStr(
    		String fromFileStr, String targetFileFullPath,
            int formatWideth, int formatHeight
            ){
    	StringBuffer sb = new StringBuffer("/usr/bin/convert");
    	sb.append(" -resize");
    	sb.append(" ");
    	if(formatWideth>0)
    		sb.append(formatWideth);
    	if(formatHeight>0)
    		sb.append("x").append(formatHeight);
    	sb.append(" -strip -quality 75%");//add by leo_soul 2014-12-3 去掉照片拍摄信息，有损压缩比75%
    	sb.append(" ").append(fromFileStr);
    	sb.append(" ").append(targetFileFullPath);
    	return sb.toString();
    }
    
    /**
     * <p>根据路径删除指定的目录或文件，无论存在与否</p>
     * <br>
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则(包括文件不存在)返回 false。
     * @author leo_soul
     * 2014年11月21日
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }
    /**
     * <p>删除单个文件</p>
     * <br>
     * @param sPath 被删除文件的文件
     * @return 单个文件删除成功返回true，否则返回false
     * @author leo_soul
     * 2014年11月21日
     */
    private static boolean deleteFile(String sPath) {
    	boolean flag = false;
    	File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    /**
     * <p>删除目录（文件夹）以及目录下的文件</p>
     * <br>
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     * @author leo_soul
     * 2014年11月21日
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
}
