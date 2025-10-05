package com.linear_algebra.thinking_machine.model;

enum Type {IDENTITY};

public class Matrix {
    private final int n, m;
    private final int[][] matrix;


    Matrix(int len, int bre) {
        this.n = len;
        this.m = bre;

        matrix = new int[n][m];
    }

    Matrix(int n, int m, Type type) {
        this.n = n;
        this.m = m;
        matrix = new int[n][m];
        if (type == Type.IDENTITY) {
            for (int i = 0; i < n; i++) {
                matrix[i][i] = 1;
            }
        }
    }

    public int[] getDimensions() {
        return new int[]{n, m};
    }

    public static Matrix identityMatrix(int dimension) {
        return new Matrix(dimension, dimension, Type.IDENTITY);
    }
}
