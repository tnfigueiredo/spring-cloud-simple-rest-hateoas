/**
 * 
 */
package com.sample.tnfigueiredo.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import com.sample.tnfigueiredo.controllers.BooksController;
import com.sample.tnfigueiredo.model.Book;
import com.sample.tnfigueiredo.utils.BookBuilder;
import com.sample.tnfigueiredo.vo.SearchVO;

/**
 * Base class for operations used to test {@link BooksController} tests
 * 
 * @author tnfigueiredo
 *
 */
public class AbstractBooksControllerTest {
	
	protected final static String BASE_PATH = "/books";
	
	private Hashtable<Long, Book> booksForTest = null;
	
	/**
	 * Initial operation for starting tests configuration. Creating books from test file similar to main csv.
	 */
	public void init() {
		booksForTest = new Hashtable<>();
		BookBuilder bookBuilder = new BookBuilder();
		addBook(bookBuilder.setId(1L).setAuthor("Author 1").setTitle("My title 1").setPrice(20.00).build());
		addBook(bookBuilder.setId(2L).setAuthor("Author 2").setTitle("My title 2").setPrice(21.00).build());
		addBook(bookBuilder.setId(3L).setAuthor("Author 3").setTitle("My title 3").setPrice(22.00).build());
		addBook(bookBuilder.setId(4L).setAuthor("Author 4").setTitle("My title 4").setPrice(23.00).build());
		addBook(bookBuilder.setId(5L).setAuthor("Author 5").setTitle("My title 5").setPrice(24.00).build());
		addBook(bookBuilder.setId(6L).setAuthor("Author 6").setTitle("My title 6").setPrice(25.00).build());
		addBook(bookBuilder.setId(7L).setAuthor("Author 7").setTitle("My title 7").setPrice(26.00).build());
		addBook(bookBuilder.setId(8L).setAuthor("Author 8").setTitle("My title 8").setPrice(27.00).build());
	}
	
	/*
	 * Add books to initialization operation
	 */
	private void addBook(Book book) {
		booksForTest.put(book.getId(), book);
	}
	
	/**
	 * Recover a {@link SearchVO} based on index and page parameters.
	 * 
	 * @param startIndex
	 * @param pageSize
	 * 
	 * @return {@link SearchVO} resulting from and filtering operation.
	 */
	protected SearchVO<Book> getSearchVO(int startIndex, int pageSize){
		
		List<Book> booksFiltered = booksForTest.entrySet().stream()
				.filter(item -> item.getKey() >= startIndex && item.getKey() <= pageSize - (startIndex -1) )
				.map(item -> item.getValue())
				.collect(Collectors.toList()); 
		
		SearchVO<Book> searchVO = new SearchVO<>();
		searchVO.setItems(booksFiltered);
		searchVO.setTotal(booksForTest.keySet().size());
		return searchVO;
	}

	/**
	 * @return the booksForTest
	 */
	protected Hashtable<Long, Book> getBooksForTest() {
		return booksForTest;
	}

}
