package com.linear_algebra.thinking_machine.controller;

import com.linear_algebra.thinking_machine.model.Matrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatrixController {

    @GetMapping("/{dimension}")
    public Matrix getIdentityMatrix(@PathVariable int dimension) {
        Matrix identityMatrix = Matrix.identityMatrix(dimension);

        return identityMatrix;
    }
}
