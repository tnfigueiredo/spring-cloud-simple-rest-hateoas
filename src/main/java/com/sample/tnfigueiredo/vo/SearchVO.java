/**
 * 
 */
package com.sample.tnfigueiredo.vo;

import java.util.List;

/**
 * @author tnfigueiredo
 *
 */
public class SearchVO<T> {
	
	private int total;
	
	private List<T> items;

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the books
	 */
	public List<T> getItems() {
		return items;
	}

	/**
	 * @param books the books to set
	 */
	public void setItems(List<T> books) {
		this.items = books;
	}

}
