package cn.itcast.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;

public class DbfUtil {

	public static void readDBF(String path) {
		InputStream fis = null;
		try {
			// 读取文件的输入流
			fis = new FileInputStream(path);
			// 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
			DBFReader reader = new DBFReader(fis);
			// 调用DBFReader对实例方法得到path文件中字段的个数
			int fieldsCount = reader.getFieldCount();
			// 取出字段信息

			for (int i = 0; i < fieldsCount; i++) {
				DBFField field = reader.getField(i);
				System.out.print(field.getName() + "||");

			}
			Object[] rowValues;
			// 一条条取出path文件中记录
			while ((rowValues = reader.nextRecord()) != null) {
				System.out.println("");
				for (int i = 0; i < rowValues.length; i++) {
					System.out.print(rowValues[i] + "||");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
	}

	public static void writeDBF(String path) {
		OutputStream fos = null;
		try {
			// 定义DBF文件字段
			DBFField[] fields = new DBFField[3];
			// 分别定义各个字段信息，setFieldName和setName作用相同，
			// 只是setFieldName已经不建议使用
			fields[0] = new DBFField();
			// fields[0].setFieldName("emp_code");
			fields[0].setName("名称");
			fields[0].setDataType(DBFField.FIELD_TYPE_C);
			fields[0].setFieldLength(10);
			fields[1] = new DBFField();
			// fields[1].setFieldName("emp_name");
			fields[1].setName("emp_name");
			fields[1].setDataType(DBFField.FIELD_TYPE_C);
			fields[1].setFieldLength(7);
			fields[2] = new DBFField();
			// fields[2].setFieldName("salary");
			fields[2].setName("salary");
			fields[2].setDataType(DBFField.FIELD_TYPE_N);
			fields[2].setFieldLength(8);
			fields[2].setDecimalCount(2);
			// 定义DBFWriter实例用来写DBF文件
			// DBFWriter writer=new DBFWriter(new File(path));
			DBFWriter writer = new DBFWriter();

			// 把字段信息写入DBFWriter实例，即定义表结构
			try {
				writer.setFields(fields);
			} catch (DBFException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 一条条的写入记录
			Object[] rowData = new Object[3];
			rowData[0] = "我是李华生";
			rowData[1] = "John";
			rowData[2] = new Double(5000.00);
			writer.addRecord(rowData);
			rowData = new Object[3];
			rowData[0] = "什么啊";
			rowData[1] = "Lalit";
			rowData[2] = new Double(3400.00);
			writer.addRecord(rowData);
			rowData = new Object[3];
			rowData[0] = "你是谁";
			rowData[1] = "Rohit";
			rowData[2] = new Double(7350.00);
			writer.addRecord(rowData);
			// 定义输出流，并关联的一个文件
			fos = new FileOutputStream(path);
			// 写入数据
			writer.write(fos);
			// writer.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbfUtil rw = new DbfUtil();
		//rw.writeDBF("D:\\testdbf.dbf");
		//rw.readDBF("D:\\testdbf.dbf");
		
		//rw.readDBF("D:\\Document\\temp\\dbf\\USERS01.DBF");
		//rw.readDBF("D:\\Document\\temp\\dbf\\UNDOTBS01.DBF");
		rw.readDBF("D:\\Document\\temp\\dbf\\SYSTEM01.DBF");
	}

}
