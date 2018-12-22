package cn.itcast.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class MultipartEntityServlet extends HttpServlet{
	
	/**Json串*/
	private String jsonStr;
	
	/**保存文件的路径*/
	private String save_path;
	
	private List<String> uploadFileNames;
	
	static String fileSeparator = System.getProperty("file.separator"); // 上传服务器的系统分隔符
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
        if (isMultipart) {  
            FileItemFactory factory = new DiskFileItemFactory();  
            ServletFileUpload upload = new ServletFileUpload(factory);  
            StringBuffer buffer = new StringBuffer("{");
            uploadFileNames = new LinkedList<String>();
            String tmpFileName = null;
            try {  
                List items = upload.parseRequest(request);  
                Iterator iter = items.iterator();  
                while (iter.hasNext()) {  
                    FileItem item = (FileItem) iter.next();  
                    if (item.isFormField()) {  
                        //普通文本信息处理  
                        String paramName = item.getFieldName();  
                        String paramValue = item.getString();  
                        System.out.println(paramName + ":" + paramValue);  
                        buffer.append(paramName + ":" + paramValue);
                    } else {  
                        //上传文件信息处理  
                        String fileName = item.getName();  
                        byte[] data = item.get(); 
                        if(save_path!=null && save_path.length()>0){
                        	String serverUploadPath = PropertiesUtils.getPropValue("fileupload.rootpath");
                        	 String filePath = getServletContext().getRealPath("/files") + "/" + fileName;  
                        	 tmpFileName = serverUploadPath+save_path+fileSeparator+filePath;
                        	 //写文件
                        	 FileOutputStream fos = new FileOutputStream(tmpFileName);  
                             fos.write(data);  
                             fos.close();
                             uploadFileNames.add(filePath);
                        }
                       
                    }  
                } 
                //得到json串
                jsonStr = buffer.append("}").toString();
            } catch (FileUploadException e) {  
                e.printStackTrace();  
            }  
        }  
        //response.getWriter().write("UPLOAD_SUCCESS");  
    }

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getSave_path() {
		return save_path;
	}

	public void setSave_path(String save_path) {
		this.save_path = save_path;
	}

	public List<String> getUploadFileNames() {
		return uploadFileNames;
	}

	public void setUploadFileNames(List<String> uploadFileNames) {
		this.uploadFileNames = uploadFileNames;
	}

	
}
