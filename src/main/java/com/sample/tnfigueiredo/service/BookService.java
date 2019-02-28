package com.sample.tnfigueiredo.service;

import com.sample.tnfigueiredo.exception.SampleException;
import com.sample.tnfigueiredo.model.Book;
import com.sample.tnfigueiredo.vo.SearchVO;

public interface BookService {

	/**
	 * 
	 * List all Books.
	 * 
	 * 
	 * @param startIndex
	 * @param numberItens
	 *
	 * @return return all books registered
	 * @throws SampleException
	 */
	SearchVO<Book> listAll(int startIndex, int numberItens) throws SampleException;
	
	/**
	 * Find a Book by Id.
	 * 	
	 * @param id - book id
	 * 
	 * @return the Book for that id.
	 * 
	 * @exception SampleException
	 */
	Book findById(Long id) throws SampleException;
	
	/**
	 * Save or Update a book.
	 * 
	 * @param book - book to be updated.
	 * 
	 * @return book to save or update
	 * 
	 * @exception SampleException
	 */
	Book saveUpdate(Book book) throws SampleException;
	
	/**
	 * Book to be deleted by id.
	 * 
	 * @param id
	 */
	void delete(Long id);
	
}
