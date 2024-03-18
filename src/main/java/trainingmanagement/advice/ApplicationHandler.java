package trainingmanagement.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.enums.EHttpStatus;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationHandler {
	public ResponseEntity<Map<String, String>> handleInvalidException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getFieldErrors().forEach(err -> {
			errors.put(err.getField(), err.getDefaultMessage());
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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
}
