/**
 * 
 */
package com.sample.tnfigueiredo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Sample exception
 * 
 * @author tnfigueiredo
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class SampleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default Constructor to Handle exceptions at the project.
	 * 
	 * @param e
	 */
	public SampleException(Exception e) {
		super("Problem at project execution", e);
	}

}
