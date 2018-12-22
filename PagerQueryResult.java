package cn.itcast.common.util;

import java.io.Serializable;
import java.util.List;

/**
 * 进行分页查询的结果封装类
 *
 * @param <T>
 */
public class PagerQueryResult<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1276004896658177046L;

	/**
	 * 当前页码
	 */
	private int page=1;
	/**
	 * 页面条目数，即每个页面上有多少条记录
	 */
	private int pageSize;
	
	
	/**
	 * 题目数量，即共有多少条记录
	 */
	private long itemCount;
	
	/**
	 * 查询结果列表
	 */
	private List<T> resultList;

	/**
	 * 获取页码
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置页码
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 获取页面条目数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置页面条目数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取页面总数
	 * @return
	 */
	public long getPageCount() {
		if(itemCount==0)return 0;
		if(pageSize==0)return 0;
		long pageTotal=itemCount/pageSize;
		if(itemCount%pageSize!=0)pageTotal+=1;
		return pageTotal;
	}


	/**
	 * 获取结果列表
	 * @return
	 */
	public List<T> getResultList() {
		return resultList;
	}

	/**
	 * 设置结果列表
	 * @param resultList
	 */
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取条目数量
	 * @return
	 */
	public long getItemCount() {
		return itemCount;
	}

	/**
	 * 设置条目数量
	 * @param itemCount
	 */
	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}
	
	
}
