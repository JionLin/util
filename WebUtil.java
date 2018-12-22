package cn.itcast.common.util;

public class WebUtil {

	/**
	 * 根据时间数字返回对应的时间格式(1'34")
	 * 
	 * @param time
	 * @return
	 */
	public static String convertNumToTime(int second) {
		if (second > 1000) {// 返回值可能是毫秒,取整数
			second = second / 1000;
		}
		int minute = second / 60;
		int sec = second % 60;
		String time = "";
		if (second <= 0) {
			return time;
		}
		if (minute > 0) {
			time += minute + "'";
		}
		if (sec > 0) {
			if (minute > 0 && sec < 10) {
				time += "0" + sec + "\"";
			} else {
				time += sec + "\"";
			}
		} else if (minute > 0 && sec == 0) {
			time += "00\"";
		}
		return time;
	}
}
