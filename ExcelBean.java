package cn.itcast.common.util;

/**
 * Copyright (c) 2008 winclass Corporation. All Rights Reserved.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 
* <p>用于Excel文件写操作，当但击“导出按钮”时可以直接在客户端导出Excel文件并提示保存 使用方法： 在action类中加入： ExcelBean eb
 * = new ExcelBean(); if(list.size()>0){ eb.exportToExcel(response, fname,
 * sheetname, titles, list); //调用生成excel文件bean } 参数：
 * response:action类中的HttpServletResponse; filename:要生成的保存的缺省的Excel文件名
 * sheetname:要保存的工作表的名称 titles:工作表中的表格横向标题 list:数据行</p>
* @ClassName: ExcelBean
* @date 2012-03-21 上午09:53:230
*
 */
public class ExcelBean {
	public ExcelBean() {
	}

	public boolean exportToExcel(HttpServletResponse response, String filename,
			String sheetname, String[] titles, List<String[]> list) throws Exception {
		OutputStream os = null;
		boolean b1 = true;

		try {
			os = response.getOutputStream(); // 取得输出流
			response.reset(); // 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ filename + ".xls"); // 设定输出文件头
			response.setContentType("application/msexcel"); // 定义输出类型
		} catch (IOException ex) {
			b1 = false;
			System.out.println("流操作错误:" + ex.getMessage());
		}
		WritableWorkbook workbook = null;
		try {
			// 创建新的Excel 工作簿
			workbook = Workbook.createWorkbook(os);
			// 在Excel工作簿中建一工作表，其名为:第一页
			jxl.write.WritableSheet wsheet = workbook.createSheet(sheetname, 0); // sheet();
			WritableFont font = new WritableFont(WritableFont.ARIAL, 14,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			WritableCellFormat format = new WritableCellFormat(font);
			for (int i = 0; i < titles.length; i++) {
				Label wlabel1 = new Label(i, 0, titles[i], format); // 列、行、单元格中的文本、文本格式
				wsheet.addCell(wlabel1);
			}
			font = new jxl.write.WritableFont(WritableFont.createFont("宋体"),
					12, WritableFont.NO_BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			format = new jxl.write.WritableCellFormat(font);
			for (int i = 0; i < list.size(); i++) { // 在索引0的位置创建行（最顶端的行）
				String[] sdata = list.get(i);
				for (int j = 0; j < sdata.length; j++) { // 在索引0的位置创建单元格（左上端）
					Label wlabel1 = new Label(j, i + 1, sdata[j], format); // 列、行、单元格中的文本、文本格式
					wsheet.addCell(wlabel1);
				}
			}
			workbook.write(); // 写入文件
		} catch (WriteException ex1) {
			b1 = false;
			System.out.println("WriteException:" + ex1.getMessage());
		} catch (IOException ex2) {
			b1 = false;
			System.out.println("IOException:" + ex2.getMessage());
		}
		workbook.close();
		os.close();
		return b1;
	}
}
