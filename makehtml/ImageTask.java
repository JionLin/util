package cn.itcast.common.util.makehtml;

import java.util.concurrent.Callable;

/**
 * 
* <p>图片处理的任务类</p>
* @ClassName: ImageTask
*
 */
public class ImageTask implements Callable{
	
	/**文件url*/
	private String urlStr;
	
	/**文件保存根目录*/
    private String prefixPath;
    
    /**文件服务器文件下载url*/
    private String picServerpath;
    
    public ImageTask(String urlStr,String prefixPath,String picServerpath){
        this.urlStr = urlStr;
        this.prefixPath = prefixPath;
        this.picServerpath = picServerpath;
    }
    @Override
	public Object call() throws Exception {
        downImgs();
        return true;
    }

    private void downImgs() throws Exception {
           HtmlUtil.getImgFromUrl(urlStr,prefixPath,picServerpath);
    }
}
