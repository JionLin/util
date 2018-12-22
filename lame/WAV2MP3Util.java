package cn.itcast.common.util.lame;

import java.util.Properties;

import cn.itcast.common.util.StreamGobbler;

public class WAV2MP3Util {
	/**
	 * 
	 * 
	 * @return
	 */
	public static boolean execute(String executeScriptPath, String wavFilePath,
			String mp3FilePath) {
		String cmd = executeScriptPath + " " + wavFilePath + " " + mp3FilePath;
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			StreamGobbler errorGobbler = new StreamGobbler(
					proc.getErrorStream(), "Error");
			StreamGobbler outputGobbler = new StreamGobbler(
					proc.getInputStream(), "Output");
			errorGobbler.start();
			outputGobbler.start();
			proc.waitFor();
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 将wav文件转换为mp3文件工具类
	 * 
	 * @param srcPath
	 *            源路径
	 * @param destPath
	 *            目的路径
	 * @return
	 */
	public static boolean wavToMp3(String srcPath, String destPath) {
		Properties prop = System.getProperties();
		String osName = prop.getProperty("os.name");
		if (osName.contains("Linux")) {
			return WAV2MP3Util.execute("/usr/local/lame/bin/lame -V 7 ",
					srcPath, destPath);
		} else if (osName.matches("^(?i)Windows.*$")) {
			String commdPath = WAV2MP3Util.class.getResource("").getPath();
			return WAV2MP3Util.execute(commdPath + "/lame.bat", srcPath,
					destPath);
		}
		return false;
	}
}
