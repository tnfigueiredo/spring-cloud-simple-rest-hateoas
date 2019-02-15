/**
 * 
 */
package com.sample.tnfigueiredo.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.sample.tnfigueiredo.exception.SampleException;
import com.sample.tnfigueiredo.model.Book;
import com.sample.tnfigueiredo.service.BookService;

/**
 * Sample for a CRUD Service for Books 
 * 
 * @author tnfigueiredo
 *
 */
@Service(value = "bookService")
public class BookServiceImpl implements BookService {
	
	private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());
	
	private Hashtable<Long, Book> books = null;
	
	/*
	 * (non-Javadoc)
	 * @see com.sample.tnfigueiredo.service.BookService#listAll()
	 */
	public List<Book> listAll(int startIndex, int numberItens) throws SampleException{
		List<Book> result = new ArrayList<>();
		List<Book> allBooks = Collections.list(getBooks().elements());
		for(int i = startIndex - 1; (i < allBooks.size() && i < startIndex + numberItens); i++) {
			result.add(allBooks.get(i));
		}
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sample.tnfigueiredo.service.BookService#findById(java.lang.Long)
	 */
	public Book findById(Long id) throws SampleException {
		return getBooks().get(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sample.tnfigueiredo.service.BookService#saveUpdate(com.sample.tnfigueiredo.model.Book)
	 */
	public Book saveUpdate(Book book) throws SampleException {
		if(book == null || (book.getId() != null && !getBooks().containsKey(book.getId()))) {
			throw new SampleException(new IllegalArgumentException("Book empty or Id does not exist."));
		}
		
		if(book.getId() == null) {
			book.setId(getNextBookId());
		}
		
		getBooks().put(book.getId(), book);
		
		return book;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sample.tnfigueiredo.service.BookService#delete(java.lang.Long)
	 */
	public void delete(Long id) {
		
	}
	
	/**
	 * @return the books
	 * @throws SampleException 
	 */
	private Hashtable<Long, Book> getBooks() throws SampleException {
		if(this.books == null) {
			books = new Hashtable<>();
			try (Stream<String> stream = 
					new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:books.csv"))).lines()) {
				
			    stream.forEach(line ->{
			    	Book book = new Book(line);
			    	books.put(book.getId(), book);
			    });
			    
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				throw new SampleException(e);
			}
		}
		return books;
	}
	
	/*
	 * Get the last higher key
	 */
	private Long getNextBookId() throws SampleException {
		List<Long> keys = Collections.emptyList();
		keys.addAll(getBooks().keySet());
		Collections.sort(keys);
		return keys.get(keys.size() - 1);
	}

}
