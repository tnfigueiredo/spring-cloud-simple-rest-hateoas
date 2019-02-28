/**
 * 
 */
package com.sample.tnfigueiredo.utils;

import com.sample.tnfigueiredo.model.Book;

/**
 * Builder for mock books.
 * 
 * @author tnfigueiredo
 *
 */
public class BookBuilder {

	private Long id;
	
	private String title;
	
	private Double price;
	
	private String author;
	
	/**
	 * Return book to build;
	 * 
	 * @return
	 */
	public Book build() {
		Book book = new Book();
		book.setId(this.id);
		book.setAuthor(this.author);
		book.setPrice(this.price);
		book.setTitle(this.title);
		return book;
	}
	
	/**
	 * Clear builder fields
	 */
	public void clear() {
		this.id = null;
		this.title = null;
		this.price = null;
		this.author = null;
	}

	/**
	 * @param id the id to set
	 */
	public BookBuilder setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @param title the title to set
	 */
	public BookBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * @param price the price to set
	 */
	public BookBuilder setPrice(Double price) {
		this.price = price;
		return this;
	}

	/**
	 * @param author the author to set
	 */
	public BookBuilder setAuthor(String author) {
		this.author = author;
		return this;
	}
	
	
}
