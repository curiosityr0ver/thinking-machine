package com.linear_algebra.thinking_machine.controller;

import com.linear_algebra.thinking_machine.model.Matrix;
import com.linear_algebra.thinking_machine.service.MatrixStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
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

    @GetMapping("/identity/{dimension}")
    public Matrix getIdentityMatrix(@PathVariable int dimension) {
        return Matrix.identityMatrix(dimension);
    }


    @GetMapping()
    public String test() {
        return "Hello World !";
    }
}
