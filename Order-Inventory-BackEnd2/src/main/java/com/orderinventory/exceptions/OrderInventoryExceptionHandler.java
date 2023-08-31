package com.orderinventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * @ControllerAdvice annotation is used in Spring MVC to define global exception handlers and model attribute methods that are applicable to all controllers in an application. 
 */
@ControllerAdvice
public class OrderInventoryExceptionHandler {

	/*
	 * @ExceptionHandler annotation is used in Spring MVC to handle exceptions that occur during the execution of controller methods.
	 */
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<CustomErrorResponse> handleException(NotFoundException exe) {

		CustomErrorResponse st = new CustomErrorResponse();
		st.setStatus(HttpStatus.NOT_FOUND.value());
		st.setMessage(exe.getMessage());
		st.setTimestamp(System.currentTimeMillis());

		return new ResponseEntity<CustomErrorResponse>(st, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(value=InvaliddataException.class)
	
	public ResponseEntity<CustomErrorResponse> handleException(Exception exe) {

		CustomErrorResponse st = new CustomErrorResponse();
		st.setStatus(HttpStatus.BAD_REQUEST.value());
		st.setMessage(exe.getMessage());
		st.setTimestamp(System.currentTimeMillis());

		return new ResponseEntity<CustomErrorResponse>(st, HttpStatus.BAD_REQUEST);

	}

	

}
