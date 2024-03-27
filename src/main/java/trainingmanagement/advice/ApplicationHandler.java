package trainingmanagement.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import trainingmanagement.exception.CustomException;
import trainingmanagement.exception.ResourceNotFoundException;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.enums.EHttpStatus;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleInvalidException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getFieldErrors().forEach(err -> {
			errors.put(err.getField(), err.getDefaultMessage());
		});
		return new ResponseEntity<>(
			new ResponseWrapper<>(
				EHttpStatus.FAILURE,
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.name(),
				errors
			), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e) {
		return new ResponseEntity<>(
			new ResponseWrapper<>(
				EHttpStatus.FAILURE,
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.name(),
				e.getMessage()
			), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(
				new ResponseWrapper<>(
				EHttpStatus.FAILURE,
				HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.name(),
				ex.getMessage()
		), HttpStatus.NOT_FOUND);
	}
}
