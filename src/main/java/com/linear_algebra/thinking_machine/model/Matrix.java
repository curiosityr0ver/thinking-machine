package com.linear_algebra.thinking_machine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;



@Getter
public class Matrix {
    public enum Type {IDENTITY, ZERO};

    private int[][] matrix;

    // rows and cols are computed dynamically
    @JsonIgnore
    public int getRows() {
        return matrix != null ? matrix.length : 0;
    }

    @JsonIgnore
    public int getCols() {
        return (matrix != null && matrix.length > 0) ? matrix[0].length : 0;
    }


    // Default constructor for Jackson
    public Matrix() { }

    // Create empty matrix with given dimensions
    public Matrix(int rows, int cols) {
        this.matrix = new int[rows][cols];
    }

    // Create a matrix of specific type
    public Matrix(int size, Type type) {
        this.matrix = new int[size][size];
        switch (type) {
            case IDENTITY -> {
                for (int i = 0; i < size; i++) {
                    matrix[i][i] = 1;
                }
            }
            case ZERO -> {
                // already zero by default, no action needed
            }
        }
    }

    // Return both dimensions as array
    @JsonIgnore
    public int[] getDimensions() {
        return new int[]{getRows(), getCols()};
    }

    // Factory methods
    public static Matrix identityMatrix(int size) {
        return new Matrix(size, Type.IDENTITY);
    }

    public static Matrix zeroMatrix(int size) {
        return new Matrix(size, Type.ZERO);
    }

    // Optional: deep copy method to prevent external mutation
    public Matrix copy() {
        int[][] newMatrix = new int[getRows()][getCols()];
        for (int i = 0; i < getRows(); i++) {
            System.arraycopy(this.matrix[i], 0, newMatrix[i], 0, getCols());
        }
        Matrix copy = new Matrix();
        copy.matrix = newMatrix;
        return copy;
    }
}
