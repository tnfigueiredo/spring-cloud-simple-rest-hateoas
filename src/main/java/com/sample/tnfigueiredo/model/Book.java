package com.sample.tnfigueiredo.model;

import java.util.StringTokenizer;

/**
 * Book representation for the sample REST Controller
 * 
 * @author tnfigueiredo
 *
 */
public class Book {
	
	private Long id;
	
	private String title;
	
	private Double price;
	
	private String author;
	
	public Book() {}
	
	public Book(String fileLine) {
		StringTokenizer tokens = new StringTokenizer(fileLine, ",");
		this.id = Long.valueOf(tokens.nextToken());
		this.title = tokens.nextToken();
		this.price = Double.valueOf(tokens.nextToken());
		this.author = tokens.nextToken();
	}
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Book)) {
			return false;
		}
        return this.getId().equals(((Book)obj).getId());
    }
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if(this.id == null) {
			return super.hashCode();
		}
		return this.id.hashCode();
	}

}
