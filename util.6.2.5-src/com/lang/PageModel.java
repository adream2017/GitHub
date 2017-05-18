/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageModel<T> {
	private int pageSize;
	private int pageNo;
	private int totalPages;
	private int totalRecords;
	private List<T> data;
	private Map<Object, Object> map;

	public PageModel() {
		this(4, 0);
	}

	public PageModel(int pageSize) {
		this(pageSize, 0);
	}

	public PageModel(int pageSize, int totalRecords) {
		this.data = new ArrayList();
		this.map = new HashMap();

		this.pageSize = ((pageSize < 0) ? 0 : pageSize);
		this.totalRecords = ((totalRecords < 0) ? 0 : totalRecords);
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = ((pageSize < 0) ? 0 : pageSize);
	}

	public int getPageNo() {
		if ((this.totalPages <= 1) || (this.pageNo > this.totalPages))
			return this.totalPages;
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = ((pageNo < 0) ? 0 : pageNo);
		setTotalRecords(this.pageNo * this.pageSize);
	}

	public int getTotalRecords() {
		return this.totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = ((totalRecords < 0) ? 0 : totalRecords);
		int totalPages = (int) Math.ceil(this.totalRecords / this.pageSize);
		if ((totalPages <= 1) || (this.pageNo > totalPages))
			this.pageNo = totalPages;
		this.totalPages = totalPages;
	}

	public int getTotalPages() {
		return this.totalPages;
	}

	public List<T> getData() {
		return this.data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Map<Object, Object> getMap() {
		return this.map;
	}

	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}

	public int getPreviousPageNo() {
		return ((this.pageNo <= 1) ? 1 : (this.pageNo <= 0) ? 0
				: this.pageNo - 1);
	}

	public int getNextPageNo() {
		return ((this.pageNo >= getTotalPages()) ? getTotalPages()
				: (this.pageNo <= 0) ? 0 : this.pageNo + 1);
	}
}