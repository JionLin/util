package cn.itcast.common.util.keywords;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import cn.itcast.common.util.StringUtil;

public class KeywordsUtil {

	/**
	 * 从指定的文件读关键词
	 * 
	 * @param filePath
	 * @return 不重复的关键词列表
	 */
	public static Set<String> readKeywords(String filePath) {

		if (StringUtil.isEmpty(filePath)) {
			return null;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		Set<String> keywords = new HashSet<String>();
		BufferedReader in = null;
		InputStreamReader ir = null;
		try {
			// 设置中文编码
			ir = new InputStreamReader(new FileInputStream(file), "gbk");
			in = new BufferedReader(ir);
			String line = in.readLine();
			keywords.add(line);
			while (line != null) {
				line = in.readLine();
				if (line != null) {
					System.out.println(line.trim());
					keywords.add(line.trim());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return keywords;
	}

	/**
	 * 从指定的集合中读取数据写入指定文件
	 * 
	 * @param keywords
	 * @param destFilePath
	 *            目标文件绝对路径
	 */
	public static void writeKeywords(Set<String> keywords, String destFilePath) {

		if (keywords == null || keywords.size() == 0) {
			return;
		}
		PrintWriter writer = null;
		BufferedWriter bw = null;
		try {
			writer = new PrintWriter(new File(destFilePath));
			bw = new BufferedWriter(writer);
			for (String key : keywords) {
				bw.write(key);
				bw.newLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		String sourceFile = "D:\\tmp\\keywords\\keywords.txt";
		String destFile = "D:\\tmp\\keywords\\mydict.dic";
		Set<String> keywords = readKeywords(sourceFile);
		System.out.println(keywords.size());
		writeKeywords(keywords, destFile);
	}
}
