package cn.itcast.common.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowImageServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1912397496946810347L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-store");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpg");
		ServletOutputStream responseOutputStream = resp.getOutputStream();
		String url = req.getParameter("url");
		if (null != url) {
			byte[] tempbytes = new byte[1024];
			int byteread = 0;
			InputStream is = new FileInputStream(url);
			while ((byteread = is.read(tempbytes)) != -1) {
				responseOutputStream.write(tempbytes, 0, byteread);
			}
			responseOutputStream.flush();   
			responseOutputStream.close();  
			is.close();
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
