package com.lytips.ITags.vo;

import java.io.Serializable;


@SuppressWarnings("serial")
public class PageInfo implements Serializable {
	
	
	private int page; // 当面页码
	private int limit; // 分页条数
	private int totalCount; // 总数
	private int totalPages; // 总页数
	private boolean firstPage; // 是否首页
	private boolean lastPage; // 是否尾页
	private boolean hasNextPage; // 是否有下一页
	private boolean hasPrePage; // 是否有上一页
	private Integer[] slider; // 中间页码
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public boolean isFirstPage() {
		return firstPage;
	}
	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}
	public boolean isLastPage() {
		return lastPage;
	}
	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public boolean isHasPrePage() {
		return hasPrePage;
	}
	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}
	public Integer[] getSlider() {
		return slider;
	}
	public void setSlider(Integer[] slider) {
		this.slider = slider;
	}
}
