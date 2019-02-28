/**
 * 
 */
package com.sample.tnfigueiredo.service;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sample.tnfigueiredo.exception.SampleException;
import com.sample.tnfigueiredo.model.Book;
import com.sample.tnfigueiredo.service.impl.BookServiceImpl;
import com.sample.tnfigueiredo.utils.BookBuilder;
import com.sample.tnfigueiredo.vo.SearchVO;

/**
 * UnitTest for BookService
 * 
 * @author tnfigueiredo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
	
	@Mock
	private Hashtable<Long, Book> booksMock = null;
	
	private Hashtable<Long, Book> books = null;
	
	@InjectMocks
	private BookServiceImpl bookService;
	
	
	/**
	 * Creating books from test file simillar to main csv.
	 * @throws IOException
	 */
	@Before
	public void init() {
		books = new Hashtable<>();
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
	
	private void addBook(Book book) {
		books.put(book.getId(), book);
	}
	
	@Test
	public void listAllTestPage1DefaultWithMidleSize() throws SampleException {
		when(booksMock.elements()).thenReturn(books.elements());
		when(booksMock.size()).thenReturn(books.size());
		SearchVO<Book> searchVO = bookService.listAll(1, 5);
		assertSame(books.size(), searchVO.getTotal());
		assertSame(5, searchVO.getItems().size());
	}
	
	@Test
	public void listAllTestPageSizeGreatherThanBookListSize() throws SampleException {
		when(booksMock.elements()).thenReturn(books.elements());
		when(booksMock.size()).thenReturn(books.size());
		SearchVO<Book> searchVO = bookService.listAll(1, books.size() + 1);
		assertSame(books.size(), searchVO.getTotal());
		assertSame(books.size(), searchVO.getItems().size());
	}
	
	@Test(expected=SampleException.class)
	public void listAllTestPage0WithMidleSize() throws SampleException {
		bookService.listAll(0, 5);
	}
	
	@Test(expected=SampleException.class)
	public void listAllTestPageGreatherThanBookListSize() throws SampleException {
		when(booksMock.size()).thenReturn(books.size());
		bookService.listAll(books.size() + 1, 5);
	}
	

}
