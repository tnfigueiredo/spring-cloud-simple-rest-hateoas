/**
 * 
 */
package com.sample.tnfigueiredo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;

import com.sample.tnfigueiredo.controllers.BooksController;
import com.sample.tnfigueiredo.exception.SampleException;
import com.sample.tnfigueiredo.model.Book;
import com.sample.tnfigueiredo.service.BookService;
import com.sample.tnfigueiredo.vo.ResearchSearchVO;
import com.sample.tnfigueiredo.vo.SearchVO;

/**
 * @author tnfigueiredo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BooksControllerTest extends AbstractBooksControllerTest {
	
	@InjectMocks
	private BooksController booksControllerMock;
	
	@Mock
	private BookService bookServiceMock;
	
	/*
	 * (non-Javadoc)
	 * @see com.sample.tnfigueiredo.controller.AbstractBooksControllerTest#init()
	 */
	@Before
	@Override
	public void init() {
		super.init();
	}

	/**
	 * Check if controller list elements with default page size and starting index. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void listAllTestPage1DefaultWithMidleSize() throws Exception {
		SearchVO<Book> searchVO = getSearchVO(1, 5);
		when(bookServiceMock.listAll(1, 5)).thenReturn(searchVO);
		HttpEntity<Resource<ResearchSearchVO<Book>>> result =  booksControllerMock.listBooks(5, 1);
		assertNotNull(result);
		ResearchSearchVO<Book> researchSearchVO = result.getBody().getContent(); 
		assertSame(researchSearchVO.getTotal(), getBooksForTest().size());
		assertSame(researchSearchVO.getCurrentPage(), 1);
		assertSame(researchSearchVO.getPageSize(), 5);
		researchSearchVO.getResult().stream().forEach(item ->{
			assertTrue(searchVO.getItems().contains(item.getContent()));
			searchVO.getItems().remove(item.getContent());
		});
		assertEquals(searchVO.getItems().size(), 0);
	}
	
	/**
	 * Check if controller fails when service receives and exception for exceding page size.
	 * 
	 * @throws SampleException
	 */
	@Test(expected=SampleException.class)
	public void listAllTestPageSizeGreatherThanBookListSize() throws SampleException {
		when(bookServiceMock.listAll(1, getBooksForTest().size() + 1)).thenThrow(new SampleException("Simulate invalid page."));
		booksControllerMock.listBooks(getBooksForTest().size() + 1, 1);
	}
	
	/**
	 * Check if controller list elements with default page size 0 as starting index. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void listAllTestPage0WithMidleSize() throws SampleException {
		SearchVO<Book> searchVO = getSearchVO(1, 5);
		when(bookServiceMock.listAll(1, 5)).thenReturn(searchVO);
		HttpEntity<Resource<ResearchSearchVO<Book>>> result =  booksControllerMock.listBooks(5, 0);
		assertNotNull(result);
		ResearchSearchVO<Book> researchSearchVO = result.getBody().getContent(); 
		assertSame(researchSearchVO.getTotal(), getBooksForTest().size());
		assertSame(researchSearchVO.getCurrentPage(), 1);
		assertSame(researchSearchVO.getPageSize(), 5);
		researchSearchVO.getResult().stream().forEach(item ->{
			assertTrue(searchVO.getItems().contains(item.getContent()));
			searchVO.getItems().remove(item.getContent());
		});
		assertEquals(searchVO.getItems().size(), 0);
	}
	
	/**
	 * Check if controller fails when service receives and exception for a 
	 * page size greather than collection size.
	 * 
	 * @throws SampleException
	 */
	@Test(expected=SampleException.class)
	public void listAllTestPageGreatherThanBookListSize() throws SampleException {
		when(bookServiceMock.listAll(getBooksForTest().size() + 1, 5)).thenThrow(new SampleException("Simulate invalid page."));
		booksControllerMock.listBooks(5, getBooksForTest().size() + 1);
	}

}
