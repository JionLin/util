package cn.itcast.common.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * 
 * <p>
 * jfreechart生成图表的根据类
 * </p>
 * 
 * @ClassName: JFreeChartUtil
 * 
 */
public class JFreeChartUtil {


	/**
	 * 
	 * <P>
	 * 饼图
	 * </P>
	 * 
	 * @param dataset
	 * @param title
	 * @param is3D
	 * @return
	 */
	private static JFreeChart createPieChart(DefaultPieDataset dataset,
			String title, boolean is3D) {
		JFreeChart chart = null;
		if (is3D) {
			chart = ChartFactory.createPieChart(title, // 图表标题
					dataset, // 数据集
					true, // 是否显示图例
					true, // 是否显示工具提示
					true // 是否生成URL
					);
		} else {
			chart = ChartFactory.createPieChart(title, // 图表标题
					dataset, // 数据集
					true, // 是否显示图例
					true, // 是否显示工具提示
					true // 是否生成URL
					);
		}
		// 设置标题字体==为了防止中文乱码：必须设置字体
		chart.setTitle(new TextTitle(title, new Font("黑体", Font.ITALIC, 22)));
		// 设置图例的字体==为了防止中文乱码：必须设置字体
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 12));
		// 获取饼图的Plot对象(实际图表)
		PiePlot plot = (PiePlot) chart.getPlot();
		// 图形边框颜色
		plot.setBaseSectionOutlinePaint(Color.GRAY);
		// 图形边框粗细
		plot.setBaseSectionOutlineStroke(new BasicStroke());
		// 设置饼状图的绘制方向，可以按顺时针方向绘制，也可以按逆时针方向绘制
		plot.setDirection(Rotation.ANTICLOCKWISE);
		// 设置绘制角度(图形旋转角度)
		plot.setStartAngle(70);
		// 设置突出显示的数据块
		// plot.setExplodePercent("One",0.1f );
		// 设置背景色透明度
		plot.setBackgroundAlpha(0.7f);
		// 设置前景色透明度
		plot.setForegroundAlpha(0.65F);
		// 设置区块标签的字体==为了防止中文乱码：必须设置字体
		plot.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		// 扇区分离显示,对图不起效
		if (is3D)
			plot.setExplodePercent(dataset.getKey(3), 0.1D);
		// 图例显示百分比:自定义方式，{ 表示选项， { 表示数值， { 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		// 图例显示百分比
		// plot.setLegendLabelGenerator(new
		// StandardPieSectionLabelGenerator("{={({)"));
		// 指定显示的饼图为：圆形(true) 还是椭圆形(false)
		plot.setCircular(true);
		// 没有数据的时候显示的内容
		plot.setNoDataMessage("找不到可用数据...");

		// 设置鼠标悬停提示
		plot.setToolTipGenerator(new StandardPieToolTipGenerator());
		// 设置热点链接
		// plot.setURLGenerator(new StandardPieURLGenerator("detail.jsp"));

		return chart;
	}

	/**
	 * 
	 * <P>
	 * 柱状图
	 * </P>
	 * 
	 * @param dataset
	 * @param title
	 * @param x
	 * @param y
	 * @param is3D
	 * @return
	 */
	public static JFreeChart createBarChart(CategoryDataset dataset,
			String title, String x, String y, boolean is3D) {
		JFreeChart chart = null;
		if (is3D) {
			chart = ChartFactory.createBarChart3D( // 柱状图
					// JFreeChart chart = ChartFactory.createLineChart( //折线图
					title, // 图表的标题
					x, // 目录轴的显示标签
					y, // 数值轴的显示标签
					dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方式：V垂直;H水平
					true, // 是否显示图例
					false, // 是否显示工具提示
					false // 是否生成URL
					);
		} else {
			chart = ChartFactory.createBarChart( // 柱状图
					// JFreeChart chart = ChartFactory.createLineChart( //折线图
					title, // 图表的标题
					x, // 目录轴的显示标签
					y, // 数值轴的显示标签
					dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方式：V垂直;H水平
					true, // 是否显示图例
					false, // 是否显示工具提示
					false // 是否生成URL
					);
		}

		// ===============为了防止中文乱码：必须设置字体
		chart.setTitle(new TextTitle(title, new Font("黑体", Font.ITALIC, 22)));// 22

		LegendTitle legend = chart.getLegend(); // 获取图例
		legend.setItemFont(new Font("宋体", Font.BOLD, 12)); // 设置图例的字体，防止中文乱码

		CategoryPlot plot = (CategoryPlot) chart.getPlot(); // 获取柱图的Plot对象(实际图表)
		// 设置柱图背景色（注意，系统取色的时候要使用坏哪Ｊ嚼床榭囱丈嗦耄庋冉献既罚?
		plot.setBackgroundPaint(new Color(255, 255, 204));
		plot.setForegroundAlpha(0.65F); // 设置前景色透明度
		// 设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.gray);

		CategoryAxis h = plot.getDomainAxis(); // 获取x轴
		h.setMaximumCategoryLabelWidthRatio(1.0f);// 横轴上的 Lable 是否完整显示
		h.setLabelFont(new Font("宋体", Font.BOLD, 12));// 设置字体，防止中文乱码
		h.setTickLabelFont(new Font("宋体", Font.BOLD, 12));// 轴数值

		plot.getRangeAxis().setLabelFont(new Font("宋体", Font.BOLD, 12)); // Y轴设置字体，防止中文乱码
		// 设定柱子的属性
		org.jfree.chart.axis.ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setUpperMargin(0.1);// 设置最高的一个柱与图片顶端的距离(最高柱的10%)

		// 柱图的呈现器*/
		BarRenderer renderer = new BarRenderer();
		// 设置柱子宽度
		renderer.setMaximumBarWidth(0.1);
		// 设置柱子高度
		// renderer.setMinimumBarLength(40);
		// 设置每个柱的颜色
		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesPaint(1, Color.yellow);
		renderer.setSeriesPaint(2, Color.green);
		// 设置柱子边框颜色
		renderer.setSeriesOutlinePaint(0, Color.BLACK);
		renderer.setSeriesOutlinePaint(1, Color.BLACK);
		renderer.setSeriesOutlinePaint(2, Color.BLACK);
		// 设置柱子边框可见
		renderer.setDrawBarOutline(true);

		// 设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.03);
		// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		plot.setBackgroundAlpha(0.9f);
		// 设置前景色透明度（0~1）
		plot.setForegroundAlpha(0.5f);

		// 给柱图添加呈现器
		plot.setRenderer(renderer);

		// 没有数据的时候显示的内容
		plot.setNoDataMessage("找不到可用数据...");
		return chart;
	}

	/**
	 * 生成柱状图
	 * 
	 * @param data
	 * @param title
	 * @param x
	 * @param y
	 * @param width
	 * @param heigth
	 * @param request
	 * @param user_id
	 *            用户id
	 * @return
	 */
	public static String generateBarChart(CategoryDataset data, String title,
			String x, String y, int width, int heigth, boolean is3D) {

		JFreeChart chart = createBarChart(data, title, x, y, is3D);

		String filePath = cn.itcast.common.util.PropertiesUtils.getPropValue("report.path");
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdir();
		}
		long currentTime = System.currentTimeMillis();
		String fileName = currentTime + ".jpg";
		drawToOutputStream(filePath + fileName, chart, width, heigth);
		String chartPath = cn.itcast.common.util.PropertiesUtils.getPropValue("chart.path");
		return chartPath + fileName;
	}

	/**
	 * step 输出图表到指定的磁盘
	 * 
	 * @param destPath
	 * @param chart
	 */
	public static String drawToOutputStream(String destPath, JFreeChart chart,
			int width, int heigth) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(destPath);
			ChartUtilities.writeChartAsJPEG(fos, // 指定目标输出流
					chart, // 图表对象
					width, // 宽
					heigth, // 高
					null); // ChartRenderingInfo信息
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e);
			}

		}
		return destPath;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
