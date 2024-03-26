package trainingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/**")
public class NotFoundException {
    @GetMapping
    public ResponseEntity<?> handleNotFoundGet() throws ResourceNotFoundException {
        throw new ResourceNotFoundException("Resource not found");
    }

    @PostMapping
    public ResponseEntity<?> handleNotFoundPost() throws ResourceNotFoundException {
        throw new ResourceNotFoundException("Resource not found");
    }

    @PutMapping
    public ResponseEntity<?> handleNotFoundPut() throws ResourceNotFoundException {
        throw new ResourceNotFoundException("Resource not found");
    }

    @PatchMapping
    public ResponseEntity<?> handleNotFoundPatch() throws ResourceNotFoundException {
        throw new ResourceNotFoundException("Resource not found");
    }

    @DeleteMapping
    public ResponseEntity<?> handleNotFoundDelete() throws ResourceNotFoundException {
        throw new ResourceNotFoundException("Resource not found");
    }
}
