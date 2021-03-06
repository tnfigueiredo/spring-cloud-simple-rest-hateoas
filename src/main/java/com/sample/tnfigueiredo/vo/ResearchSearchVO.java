/**
 * 
 */
package com.sample.tnfigueiredo.vo;

import java.util.List;

import org.springframework.hateoas.Resource;

/**
 * Value Object to represent a Search result and its metadata. 
 *
 * @author tnfigueiredo
 *
 */
public class ResearchSearchVO<T extends Object> {
	
	private int pageSize;
	
	private int total;
	
	private int currentPage;
	
	private List<Resource<T>> result;

	public ResearchSearchVO(List<Resource<T>> searchResult, int pageSize, int totalSearchItens) {
		this.pageSize = pageSize;
		this.total = totalSearchItens;
		this.currentPage = 1;
		this.result = searchResult;
	}
	
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @return the result
	 */
	public List<Resource<T>> getResult() {
		return result;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
