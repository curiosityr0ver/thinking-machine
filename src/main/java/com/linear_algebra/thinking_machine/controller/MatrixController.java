package com.linear_algebra.thinking_machine.controller;

import com.linear_algebra.thinking_machine.model.Matrix;
import com.linear_algebra.thinking_machine.service.MatrixStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MatrixController {

    private final MatrixStore store;

    public MatrixController() {
        store = new MatrixStore();
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Matrix matrix) {
        String id = store.create(matrix.copy()); // always store a copy for safety
        return ResponseEntity.created(URI.create("/api/v1/" + id)).body(id);
    }

    @GetMapping("/generate")
    public ResponseEntity<?> generateMatrix(
            @RequestParam int size,
            @RequestParam(defaultValue = "identity") String type,
            @RequestParam(defaultValue = "false") boolean store) {

        // 1️⃣ Validate and convert type
        Matrix.Type matrixType;
        try {
            matrixType = Matrix.Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            String errorMsg = "Invalid matrix type '" + type + "'. Allowed values: identity, zero.";
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", errorMsg));
        }

        // 2️⃣ Create matrix (type is guaranteed valid)
        Matrix matrix;
        switch (matrixType) {
            case IDENTITY:
                matrix = Matrix.identityMatrix(size);
                break;
            case ZERO:
                matrix = Matrix.zeroMatrix(size);
                break;
            default:
                // Should never happen
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "Unknown matrix type."));
        }

        // 3️⃣ Store if requested
        if (store) {
            String id = this.store.create(matrix.copy()); // defensive copy
            return ResponseEntity.ok()
                    .header("X-Matrix-ID", id)
                    .body(matrix);
        }

        // 4️⃣ Return matrix directly
        return ResponseEntity.ok(matrix);
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<Matrix> get(@PathVariable String id) {
        return store.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // List all stored matrix IDs
    @GetMapping("/list-all")
    public List<String> list() {
        return store.listIDs();
    }

    // Delete a matrix by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        store.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Replace a matrix by ID
    @PutMapping("/{id}")
    public ResponseEntity<Void> replace(@PathVariable String id, @RequestBody Matrix matrix) {
        boolean updated = store.update(id, matrix);
        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
