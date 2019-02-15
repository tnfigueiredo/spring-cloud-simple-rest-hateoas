package com.sample.tnfigueiredo.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.tnfigueiredo.exception.SampleException;
import com.sample.tnfigueiredo.model.Book;
import com.sample.tnfigueiredo.service.BookService;
import com.sample.tnfigueiredo.vo.SearchVO;

/**
 * 
 * @author tnfigueiredo
 *
 */
@RestController
@RequestMapping(value = "/books", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BooksController {
	
	private static final Logger LOGGER = Logger.getLogger(BooksController.class.getName());
	
	@Autowired
	private BookService bookService;
	
	/**
	 * Creating a list of books as resources
	 * 
	 * 
	 * @param pageSize
	 * @param currentPage
	 * 
	 * @return all books
	 * 
	 * @throws SampleException
	 */
	@RequestMapping(params= {"pageSize", "currentPage"})
	@GetMapping
	public HttpEntity<Resource<SearchVO<Book>>> listBooks(
			@RequestParam(value="pageSize", required=false) int pageSize, 
			@RequestParam(value="currentPage", required=false) int currentPage) throws SampleException{
		int startIndex = (pageSize!=0?pageSize:5)*(currentPage!=0?currentPage:1) - (pageSize - 1);
		SearchVO<Book> searchVo = new SearchVO<>(createBookResources(bookService.listAll(startIndex, pageSize)), pageSize);
		Resource<SearchVO<Book>> books = new Resource<>(searchVo);
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	/**
	 * Getting a book on detais.
	 * 
	 * @param id
	 * 
	 * @return book represented by the param id
	 * 
	 * @throws SampleException 
	 */
	@GetMapping("/{id}")
	public HttpEntity<Resource<Book>> getBookById(@PathVariable("id") Long id) throws SampleException{
		Resource<Book> book = new Resource<>(bookService.findById(id));
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	/**
	 * Getting a book on detais.
	 * 
	 * @param id
	 * @param bookToUpdate
	 * 
	 * @return book represented by the param id
	 * 
	 * @throws SampleException
	 */
	@PutMapping("/{id}")
	public HttpEntity<Resource<Book>> updateBookById(@PathVariable("id") Long id, @RequestBody Book bookToUpdate) throws SampleException{
		bookToUpdate.setId(id);
		Book bookToReturn = bookService.saveUpdate(bookToUpdate);
		
		Resource<Book> book = new Resource<>(bookToReturn,
				linkTo(methodOn(BooksController.class).getBookById(bookToReturn.getId())).withSelfRel());
		
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	/**
	 * Getting a book on detais.
	 * 
	 * @param id
	 * @param bookToUpdate
	 * 
	 * @return book represented by the param id
	 * 
	 * @throws SampleException
	 */
	@PatchMapping("/{id}")
	public HttpEntity<Resource<Book>> updatePartialBookById(@PathVariable("id") Long id, @RequestBody Book bookToUpdate) throws SampleException{
				
		Book bookToReturn = bookService.findById(id);
		
		Resource<Book> book = new Resource<>(bookToReturn,
				linkTo(methodOn(BooksController.class).getBookById(bookToReturn.getId())).withSelfRel());
		
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	/**
	 * 
	 * @param bookToCreate
	 * @return
	 * @throws SampleException
	 */
	@PostMapping
	public HttpEntity<Resource<Book>> createkBook(@RequestBody Book bookToCreate) throws SampleException{
		Book bookToReturn = bookService.saveUpdate(bookToCreate);
		
		Resource<Book> book = new Resource<>(bookToReturn,
				linkTo(methodOn(BooksController.class).getBookById(bookToReturn.getId())).withSelfRel());
		
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	/*
	 * Transforming all books in resources
	 */
	private List<Resource<Book>> createBookResources(List<Book> books){
		List<Resource<Book>> objectToReturn = new ArrayList<>();
		
		books.forEach(book -> {
			try {
				objectToReturn.add(new Resource<Book>(book, 
					linkTo(methodOn(BooksController.class).getBookById(book.getId())).withSelfRel()
				));
			} catch (SampleException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		});
		
		return objectToReturn;
	}
}
