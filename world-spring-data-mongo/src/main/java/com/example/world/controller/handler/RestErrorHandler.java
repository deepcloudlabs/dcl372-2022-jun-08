package com.example.world.controller.handler;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.world.dto.error.ErrorResponse;

@RestControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleAllOtherExceptions(Exception e) {
		return new ErrorResponse("FAILED", e.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public ErrorResponse handleRuntimeExceptions(RuntimeException e) {
		return new ErrorResponse("FAILED", e.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
		return e.getConstraintViolations().stream()
				                          .map(cv -> new ErrorResponse("validation error", cv.getMessage()))
				                          .toList();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return e.getBindingResult()
				.getAllErrors()
				.stream()
				.map(oe -> new ErrorResponse("validation error", oe.getDefaultMessage()))
				.toList();
	}	
}
