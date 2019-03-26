/**
 * 
 */
package com.sample.tnfigueiredo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

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
	
	private BookBuilder bookBuilder;
	
	/**
	 * Creating books from test file simillar to main csv.
	 * @throws IOException
	 */
	@Before
	public void init() {
		books = new Hashtable<>();
		bookBuilder = new BookBuilder();
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
	
	@Test
	public void recoverExistingBookTest() throws SampleException {
		when(booksMock.get(1L)).thenReturn(books.get(1L));
		Book book = bookService.findById(1L);
		assertNotNull(book);
		assertSame(book.getId(), Long.valueOf(1L));
	}
	
	@Test
	public void recoverNonExistingBookTest() throws SampleException {
		when(booksMock.get(books.size() + 1L)).thenReturn(books.get(books.size() + 1L));
		Book book = bookService.findById(books.size() + 1L);
		assertNull(book);
	}
	
	@Test
	public void removeExistingBookTest() throws SampleException {
		int sizeBooks = books.size();
		when(booksMock.remove(1L)).thenReturn(books.remove(1L));
		bookService.delete(1L);
		assertNull(books.get(1L));
		assertSame(books.size(), sizeBooks - 1);
	}
	
	@Test
	public void removeNonExistingBookTest() throws SampleException {
		int sizeBooks = books.size();
		when(booksMock.remove(books.size() + 1L)).thenReturn(books.remove(books.size() + 1L));
		bookService.delete(books.size() + 1L);
		assertNull(books.get(books.size() + 1L));
		assertSame(books.size(), sizeBooks);
	}
	
	@Test
	public void addNewBookTest() throws SampleException {
		int sizeBooks = books.size();
		List<Long> keys = new ArrayList<>();
		keys.addAll(books.keySet());
		Collections.sort(keys);
		bookBuilder.clear();
		Book newBook = bookBuilder.setAuthor("New Author").setPrice(50.00).setTitle("New Title").build();
		when(booksMock.keySet()).thenReturn(books.keySet());
		when(booksMock.put(keys.get(keys.size() - 1) + 1, newBook))
			.thenReturn(books.put(keys.get(keys.size() - 1) + 1, newBook));
		Book bookInserted = bookService.saveUpdate(newBook);
		assertNotNull(bookInserted);
		assertSame(books.size(), sizeBooks + 1);
	}
	
	@Test
	public void updateExistingBookTest() throws SampleException {
		int sizeBooks = books.size();
		bookBuilder.clear();
		Book bookToUpdate = bookBuilder.setId(8L).setAuthor("New Author").setPrice(50.00).setTitle("New Title").build();
		when(booksMock.get(bookToUpdate.getId())).thenReturn(books.get(bookToUpdate.getId()));
		when(booksMock.containsKey(bookToUpdate.getId())).thenReturn(books.containsKey(bookToUpdate.getId()));
		when(booksMock.put(bookToUpdate.getId(), bookToUpdate)).thenReturn(books.put(bookToUpdate.getId(), bookToUpdate));
		Book bookUpdated = bookService.saveUpdate(bookToUpdate);
		assertNotNull(bookUpdated);
		assertSame(books.size(), sizeBooks);
		assertSame(bookToUpdate, bookUpdated);
		assertSame(bookToUpdate.getAuthor(), bookUpdated.getAuthor());
		assertSame(bookToUpdate.getId(), bookUpdated.getId());
		assertSame(bookToUpdate.getPrice(), bookUpdated.getPrice());
		assertSame(bookToUpdate.getTitle(), bookUpdated.getTitle());
	}
	
	@Test(expected=SampleException.class)
	public void updateNonExistingBookTest() throws SampleException {
		List<Long> keys = new ArrayList<>();
		keys.addAll(books.keySet());
		Collections.sort(keys);
		bookBuilder.clear();
		Book bookToUpdate = 
				bookBuilder
					.setId(keys.get(keys.size() - 1) + 1)
					.setAuthor("New Author")
					.setPrice(50.00)
					.setTitle("New Title")
					.build();
		when(booksMock.get(bookToUpdate.getId())).thenReturn(books.get(bookToUpdate.getId()));
		when(booksMock.containsKey(bookToUpdate.getId())).thenReturn(books.containsKey(bookToUpdate.getId()));
		bookService.saveUpdate(bookToUpdate);
	}

}
