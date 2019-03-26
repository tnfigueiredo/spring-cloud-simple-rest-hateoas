/**
 * 
 */
package com.sample.tnfigueiredo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sample.tnfigueiredo.controllers.BooksController;
import com.sample.tnfigueiredo.exception.SampleException;
import com.sample.tnfigueiredo.service.BookService;

/**
 * @author tnfigueiredo
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BooksController.class)
public class BooksControllerIntegrationTest extends AbstractBooksControllerTest{

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
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
	 * Evaluate integration test for controller requesting a 1st page.
	 * 
	 * @throws Exception
	 */
	@Test
	public void listAllTestPage1() throws Exception	 {
		when(bookServiceMock.listAll(1, 5)).thenReturn(getSearchVO(1, 5));
		
		mockMvc.perform(
				get(BASE_PATH)
				.param("pageSize", "5")
				.param("currentPage", "1")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	/**
	 * Evaluate integration test for controller requesting only 1st page and no page size.
	 * 
	 * @throws Exception
	 */
	@Test
	public void listAllTestPage1NoPageSize() throws Exception	 {
		when(bookServiceMock.listAll(1, 5)).thenReturn(getSearchVO(1, 5));
		
		mockMvc.perform(
				get(BASE_PATH)
				.param("currentPage", "1")
				)
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	/**
	 * Evaluate integration test for controller requesting a 1st page.
	 * 
	 * @throws Exception
	 */
	@Test
	public void listAllTestPage1OnlyPageSize() throws Exception	 {
		when(bookServiceMock.listAll(1, 5)).thenReturn(getSearchVO(1, 5));
		
		mockMvc.perform(
				get(BASE_PATH)
				.param("pageSize", "5")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	/**
	 * Evaluate integration test for controller requesting no page parameter.
	 * 
	 * @throws Exception
	 */
	@Test
	public void listAllTestPage1DefaultWithMidleSize() throws Exception	 {
		when(bookServiceMock.listAll(1, 5)).thenReturn(getSearchVO(1, 5));
		
		mockMvc.perform(get(BASE_PATH))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	/**
	 * Check if controller fails when service receives and exception for exceding page size.
	 * 
	 * @throws Exception 
	 */
	@Test
	public void listAllTestPageSizeGreatherThanBookListSize() throws Exception {
		when(bookServiceMock.listAll(1, getBooksForTest().size() + 1)).thenThrow(new SampleException("Simulate invalid page."));
		
		mockMvc.perform(
				get(BASE_PATH)
				.param("pageSize", Integer.toString(getBooksForTest().size()+1))
				.param("currentPage", "1")
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
		
	}
	
}
